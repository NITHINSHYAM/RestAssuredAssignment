package com.internetapp.tests;

import com.jayway.jsonpath.DocumentContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.UUID;


public class CITIBANK_LOP2 {

    public CITIBANK_LOP2() {
    }

    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = "https://sandbox.apihub.citi.com";
    }


    @Test(priority = 1)
    public void givenValidDetails_Create_PetData_Check_StatusCode200() {

 /*       String payload = "        {\n" +
                "            \"grant_type\": \"card_authorization\",\n" +
                "                \"lastFourDigitsCardNumber\": \"8653\",\n" +
                "                \"citiCardHolderPhoneNumber\": \"6590020056\",\n" +
                "                \"merchantCustomerReferenceId\": \"CB072000128065\"\n" +
                "        }\n";
*/

        String payload = "{" +
                "\t\t\"lastFourDigitsCardNumber\": \"7411\",\n" +
                "\t\t\"citiCardHolderPhoneNumber\": \"2608191234\",\n" +
                "\t\t\"merchantCustomerReferenceId\": \"2608191234111\"\n" +
                "}";


        String authorization = "Basic ODMwZjVlMjctYmMwNy00YmVjLTk1NDMtMGMwYmY5YmE3YjljOkMxaFg2aUcwY1gyaUIzdE44eEM3eVEwZ1U2eEQwc0YxZ1Q1eUc2ZkE0cks0aEU1akEy";
        String client_id = "830f5e27-bc07-4bec-9543-0c0bf9ba7b9c";


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
                .post("/gcb/api/cardAuth/oauth2/authorize/SG/gcb");

        ResponseBody body = response.body();
        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String controlFlowId = jsonContext.read("$.moreInfo.controlFlowId");
        System.out.println("<<<<<<<<<<<<<<<" + controlFlowId);

        request = RestAssured.given();
        String formUrlEncodedPayload = "grant_type=card_authorization&controlFlowId=" + controlFlowId + "&linkageConfirmationCode=735937&scope=lop_booking epp_booking";
        response = request
                .when()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", authorization)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(formUrlEncodedPayload)
                .post("/gcb/api/cardAuth/oauth2/token/SG/gcb");

        System.out.println(response.body().prettyPrint());

        body = response.body();
        jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token = jsonContext.read("$.access_token");
        System.out.println("<<<<<<<<<<<<<<<" + access_token);

        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization", "Bearer " + access_token)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .get("/gcb/api/v1/accounts/loans/eligibility");

        System.out.println(response.body().prettyPrint());

        body = response.body();
        jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String accountId = jsonContext.read("$.loanEligibilityDetails[0].accountId");
        System.out.println("<<<<<<<<<<<<<<<" + accountId);

        String uri = "/gcb/api/v1/accounts/" + accountId + "/loans/disbursementOptions";
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization", "Bearer " + access_token)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .get(uri);

        System.out.println(response.body().prettyPrint());


        uri = "/gcb/api/v1/accounts/" + accountId + "/loans/60000/paymentsPlans";
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization", "Bearer " + access_token)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .get(uri);

        System.out.println(response.body().prettyPrint());


        uri = "/gcb/api/v1/accounts/" + accountId + "/loans/repaymentSchedules?loanAmount=60000&tenor=12";
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization", "Bearer " + access_token)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .get(uri);

        System.out.println(response.body().prettyPrint());


        payload = "        {\n" +
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


        uri = "/gcb/api/v1/accounts/" + accountId + "/loans/bookings";

        request = RestAssured.given();
        response = request
                .when()
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("Authorization", "Bearer " + access_token)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(payload)//add pet object
                .post(uri);

        body = response.body();
        System.out.println(response.body().prettyPrint());

        body = response.body();
        jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        controlFlowId = jsonContext.read("$.moreInfo.controlFlowId");
        System.out.println("<<<<<<<<<<<<<<<" + controlFlowId);


        uri = "/gcb/api/v1/security/e2eKey";
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization", "Bearer " + access_token)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .get(uri);

        System.out.println(response.body().prettyPrint());


        payload = " {\n" +
                "            \"otpDeliveryOption\": \"SMS\",\n" +
                "                \"otpToken\": \"77219F78B16B130E4894E768F08567F06470F59AAC12722D943276877C30567E8E78462F03F3D2BE5679E1AC109530F1CE7F340DB5DB342AEC0C822777D3C3B3B751A7850FAE738FF8DFCB6C805650FD0FBA90FF8E8F4A16AA1A6DC74C44A39B23512336F02C70C02D5BFCF0FAEA4170F98812B3FA9D21DE8239B300A0FFF259\",\n" +
                "                \"transactionMemo\": \"ENROLLCARD\",\n" +
                "                \"controlFlowId\": \"";

        payload = payload + controlFlowId;
        payload = payload + "\"\n" +
                "        }        ";

        uri = "/gcb/api/v1/mfa/otp";
        request = RestAssured.given();
        response = request
                .when()
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("Authorization", "Bearer " + access_token)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(payload)//add pet object
                .put(uri);

        body = response.body();
        System.out.println("The response is : " + response.body().prettyPrint());


        payload = "{ \"controlFlowId\": \"";
        payload = payload + controlFlowId;
        payload = payload + "\"}";
        System.out.println("The OTP payload is : " + payload);


        uri = "/gcb/api/v1/accounts/loans/bookings/confirmation";
        request = RestAssured.given();
        response = request
                .when()
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("Authorization", "Bearer " + access_token)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(payload)//add pet object
                .post(uri);

        body = response.body();
        System.out.println("The loan booking confirmation response is : <><><><><>" + response.body().prettyPrint());


        body = response.body();
        jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String loanReferenceId = jsonContext.read("$.loanReferenceId");
        System.out.println("The loan reference id is : >>>>>>>>>>>>>>>>>>" + loanReferenceId);


        uri = "/gcb/api/v1/accounts/" + accountId + "/loans";
        System.out.println("The final url for loan summary is : " + uri);

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

        body = response.body();
        jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());

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
            }

        } catch (Exception e) {

        }
    }
}