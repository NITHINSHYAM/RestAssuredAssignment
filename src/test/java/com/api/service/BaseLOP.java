package com.api.service;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;

import com.jayway.jsonpath.DocumentContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import PropertyFile.PropertyFile;

import java.util.UUID;

public class BaseLOP {

	String  authorization,client_id,baseURI,payload;
	 String controlFlowId, access_token, accountId;
	 String loanReferenceId;
	 int statusCode;

		@BeforeTest
	    public void setUp() {
	        RestAssured.baseURI = PropertyFile.getValue("baseURI");;
	        authorization = PropertyFile.getValue("authorization");
	        client_id = PropertyFile.getValue("client_id");
	        
	      payload = "{" +
	                "\t\t\"lastFourDigitsCardNumber\": \"7411\",\n" +
	                "\t\t\"citiCardHolderPhoneNumber\": \"2608191234\",\n" +
	                "\t\t\"merchantCustomerReferenceId\": \"2608191234111\"\n" +
	                "}";
	    }
		
		public void Card_Authentication() {
			
             
	        RequestSpecification request = RestAssured.given();
	        Response response = request
	                .when()
	                .contentType(ContentType.APPLICATION_JSON.getMimeType())
	                .header("Authorization", authorization)
	                .accept(ContentType.APPLICATION_JSON.getMimeType())
	                .header("uuid", UUID.randomUUID().toString())
	                .header("client_id", client_id)
	                .with()
	                .body(payload)//add pet object
	                .post(PropertyFile.getValue("lop_auth_url"));


	        ResponseBody body = response.body();
	        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
	        controlFlowId = jsonContext.read("$.moreInfo.controlFlowId");
	        
	        System.out.println("<<<<<<<<<<<<<<<" + controlFlowId);
	        statusCode = response.getStatusCode();
	        Assert.assertEquals(statusCode, 403);
	        
		}
	
		public void Card_AccessToken() {
			
			String url="/gcb/api/cardAuth/oauth2/token/SG/gcb";
			RequestSpecification request = RestAssured.given();
	        String formUrlEncodedPayload = "grant_type=card_authorization&controlFlowId=" + controlFlowId + "&linkageConfirmationCode=735937&scope=lop_booking epp_booking";
	        Response response = request
	                .when()
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .header("Authorization", authorization)
	                .accept(ContentType.APPLICATION_JSON.getMimeType())
	                .header("uuid", UUID.randomUUID().toString())
	                .header("client_id", client_id)
	                .with()
	                .body(formUrlEncodedPayload)
	                .post(url);

	        System.out.println(response.body().prettyPrint());

	        ResponseBody body = response.body();
	        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
	         access_token = jsonContext.read("$.access_token");
	        System.out.println("<<<<<<<<<<<<<<<" + access_token);
	        statusCode = response.getStatusCode();
	        Assert.assertEquals(statusCode, 200);
	        
		}
		
		public void Loan_Eligibility() {
			
			String url = "/gcb/api/v1/accounts/loans/eligibility";
			RequestSpecification request = RestAssured.given();
			Response    response = request
		                .when()
		                .header("Authorization", "Bearer " + access_token)
		                .accept(ContentType.APPLICATION_JSON.getMimeType())
		                .header("uuid", UUID.randomUUID().toString())
		                .header("client_id", client_id)
		                .with()
		                .get(url);

		        System.out.println(response.body().prettyPrint());

		        ResponseBody body = response.body();
		        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
		         accountId = jsonContext.read("$.loanEligibilityDetails[0].accountId");
		        System.out.println("<<<<<<<<<<<<<<<" + accountId);
		        statusCode = response.getStatusCode();
		        Assert.assertEquals(statusCode, 200 );
		        
		}
		
		public void Disbursment() {
			
			String uri = "/gcb/api/v1/accounts/" + accountId + "/loans/disbursementOptions";
			RequestSpecification request = RestAssured.given();
			Response response = request
	                .when()
	                .header("Authorization", "Bearer " + access_token)
	                .accept(ContentType.APPLICATION_JSON.getMimeType())
	                .header("uuid", UUID.randomUUID().toString())
	                .header("client_id", client_id)
	                .with()
	                .get(uri);

	        System.out.println(response.body().prettyPrint());

		}
		
		public void LOP_PaymentPlans() {
			
			String uri = "/gcb/api/v1/accounts/" + accountId + "/loans/60000/paymentsPlans";
			RequestSpecification request = RestAssured.given();
			Response response = request
	                .when()
	                .header("Authorization", "Bearer " + access_token)
	                .accept(ContentType.APPLICATION_JSON.getMimeType())
	                .header("uuid", UUID.randomUUID().toString())
	                .header("client_id", client_id)
	                .with()
	                .get(uri);

	        System.out.println(response.body().prettyPrint());

		}
		
		public void Loan_RePayment() {
			
			String  uri = "/gcb/api/v1/accounts/" + accountId + "/loans/repaymentSchedules?loanAmount=60000&tenor=12";
			RequestSpecification    request = RestAssured.given();
			Response  response = request
		                .when()
		                .header("Authorization", "Bearer " + access_token)
		                .accept(ContentType.APPLICATION_JSON.getMimeType())
		                .header("uuid", UUID.randomUUID().toString())
		                .header("client_id", client_id)
		                .with()
		                .get(uri);

		        System.out.println(response.body().prettyPrint());
		        
		}
		
		public void Loan_Booking() {
			
			String payload = "        {\n" +
		                "            \"loanAmount\":60000 ,\n" +
		                "                \"disbursementOption\": \"PERSONAL_ACCOUNT\",\n" +
		                "                \"disbursementAccountId\": \"44125873852316f2b4d4d796c344e38756339654972776f663745446e6d4c32486f455a4165374a476858343d\",\n" +
		                "                \"tenor\": 12,\n" +
		                "                \"bankDetails\": {\n" +
		                "            \"bankCode\": \"DBSSG0XXX\",\n" +
		                "                    \"bankName\": \"HSBC Bank\",\n" +
		                "                    \"branchCode\": \"10034\",\n" +
		                "                    \"accountNumber\": \"5396100020088653\"\n" +
		                "        },\n" +
		                "            \"loanPurpose\": \"EDUCATION_OVERSEAS\",\n" +
		                "                \"employmentStatus\": \"PRIVATE_EMPLOYEE\",\n" +
		                "                \"occupationCode\": \"BUSINESS\",\n" +
		                "                \"highestEducationLevel\": \"GRADUATE\",\n" +
		                "                \"paymentReferenceId\": 123456\n" +
		                "        }        \n";


			String uri = "/gcb/api/v1/accounts/" + accountId + "/loans/bookings";

			RequestSpecification  request = RestAssured.given();
			Response   response = request
		                .when()
		                .contentType(ContentType.APPLICATION_JSON.getMimeType())
		                .header("Authorization", "Bearer " + access_token)
		                .accept(ContentType.APPLICATION_JSON.getMimeType())
		                .header("uuid", UUID.randomUUID().toString())
		                .header("client_id", client_id)
		                .with()
		                .body(payload)//add pet object
		                .post(uri);

			ResponseBody   body = response.body();
		        System.out.println(response.body().prettyPrint());

		        ResponseBody  body1 = response.body();
		        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body1.print());
		        controlFlowId = jsonContext.read("$.moreInfo.controlFlowId");
		        System.out.println("<<<<<<<<<<<<<<<" + controlFlowId);
		        statusCode = response.getStatusCode();
		        Assert.assertEquals(statusCode, 403 );
		        
		}
		
		public void e2eKey() {
			
			String uri = "/gcb/api/v1/security/e2eKey";
			RequestSpecification request = RestAssured.given();
	        Response response = request
	                .when()
	                .header("Authorization", "Bearer " + access_token)
	                .accept(ContentType.APPLICATION_JSON.getMimeType())
	                .header("uuid", UUID.randomUUID().toString())
	                .header("client_id", client_id)
	                .with()
	                .get(uri);

	        System.out.println(response.body().prettyPrint());
	        
		}
		
		public void ValidateOTP() {
			
			String payload = " {\n" +
	                "            \"otpDeliveryOption\": \"SMS\",\n" +
	                "                \"otpToken\": \"77219F78B16B130E4894E768F08567F06470F59AAC12722D943276877C30567E8E78462F03F3D2BE5679E1AC109530F1CE7F340DB5DB342AEC0C822777D3C3B3B751A7850FAE738FF8DFCB6C805650FD0FBA90FF8E8F4A16AA1A6DC74C44A39B23512336F02C70C02D5BFCF0FAEA4170F98812B3FA9D21DE8239B300A0FFF259\",\n" +
	                "                \"transactionMemo\": \"ENROLLCARD\",\n" +
	                "                \"controlFlowId\": \"";

	        payload = payload + controlFlowId;
	        payload = payload + "\"\n" + "        }        ";
	        
			String uri = "/gcb/api/v1/mfa/otp";
			RequestSpecification request = RestAssured.given();
	        Response response = request
	                .when()
	                .contentType(ContentType.APPLICATION_JSON.getMimeType())
	                .header("Authorization", "Bearer " + access_token)
	                .accept(ContentType.APPLICATION_JSON.getMimeType())
	                .header("uuid", UUID.randomUUID().toString())
	                .header("client_id", client_id)
	                .with()
	                .body(payload)//add pet object
	                .put(uri);

	        ResponseBody body = response.body();
	        System.out.println("The response is : " + response.body().prettyPrint());
	        statusCode = response.getStatusCode();
	        Assert.assertEquals(statusCode, 200 );


	        
		}
		
		public void Loan_Booking_Confirmation() {
			
			
			String payload = "{ \"controlFlowId\": \"";
	        payload = payload + controlFlowId;
	        payload = payload + "\"}";
	        System.out.println("The OTP payload is : " + payload);
			
			 String uri = "/gcb/api/v1/accounts/loans/bookings/confirmation";
			 RequestSpecification request = RestAssured.given();
			 Response response  = request
		                .when()
		                .contentType(ContentType.APPLICATION_JSON.getMimeType())
		                .header("Authorization", "Bearer " + access_token)
		                .accept(ContentType.APPLICATION_JSON.getMimeType())
		                .header("uuid", UUID.randomUUID().toString())
		                .header("client_id", client_id)
		                .with()
		                .body(payload)//add pet object
		                .post(uri);

		       // ResponseBody body = response.body();
		        System.out.println("The loan booking confirmation response is : <><><><><>" + response.body().prettyPrint());


		        ResponseBody body1 = response.body();
		        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body1.print());
		         loanReferenceId = jsonContext.read("$.loanReferenceId");
		        System.out.println("The loan reference id is : >>>>>>>>>>>>>>>>>>" + loanReferenceId);
		        statusCode = response.getStatusCode();
		        Assert.assertEquals(statusCode, 200 );
			
			
		}
		
		public void LOP_Summary_Repayment() {
			
			String uri = "/gcb/api/v1/accounts/" + accountId + "/loans";
		        System.out.println("The final url for loan summary is : " + uri);

		        RequestSpecification request = RestAssured.given();
		        Response response = request
		                .when()
		                .header("Authorization", "Bearer " + access_token)
		                .accept(ContentType.APPLICATION_JSON.getMimeType())
		                .header("uuid", UUID.randomUUID().toString())
		                .header("client_id", client_id)
		                .with()
		                .get(uri);

		        ResponseBody body = response.body();
		        System.out.println("The response is : " + response.body().prettyPrint());

		        ResponseBody body1 = response.body();
		        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body1.print());

		        int i = -1;
		        try {
		            for (; ; ) {
		                i = i + 1;
		                 loanReferenceId = jsonContext.read("$.loans[" + i + "].loanReferenceId");
		                System.out.println("<<<<<<<<<<<<<<<" + loanReferenceId);


		                uri = "/gcb/api/v1/accounts/loans/" + loanReferenceId + "/repaymentSchedules";
		                //uri = "https://qa.sandbox.apihub.citi.com/gcb/gp/api/v1/accounts/loans/EbR3xtBY7TSHQYmekXe7/repaymentSchedules";
		                System.out.println("The final url is : " + uri);

		                request = RestAssured.given();
		                response = request
		                        .when()
		                        .header("Authorization", "Bearer " + access_token)
		                        .accept(ContentType.APPLICATION_JSON.getMimeType())
		                        .header("uuid", UUID.randomUUID().toString())
		                        .header("client_id", client_id)
		                        .with()
		                        .get(uri);

		                body = response.body();
		                System.out.println("The response is : " + response.body().prettyPrint());
		                statusCode = response.getStatusCode();
				        Assert.assertEquals(statusCode, 200 );
		            }

		        } catch (Exception e) {

		        }
		        
		}
}


