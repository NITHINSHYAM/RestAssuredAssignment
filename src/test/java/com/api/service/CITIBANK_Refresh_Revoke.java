package com.api.service;

import com.jayway.jsonpath.DocumentContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.UUID;


public class CITIBANK_Refresh_Revoke extends BaseEPP {

	String payload;
	String authorization;
	String client_id;
    String controlFlowId;
	
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
	public void Initiate_the_Refresh_Token_API_with_valid_fields_and_verify_the_Expected_Result() throws IOException {
		
		cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");
		ResponseBody body = response.body();
		DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
	
		String refreshToken = jsonContext.read("$.refresh_token");
      
        response=refreshToken(response,refreshToken);
        Assert.assertEquals(200, response.statusCode());
       
        
		}
	
	
	@Test(priority = 4)
	public void To_verify_the_request_with_invalid_refresh_token() throws IOException {
		
		cardAuthorization("card_Auth","Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result");
		Response response=cardAuthAccessToken("card_Auth_AcessToken","Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result");
		ResponseBody body = response.body();
		DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
	
		String refreshToken = jsonContext.read("$.refresh_token");
		refreshToken=refreshToken+"123";
      
		response=refreshToken(response,refreshToken);
        Assert.assertEquals(401, response.statusCode());
         

		}
   
}