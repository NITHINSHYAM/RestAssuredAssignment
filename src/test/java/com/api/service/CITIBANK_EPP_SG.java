package com.api.service;

import com.jayway.jsonpath.DocumentContext;

import PropertyFile.PropertyFile;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;



public class CITIBANK_EPP_SG extends BaseEPP {


	
	@Test(priority = 1)
	public void Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result() throws IOException {
		
		Response response=cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		

		 Assert.assertEquals(403, response.statusCode());

	}

	@Test(priority = 2)
	public void Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result() throws IOException {

		cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");
		
	    Assert.assertEquals(200, response.statusCode());
		
	}
    
    @Test(priority = 3)
	public void Initiate_the_easyPaymentPlan_API_with_valid_fields_and_verify_the_Expected_Result() throws IOException {
    	
    	cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");

		ResponseBody body = response.body();
		DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
		String cardId = jsonContext.read("$.cardId");
		String uri=PropertyFile.getValue("easyPaymentPlan_URL");
		uri=StringUtils.replace(uri, "%cardId%", cardId);
		easyPaymentPlan(response, uri);
		
		 Assert.assertEquals(200, response.statusCode());

	}
    
    
    @Test(priority = 4)
    public void To_verify_the_request_with_invalid_eligbleLoanAmount() throws IOException {
    	
    	cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");

		ResponseBody body = response.body();
		DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
		String cardId = jsonContext.read("$.cardId");
		String uri=PropertyFile.getValue("To_verify_the_request_with_invalid_eligbleLoanAmount");
		uri=StringUtils.replace(uri, "%cardId%", cardId);
		response=easyPaymentPlan(response, uri);
		
		 Assert.assertEquals(422, response.statusCode());
		 Assert.assertTrue(response.prettyPrint().contains("Transaction amount is invalid"),"error msg");
					

    }
    
    
    @Test(priority = 5)
    public void To_verify_the_request_with_invalid_card_id() throws FileNotFoundException, IOException {
    	
    	cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");

		ResponseBody body = response.body();
		DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
		String uri=PropertyFile.getValue("easyPaymentPlan_URL");
		String cardId="1234";
		uri=StringUtils.replace(uri, "%cardId%", cardId);
		response=easyPaymentPlan(response, uri);
		
		Assert.assertEquals(422, response.statusCode());
		Assert.assertTrue(response.prettyPrint().contains("Card is invalid"),"error msg");    	
    }
    
    @Test(priority = 6)
    public void Initiate_the_repaymentSchedules_API_with_valid_fields_and_verify_the_Expected_Result() throws FileNotFoundException, IOException {
    	
    	cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");

		ResponseBody body = response.body();
		DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
		String cardId = jsonContext.read("$.cardId");
		String uri=PropertyFile.getValue("easyPaymentPlan_URL");
		uri=StringUtils.replace(uri, "%cardId%", cardId);
		easyPaymentPlan(response, uri);
		response=rechedulepayments(response, cardId);
		
		Assert.assertEquals(200, response.statusCode());
    }
    
    
    @Test(priority = 7)
    public void To_verify_the_request_with_valid_eppLoanBookingType() throws IOException {
    	
    	cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");

		ResponseBody body = response.body();
		DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
		String cardId = jsonContext.read("$.cardId");
		String uri=PropertyFile.getValue("easyPaymentPlan_URL");
		uri=StringUtils.replace(uri, "%cardId%", cardId);
		easyPaymentPlan(response, uri);
		rechedulepayments(response, cardId);
		
		String payloadBooking=UtilityClass.excelreading(excelFilePath,"valid_EppLoanBookingType","To_verify_the_request_with_valid_eppLoanBookingType");
		response=eppLoanBookingType(payloadBooking,cardId);
		
		//Assert.assertEquals(200, response.statusCode());
    }
    
    @Test(priority = 8)
    public void To_verify_the_request_with_invalid_eppLoanBookingType1() throws FileNotFoundException, IOException {
    	
    	cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");

		ResponseBody body = response.body();
		DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
		String cardId = jsonContext.read("$.cardId");
		String uri=PropertyFile.getValue("easyPaymentPlan_URL");
		uri=StringUtils.replace(uri, "%cardId%", cardId);
		easyPaymentPlan(response, uri);
		rechedulepayments(response, cardId);
		
		String payloadBooking=UtilityClass.excelreading(excelFilePath,"Invalid_EppLoanBookingType_1","To_verify_the_request_with_invalid_eppLoanBookingType1");
		response=eppLoanBookingType(payloadBooking,cardId);
		
		Assert.assertEquals(400, response.statusCode());
		Assert.assertTrue(response.prettyPrint().contains("Missing or invalid parameters"),"error msg");
		
    }
    
    @Test(priority = 9)
    public void To_verify_the_request_with_invalid_eppLoanBookingType2() throws FileNotFoundException, IOException {
    	
    	cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");

		ResponseBody body = response.body();
		DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
		String cardId = jsonContext.read("$.cardId");
		String uri=PropertyFile.getValue("easyPaymentPlan_URL");
		uri=StringUtils.replace(uri, "%cardId%", cardId);
		easyPaymentPlan(response, uri);
		rechedulepayments(response, cardId);
		
		String payloadBooking=UtilityClass.excelreading(excelFilePath,"Invalid_EppLoanBookingType_2","To_verify_the_request_with_invalid_eppLoanBookingType2");
		response=eppLoanBookingType(payloadBooking,cardId);
		
		Assert.assertTrue(response.prettyPrint().contains("Missing or invalid parameters"),"error msg");
    }
    

	
}