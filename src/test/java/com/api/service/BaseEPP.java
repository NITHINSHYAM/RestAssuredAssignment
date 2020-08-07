package com.api.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.jayway.jsonpath.DocumentContext;

import PropertyFile.PropertyFile;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class BaseEPP {
	
	
	static String authorization;
	static String client_id;
     String access_token;
     static String controlFlowId;
     String payload;
     static String excelFilePath;
     

	@BeforeTest
	public void setUp() throws IOException {

		RestAssured.baseURI = PropertyFile.getValue("baseURI");
		authorization = PropertyFile.getValue("authorization");
		client_id = PropertyFile.getValue("client_id");
		excelFilePath=PropertyFile.getValue("EPPExcelFilePath");
	}
	
	 public static Response cardAuthorization(String sheetName,String testCaseName) throws IOException {
			
		    String payload1 =UtilityClass.excelreading(excelFilePath,sheetName,testCaseName);
			
			 System.out.println("Start of API >>>>   /gcb/api/cardAuth/oauth2/authorize/SG/gcb");
		        RequestSpecification request = RestAssured.given();
		        Response response = request
		                .when()
		                .contentType(ContentType.APPLICATION_JSON.getMimeType())
		                .header("Authorization", authorization)
		                .accept(ContentType.APPLICATION_JSON.getMimeType())
		                .header("uuid", UUID.randomUUID().toString())
		                .header("client_id", client_id)
		                .with()
		                .body(payload1)//passing payload 
		                .post(PropertyFile.getValue("epp_auth_url"));

		        ResponseBody body = response.body();
		        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
		        controlFlowId = jsonContext.read("$.moreInfo.controlFlowId");
		        System.out.println(response.statusCode());
		        System.out.println("<<<<<<<<<<<<<<<" + controlFlowId);

		        System.out.println("End of API >>>>   /gcb/api/cardAuth/oauth2/authorize/SG/gcb");
		        System.out.println();
				return response;
		}


	
	public Response cardAuthAccessToken(String sheetName,String testCaseName) throws FileNotFoundException, IOException {
		
		String formUrlEncodedPayload=UtilityClass.excelreading(excelFilePath,sheetName,testCaseName);
		if (formUrlEncodedPayload.contains("%controlFlowId%")) {

			formUrlEncodedPayload = StringUtils.replace(formUrlEncodedPayload, "%controlFlowId%", controlFlowId);
			
		}
		 System.out.println("Start of API >>>>   /gcb/api/cardAuth/oauth2/token/SG/gcb");
         RequestSpecification  request = RestAssured.given();
	     Response response = request
	                .when()
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .header("Authorization", authorization)
	                .accept(ContentType.APPLICATION_JSON.getMimeType())
	                .header("uuid", UUID.randomUUID().toString())
	                .header("client_id", client_id)
	                .with()
	                .body(formUrlEncodedPayload)
	                .post(PropertyFile.getValue("epp_authaccesstoken_url"));

	        System.out.println(response.statusCode());
	        System.out.println(response.body().prettyPrint());

	        System.out.println("End of API >>>>   /gcb/api/cardAuth/oauth2/token/SG/gcb");
	        System.out.println(PropertyFile.getValue("epp_authaccesstoken_url"));
	        return response;
	}
	
	public Response easyPaymentPlan(Response response,String uri) {
		
		    ResponseBody body = response.body();
		    DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
			access_token = jsonContext.read("$.access_token");
	        System.out.println("Start of API >>>>  " +  uri);
	        RequestSpecification request = RestAssured.given();
	        response = request
	                .when()
	                .header("Authorization", "Bearer " + access_token)
	                .accept(ContentType.APPLICATION_JSON.getMimeType())
	                .header("uuid", UUID.randomUUID().toString())
	                .header("client_id", client_id)
	                .with()
	                .get(uri);

	        System.out.println(response.statusCode());
	        System.out.println(response.body().prettyPrint());
	        System.out.println("End of API >>>> " +  uri );
			return response;

	}
	
	public Response rechedulepayments(Response response,String cardId) {
		
		String uri=PropertyFile.getValue("rechedulepayments_URL");
		uri=StringUtils.replace(uri, "%cardId%", cardId);
		    System.out.println();
	        System.out.println("Start of API >>>>  " +  uri );

	        RequestSpecification request = RestAssured.given();
	        response = request
	                .when()
	                .header("Authorization", "Bearer " + access_token)
	                .accept(ContentType.APPLICATION_JSON.getMimeType())
	                .header("uuid", UUID.randomUUID().toString())
	                .header("client_id", client_id)
	                .with()
	                .get(uri);

	        System.out.println(response.statusCode());
	        System.out.println(response.body().prettyPrint());

	        System.out.println("End of API >>>> " +  uri );
			return response;
	}
	
	public Response eppLoanBookingType(String payloadBooking,String cardId) {
		
		 String uri=PropertyFile.getValue("eppLoanBookingType_URL");
		 uri=StringUtils.replace(uri, "%cardId%", cardId);
	    // String uri = "/gcb/api/v1/creditCards/" + cardId + "/easyPaymentPlans/bookings";
	     RequestSpecification request = RestAssured.given();
	     Response response = request
	             .when()
	             .contentType(ContentType.APPLICATION_JSON.getMimeType())
	             .header("Authorization", "Bearer " + access_token)
	             .accept(ContentType.APPLICATION_JSON.getMimeType())
	             .header("uuid", UUID.randomUUID().toString())
	             .header("client_id", client_id)
	             .with()
	             .body(payloadBooking)//add pet object
	             .post(uri);
	
	     System.out.println(response.statusCode());
	     System.out.println(response.body().prettyPrint());
		return response;
	}
	
	
	public Response refreshToken(Response response,String refreshToken) throws FileNotFoundException, IOException {
		
		String refreshPayload = UtilityClass.excelreading(excelFilePath,"Refresh_Token",
				"Initiate_the_Refresh_Token_API_with_valid_fields_and_verify_the_Expected_Result");
		refreshPayload = StringUtils.replace(refreshPayload, "%refresh_token%", refreshToken);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        RequestSpecification request = RestAssured.given();
        response = request
                .when()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", authorization)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(refreshPayload).log().all()//add pet object
                .post(PropertyFile.getValue("refresh_token_URL"));

        System.out.println(response.body().prettyPrint());
        System.out.println("The refresh status code is " + response.getStatusCode());
		return response;

	}	

}
