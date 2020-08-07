package com.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.citibankapis.accounttransactions.AccountGroupSummary;
import com.citibankapis.accounttransactions.AccountGroupSummaryList;
import com.citibankapis.pdt.PDT_Payload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jayway.jsonpath.DocumentContext;

import PropertyFile.PropertyFile;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CITIBANK_API_ENGINE {
	String authorization_64;
	String client_id;
	String client_secret;
	String access_token;
	String BizToken;
	String Authorization_new;
	String access_token_new;
	
@BeforeTest
public void baseURI_setup() {
	RestAssured.baseURI=PropertyFile.getValue("MM_baseURI");
	authorization_64 = PropertyFile.getValue("MM_authorization_64");
	client_id= PropertyFile.getValue("MM_client_id");
	client_secret = PropertyFile.getValue("MM_client_secret");
	
	RequestSpecification request = RestAssured.given();
	RequestSpecBuilder reqbuilder = new RequestSpecBuilder();
	reqbuilder.addHeader("Authorization",authorization_64);
	reqbuilder.addHeader("uuid",UUID.randomUUID().toString());
	request = reqbuilder.build();
}
@Test
public void API_clientCredentials(String CCmethod,String Grant, String scope) {
	
	System.out.println("Start of the API >>>  /gcb/api/clientCredentials/oauth2/token/sg/gcb");
    String payload = "grant_type="+Grant+"&scope="+scope;
   
    RequestSpecification request = RestAssured.given();
    RequestSpecBuilder reqbuilder = new RequestSpecBuilder();
	reqbuilder.addHeader("Authorization",authorization_64);
	reqbuilder.addHeader("uuid",UUID.randomUUID().toString());
    request = reqbuilder.build();
    
    Response response = RestAssured.given()
            .when()
            .contentType("application/x-www-form-urlencoded")
            //.header("Authorization",authorization_64)
            //.header("uuid",UUID.randomUUID().toString())
            .spec(request)
            .accept(ContentType.APPLICATION_JSON.getMimeType())
            .with()
            .body(payload)
            .post(PropertyFile.getValue(CCmethod));

    
    if (Grant.toString().equals("loan_authorization")||(scope.toString().equals("/apis"))){
    	
    	ValidatableResponse valresponse_neg = response.then().assertThat().statusCode(400);
    }
    else {
    	 ValidatableResponse valresponse = response.then().assertThat().statusCode(200);
    }

    System.out.println(response.statusCode());
    System.out.println(response.body().prettyPrint());

    if (response.statusCode() == 200) {
    ResponseBody body = response.body();
    DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
     access_token = jsonContext.read("$.access_token");
     System.out.println("End of API >>>>   /gcb/api/clientCredentials/oauth2/token/sg/gcb");
    }
    
}

public void Print_AccessToken() {
	System.out.println("AccessToken: "+access_token);
	  
}

@Test
public void API_clientCredentials(String CCmethod) {
	
	System.out.println("Start of the API >>>  /gcb/api/clientCredentials/oauth2/token/sg/gcb");
    String payload = "grant_type=client_credentials&scope=/api";
    
    HashMap <String, String> map = new HashMap<String,String>();
    
    map.put("Authorization",authorization_64);
    map.put("uuid",UUID.randomUUID().toString());
   
    RequestSpecification request = RestAssured.given();
    Response response = request
            .when()
            .contentType("application/x-www-form-urlencoded")
            .headers(map)
            .accept(ContentType.APPLICATION_JSON.getMimeType())
            .with()
            .body(payload)
            .post(PropertyFile.getValue(CCmethod));
           // .post("/gcb/api/clientCredentials/oauth2/token/us/gcb");
           

    ValidatableResponse valresponse = response.then().assertThat().statusCode(200);
    System.out.println(response.statusCode());
    System.out.println(response.body().prettyPrint());

    ResponseBody body = response.body();
    DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
     access_token = jsonContext.read("$.access_token");
     System.out.println("End of API >>>>   /gcb/api/clientCredentials/oauth2/token/sg/gcb");
}

@Test
public void API_e2ekey(String EEmethod) {
	 System.out.println("Start of the API >>>  /gcb/api/security/e2eKey");
	 RequestSpecification request = RestAssured.given(); 
     String Authorization = "Bearer" + " " + access_token;
     
     RequestSpecBuilder reqbuilder = new RequestSpecBuilder();
 	reqbuilder.addHeader("Authorization",Authorization);
 	reqbuilder.addHeader("client_id",client_id);
 	reqbuilder.addHeader("client_secret",client_secret);
 	reqbuilder.addHeader("uuid",UUID.randomUUID().toString());
 	reqbuilder.addHeader("countryCode","PL");
 	reqbuilder.addHeader("businessCode","GCB");
 	reqbuilder.addHeader("channelId","SSA");
 	request =reqbuilder.build(); 
 	
     request = reqbuilder.build();
     Response response =  RestAssured.given() 
             .when()
             .accept("application/json")
             .contentType(ContentType.APPLICATION_JSON.getMimeType())
             .spec(request)
             .with()
             .get(PropertyFile.getValue(EEmethod));

     ResponseSpecification responespec;
     ResponseSpecBuilder resbuilder = new ResponseSpecBuilder();
     resbuilder.expectStatusCode(200);
     responespec=resbuilder.build();
     //assertion
     response.then().spec(responespec);
     
     System.out.println(response.statusCode());
     System.out.println(response.body().prettyPrint());
      BizToken =  response.header("bizToken").toString();
      
      EtoEkey key = response.as(EtoEkey.class);
      
      System.out.println("***From Deserialization***"+key.getModulus());
      System.out.println("***From Deserialization***"+key.getExponent());
      String actual = "8aa58f900d7d599ee1cd492c592e0b36ca906d506bde2fbb626db0e9d6d752e0941ea54e3254d5f3a76a45c98c43c6efe4ed687a56b4f6c2fb6337ee4850f7dd54c6f9972b7124565786b62ceb566c36eed79337d799061efd1d115ead0204e426bd51b3110a9651f2438d477a1e75ed73106c8d60818910b205d38bb2968bcd";
      Assert.assertEquals(actual,key.getModulus().toString() );

     System.out.println("End of API >>>>   /gcb/api/security/e2eKey");
	
}

@Test
public void API_ResourceOwner(String ROmethod) {
	System.out.println("Start of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");
	RequestSpecification request = RestAssured.given();
    String username ="SandboxUser1";
    String encrypted_string = "967c2812d7c437b1b8f5dcc887ab64eb24637c9855cc5adeae232c5ae68232802579af2dad68e94c09d3cd6ec3f974e060468150c5ad8c01f39830a723229e8507e9a23433ff273cb6084cf12e838e76e30b2cba1e23ff7be9567726e584f5f442865553df94ddda1ea035e17e3109fcfd9ea3a1d5499a184ca7b79964ca5adfeac59b566f52fb4aa0e7b35983c71b838d16d3dff61715bafde60328e9c5c7fe5ae0e5edc8187d0167206b59df38e718deef822a0499d128d1d6a8464d0871bcdc273658d3c73ebda9c3e348b6e16838942d4630d555ff3ca6569d409b674c37e6b2412e086b644844662dd36fa79acb467207855a39e203552b9f1d2b0e1600";
    String payload = "grant_type=password&scope=/api&username="+username+"&password="+encrypted_string;
    Response response = request
            .when()
            .contentType("application/x-www-form-urlencoded")
            .header("Authorization",authorization_64)
            .accept("application/json")
            .header("uuid",UUID.randomUUID().toString())
            .header("BizToken",BizToken)
            .with()
            .body(payload)
            .post(PropertyFile.getValue(ROmethod));

    System.out.println(response.statusCode());
    System.out.println(response.body().prettyPrint());

    ResponseBody body = response.body();
    DocumentContext jsonContext_new = com.jayway.jsonpath.JsonPath.parse(body.print());
     access_token_new= jsonContext_new.read("$.access_token");

     Authorization_new = "Bearer "+access_token_new;

    System.out.println("End of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");
	
}

@Test
public void API_ResourceOwner(String ROmethod,String username,String password, String control) {
	System.out.println("Start of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");
	RequestSpecification request = RestAssured.given();
    //String username1 ="SandboxUser1";
   // String encrypted_string = "967c2812d7c437b1b8f5dcc887ab64eb24637c9855cc5adeae232c5ae68232802579af2dad68e94c09d3cd6ec3f974e060468150c5ad8c01f39830a723229e8507e9a23433ff273cb6084cf12e838e76e30b2cba1e23ff7be9567726e584f5f442865553df94ddda1ea035e17e3109fcfd9ea3a1d5499a184ca7b79964ca5adfeac59b566f52fb4aa0e7b35983c71b838d16d3dff61715bafde60328e9c5c7fe5ae0e5edc8187d0167206b59df38e718deef822a0499d128d1d6a8464d0871bcdc273658d3c73ebda9c3e348b6e16838942d4630d555ff3ca6569d409b674c37e6b2412e086b644844662dd36fa79acb467207855a39e203552b9f1d2b0e1600";
	String encrypted_string = PropertyFile.getValue(password);
	if (control.toString().equals("Yes")) {
		encrypted_string = PropertyFile.getValue("invalidpassword");
	}
	String payload = "grant_type=password&scope=/api&username="+username+"&password="+encrypted_string;
	
	HashMap <String, String> map = new HashMap<String,String>();
	map.put("Authorization",authorization_64);
	map.put("uuid",UUID.randomUUID().toString());
	map.put("BizToken",BizToken);
    Response response = request
            .when()
            .contentType("application/x-www-form-urlencoded")
            .accept("application/json")
            .headers(map)
            .with()
            .body(payload)
            .post(PropertyFile.getValue(ROmethod));
    
 if (username.toString().equals("SandboxUser123")||(encrypted_string.toString().equals("12345"))){
    	
    	ValidatableResponse valresponse_neg = response.then().assertThat().statusCode(401);
    }
    else {
    	 ValidatableResponse valresponse = response.then().assertThat().statusCode(200);
    }

    System.out.println(response.statusCode());
    System.out.println(response.body().prettyPrint());
    
    if (response.statusCode() == 200) {

    ResponseBody body = response.body();
    DocumentContext jsonContext_new = com.jayway.jsonpath.JsonPath.parse(body.print());
     access_token_new= jsonContext_new.read("$.access_token");

     Authorization_new = "Bearer "+access_token_new;
     
     ResourceOwner resown = response.as(ResourceOwner.class);
     System.out.println("***From Deserialisation***"+ resown.getAccess_token());
     Assert.assertEquals("Bearer", resown.getToken_type());
     
    System.out.println("End of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");
	
    }	
}




@Test
public void API_Accounts(String ACCmethod,String Authorization_neg,String clientid_neg,String control) {
	 System.out.println("Start of the API >>>  /gcb/api//v1/accounts");
	 
	 if (control.toString().equals("Yes")) {
		 Authorization_new = Authorization_neg;
		}
	 
	 else if(control.toString().equals("No")) {
		 client_id =clientid_neg;
	 }

	 RequestSpecification request= RestAssured.given();

	 Response response = request
             .when()
             .header("Authorization",Authorization_new)
             .accept("application/json")
             .header("uuid",UUID.randomUUID().toString())
             .header("client_id",client_id)
             .with()
             .get(PropertyFile.getValue(ACCmethod));
             //.get("/gcb/api/v1/accounts");
	 
	 if (Authorization_neg.toString().equals("145678")||(clientid_neg.toString().equals("123456"))){
	    	
	    	ValidatableResponse valresponse_neg = response.then().assertThat().statusCode(401);
	    }
	    else {
	    	 ValidatableResponse valresponse = response.then().assertThat().statusCode(200);
	    }

     System.out.println(response.statusCode());
     System.out.println(response.body().prettyPrint());

     System.out.println("End of the API >>>  /gcb/api/v1/accounts");

 }

@Test
public void API_CARD_Details(String cardMethod,String cardfunction) {

    RequestSpecification request = RestAssured.given();
 
    Response response = request
            .when()
            .header("Authorization",Authorization_new)
            .accept("application/json")
            .header("uuid",UUID.randomUUID().toString())
            .header("client_id",client_id)
            .queryParam("cardFunction", cardfunction.toString())
            .with()
            .get(PropertyFile.getValue(cardMethod));
            //.get(cardMethod);
    
    if (cardfunction.toString().equals("cards")){
    	
    	ValidatableResponse valresponse_neg = response.then().assertThat().statusCode(400);
    }
    else {
    	 ValidatableResponse valresponse = response.then().assertThat().statusCode(200);
    }

    System.out.println(response.statusCode());
    System.out.println(response.body().prettyPrint());

    System.out.println("End of the API >>>  /gcb/api/v1/cards");

}


    @Test
    public void API_Accounts_Transactions(String ACCmethod,String Trnx_status, String control) {

        System.out.println("Start of the API >>>  /gcb/api//v1/accounts");

        RequestSpecification request = RestAssured.given();

        Response response = request
                .when()
                .header("Authorization", Authorization_new)
                .accept("application/json")
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .get(PropertyFile.getValue(ACCmethod));


        System.out.println(response.statusCode());
        System.out.println("**************************************************************************************");
        System.out.println(response.body().prettyPrint());
        System.out.println("**************************************************************************************");
        System.out.println("End of the API >>>  /gcb/api/v1/accounts");

        //String accountsWithTransactions = "{    \"accountGroupSummary\": [        {            \"accountGroup\": \"SAVINGS_AND_INVESTMENTS\",            \"accounts\": [                {                    \"savingsAccountSummary\": {                        \"productName\": \"KONTO OSZCZEDNOSCIOWE BHD\",                        \"productCode\": \"0007_SSC20\",                        \"displayAccountNumber\": \"XXXXXX7556\",                        \"accountId\": \"63345745333946686651706662332f634a375573535370634d706a6574315a2b534565795270615a3533513d\",                        \"currencyCode\": \"BHD\",                        \"accountClassification\": \"ASSET\",                        \"accountStatus\": \"ACTIVE\",                        \"currentBalance\": 2582.18,                        \"availableBalance\": 2582.18                    }                },                {                    \"savingsAccountSummary\": {                        \"productName\": \"KONTO OSZCZEDNOSCIOWE BHD\",                        \"productCode\": \"0007_SSC20\",                        \"displayAccountNumber\": \"XXXXXX7557\",                        \"accountId\": \"809433290433946686651706662332f634a375573535370634d706a6574315a2b534565795270615a3533664d\",                        \"currencyCode\": \"BHD\",                        \"accountClassification\": \"ASSET\",                        \"accountStatus\": \"ACTIVE\",                        \"currentBalance\": 4582.18,                        \"availableBalance\": 4582.18                    }                }            ]        },        {            \"accountGroup\": \"CHECKING\",            \"accounts\": [                {                    \"checkingAccountSummary\": {                        \"productName\": \"SUB. WALUTOWE CITIGOLD USD\",                        \"productCode\": \"0001_SAG00\",                        \"displayAccountNumber\": \"XXXXXX3260\",                        \"accountId\": \"4556622f58466e6e4d67717537337969422b746c66646a6865484d7470736d683436393178494c2b3563566d\",                        \"currencyCode\": \"USD\",                        \"accountClassification\": \"ASSET\",                        \"accountStatus\": \"ACTIVE\",                        \"currentBalance\": 91352.21,                        \"availableBalance\": 91352.21                    }                }            ]        },        {            \"accountGroup\": \"CREDIT_CARD\",            \"accounts\": [                {                    \"creditCardAccountSummary\": {                        \"productName\": \"CITIBANK VISA PLATINUM\",                        \"productCode\": \"0004_VC400\",                        \"displayAccountNumber\": \"XXXXXXXXXXXX2875\",                        \"accountId\": \"743552522b34655255764b546638573230424439313956744f4a69414b71584e6a764a654e5272762b44633d\",                        \"currencyCode\": \"BHD\",                        \"accountClassification\": \"LIABILITY\",                        \"accountStatus\": \"ACTIVE\",                        \"outstandingBalance\": 950,                        \"availableCredit\": 950,                        \"creditLimit\": 5400,                        \"minimumDueAmount\": 500,                        \"paymentDueDate\": \"2016-09-25\",                        \"alternateCurrency\": \"GBP\",                        \"alternateCurrencyCurrentBalance\": 0,                        \"cardHolderType\": \"PRIMARY\"                    }                },                {                    \"creditCardAccountSummary\": {                        \"productName\": \"CITIBANK VISA PLATINUM\",                        \"productCode\": \"0004_VC400\",                        \"displayAccountNumber\": \"XXXXXX5753\",                        \"accountId\": \"75334a554163504f696b485862753135706a3571764332357441476832723073705a647469497372776f544d\",                        \"currencyCode\": \"BHD\",                        \"accountClassification\": \"LIABILITY\",                        \"accountStatus\": \"ACTIVE\",                        \"outstandingBalance\": 3045.87,                        \"availableCredit\": 3045.87,                        \"creditLimit\": 10300,                        \"minimumDueAmount\": 150,                        \"paymentDueDate\": \"2017-07-07\",                        \"alternateCurrency\": \"GBP\",                        \"alternateCurrencyCurrentBalance\": 0,                        \"cardHolderType\": \"SUPPLEMENTARY\"                    }                }            ]        }    ]}";
        //GsonBuilder builder = new GsonBuilder();
        //Gson gson = builder.create();
        //AccountGroupSummaryList accountGroupSummaryList = gson.fromJson(accountsWithTransactions, AccountGroupSummaryList.class);

        AccountGroupSummaryList accountGroupSummaryList = response.getBody().as(AccountGroupSummaryList.class);


        for(int accountGroupSummaryListIndex = 0; accountGroupSummaryListIndex < accountGroupSummaryList.getAccountGroupSummary().size(); accountGroupSummaryListIndex++)
            {
                AccountGroupSummary accountGroupSummary = accountGroupSummaryList.getAccountGroupSummary().get(accountGroupSummaryListIndex);
                for(int accountIndex = 0; accountIndex < accountGroupSummary.getAccounts().size(); accountIndex++)
                {
                    String accountId = null;
                    try {
                        accountId = accountGroupSummary.getAccounts().get(accountIndex).getSavingsAccountSummary().getAccountId();
                        System.out.println(accountGroupSummary.getAccounts().get(accountIndex).getSavingsAccountSummary().getAccountId());
                    }catch(Exception e){
                    }

                    try {
                        accountId = accountGroupSummary.getAccounts().get(accountIndex).getCheckingAccountSummary().getAccountId();
                        System.out.println(accountGroupSummary.getAccounts().get(accountIndex).getCheckingAccountSummary().getAccountId());
                    }catch(Exception e){
                    }

                    try{
                        accountId = accountGroupSummary.getAccounts().get(accountIndex).getCreditCardAccountSummary().getAccountId();
                        System.out.println(accountGroupSummary.getAccounts().get(accountIndex).getCreditCardAccountSummary().getAccountId());
                    }catch(Exception e){
                    }

                    if (control.toString().equals("Yes")) {
                    	accountId = "123456";
               		}
                    System.out.println("Start of the API >>> /gcb/api//v1/accounts/"+accountId+"/transactions");
                    request = RestAssured.given();
                   
                    response = request
                            .when()
                            .header("Authorization",Authorization_new)
                            .accept("application/json")
                            .header("uuid",UUID.randomUUID().toString())
                            .header("Client_id",client_id)
                            .queryParam("transactionStatus", Trnx_status.toString())
                            .with()
                            .get("/gcb/api/v1/accounts/"+accountId+"/transactions");
                    
                    //response.then().statusCode(anyOf(is(200),is(404));
                    
                    if (accountId.toString().equals("123456")||(Trnx_status.toString().equals("Acc_posted"))){
            	    	
            	    	ValidatableResponse valresponse_neg = response.then().assertThat().statusCode(400);
            	    }

                    System.out.println(response.statusCode());
                    System.out.println(response.body().prettyPrint());
                    System.out.println("End of the API >>>  /gcb/api//v1/accounts/"+accountId+"/transactions");

                }
        }
    }


    @Test
        public void PDT_Confirmation_API(String ACCmethod, String control_Auth,String control_dest_account,String control_chargebearer,String control_flowid) {
        System.out.println("Start of the API >>>  /gcb/api//v1/accounts");

        RequestSpecification request = RestAssured.given();

        Response response = request
                .when()
                .header("Authorization", Authorization_new)
                .accept("application/json")
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .get(PropertyFile.getValue(ACCmethod));


        System.out.println(response.statusCode());
        System.out.println("**************************************************************************************");
        System.out.println(response.body().prettyPrint());
        System.out.println("**************************************************************************************");
        System.out.println("End of the API >>>  /gcb/api/v1/accounts");



        System.out.println("Start of the API >>>  /gcb/api/v1/cards?cardFunction=ALL");
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization_new)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .with()
                .get("/gcb/api/v1/cards?cardFunction=ALL");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        System.out.println("End of the API >>>  /gcb/api/v1/cards?cardFunction=ALL");


        String accountId="809433290433946686651706662332f634a375573535370634d706a6574315a2b534565795270615a3533664d";
        System.out.println("Start of the API >>> /gcb/api//v1/accounts/"+accountId+"/transactions?transactionStatus=POSTED");
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization_new)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .queryParam("transactionStatus","POSTED")
                .with()
                .get("/gcb/api/v1/accounts/"+accountId+"/transactions");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        System.out.println("End of the API >>>  /gcb/api//v1/accounts/"+accountId+"/transactions?transactionStatus=POSTED");

        System.out.println("Start of the API >>>   /gcb/api/v1/moneyMovement/personalDomesticTransfers/destinationAccounts/sourceAccounts");
        if (control_Auth.toString().equals("Yes")) {
        	Authorization_new = "123456";
   		}
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization_new)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .with()
                .get("/gcb/api/v1/moneyMovement/personalDomesticTransfers/destinationAccounts/sourceAccounts");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        System.out.println("End of the API >>>  /gcb/api/v1/moneyMovement/personalDomesticTransfers/destinationAccounts/sourceAccounts");


        System.out.println("Start of the API >>>  /gcb/api/v1/moneyMovement/personalDomesticTransfers/preprocess");
        /*String payload_PDT="{\"sourceAccountId\": \"788852522b34655255764b546638573230424439313956744f4a69414b71584e6a764a654e5272762b44633d\",\n" +
                "  \"transactionAmount\": \"400\",\n" +
                "  \"transferCurrencyIndicator\": \"SOURCE_ACCOUNT_CURRENCY\",\n" +
                "  \"destinationAccountId\": \"90007745333946686651706662332f634a375574544020634d706a6574315a2b534565795270615a3533513d\",\n" +
                "  \"chargeBearer\": \"BENEFICIARY\",\n" +
                "  \"fxDealReferenceNumber\": \"123456\",\n" +
                "  \"remarks\": \"1\"}";

        System.out.println("The payload for domestic transfer >>>>>>>>>>>>" + payload_PDT);
*/

/*
        PDT_Payload payload = new PDT_Payload();
        payload.setSourceAccountId("788852522b34655255764b546638573230424439313956744f4a69414b71584e6a764a654e5272762b44633d");
        payload.setTransactionAmount("400");
        payload.setTransferCurrencyIndicator("SOURCE_ACCOUNT_CURRENCY");
        payload.setDestinationAccountId("90007745333946686651706662332f634a375574544020634d706a6574315a2b534565795270615a3533513d");
        payload.setChargeBearer("BENEFICIARY");
        payload.setFxDealReferenceNumber("123456");
        payload.setRemarks("1");
*/
        	String destinationAccountId = "90007745333946686651706662332f634a375574544020634d706a6574315a2b534565795270615a3533513d";
        	String chargeBearer= "BENEFICIARY";
        	if (control_dest_account.toString().equals("Yes")) {
        		destinationAccountId = "123456";
       		}
        	else if(control_chargebearer.toString().equals("Yes")) {
        		chargeBearer = "ours";
        	}
        JSONObject payload = new JSONObject();
        payload.put("sourceAccountId","788852522b34655255764b546638573230424439313956744f4a69414b71584e6a764a654e5272762b44633d");
        payload.put("transactionAmount","400");
        payload.put("transferCurrencyIndicator","SOURCE_ACCOUNT_CURRENCY");
        payload.put("destinationAccountId",destinationAccountId);
        payload.put("chargeBearer",chargeBearer);
        payload.put("fxDealReferenceNumber","123456");
        payload.put("remarks","1");


        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization_new)
                .accept("application/json")
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .with()
                .body(payload.toString())
                .post("/gcb/api/v1/moneyMovement/personalDomesticTransfers/preprocess");
       
       if(response.statusCode()==200) {
        System.out.println(response.statusCode());
        ResponseBody body2 = response.body();
        DocumentContext jsonContext2 = com.jayway.jsonpath.JsonPath.parse(body2.print());
        String controlFlowID = jsonContext2.read("$.controlFlowId");

        System.out.println("Start of the API >>> /gcb/api/v1/moneyMovement/personalDomesticTransfers");
        
        if (control_flowid.toString().equals("Yes")) {
        	controlFlowID = "123456";
   		}
        
        
        String payload_ControlFlowID="{\n" +
                "  \"controlFlowId\": \""+controlFlowID+"\"\n" +
                "}";
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization_new)
                .accept("application/json")
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .with()
                .body(payload_ControlFlowID)
                .post("/gcb/api/v1/moneyMovement/personalDomesticTransfers");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        System.out.println("End of the API >>>  /gcb/api/v1/moneyMovement/personalDomesticTransfers");

    }

    }
  


}

