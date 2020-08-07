package com.api.service;

import com.jayway.jsonpath.DocumentContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.UUID;

public class CITIBANK_Accounts_Transactions_BillPayments_Cards {
    String authorization_64 = "Basic YzlhYTIwNzYtMTU2OS00ZmU2LWE5MDItODkwMDQxZTNhYjBhOnRIOGdUOGdDNmtWMmJSM2hLNm1NNWtMM2RUMmNRNGJKOGlONGVIM25BNndXMmFBN3VK";
    String client_id="c9aa2076-1569-4fe6-a902-890041e3ab0a";
    String client_secret="tH8gT8gC6kV2bR3hK6mM5kL3dT2cQ4bJ8iN4eH3nA6wW2aA7uJ";


    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = "https://sandbox.apihub.citi.com";
    }

    @Test
    public void Initiate_the_Account_API_with_valid_fields_and_verify_the_Expected_Result() {

        System.out.println("Start of the API >>>  /gcb/api/clientCredentials/oauth2/token/sg/gcb");

        String payload = "grant_type=client_credentials&scope=/api";
        RequestSpecification request = RestAssured.given();
        Response response = request
                .when()
                .contentType("application/x-www-form-urlencoded")
                .header("Authorization",authorization_64)
                .header("uuid",UUID.randomUUID().toString())
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .with()
                .body(payload)
                .post("/gcb/api/clientCredentials/oauth2/token/us/gcb");


        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        ResponseBody body = response.body();
        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token = jsonContext.read("$.access_token");


        System.out.println("End of API >>>>   /gcb/api/clientCredentials/oauth2/token/sg/gcb");


        System.out.println("Start of the API >>>  /gcb/api/security/e2eKey");
        request = RestAssured.given();
        String Authorization = "Bearer" + " " + access_token;
        response = request
                .when()
                .header("Authorization",Authorization)
                .header("client_id",client_id)
                .header("client_secret",client_secret)
                .header("uuid",UUID.randomUUID().toString())
                .accept("application/json")
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("countryCode","PL")
                .header("businessCode","GCB")
                .header("channelId","SSA")
                .with()
                .get("/gcb/api/security/e2eKey");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        String BizToken =  response.header("bizToken").toString();

        System.out.println("End of API >>>>   /gcb/api/security/e2eKey");


        System.out.println("Start of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");
        request = RestAssured.given();
        String username ="SandboxUser1";
        String encrypted_string = "967c2812d7c437b1b8f5dcc887ab64eb24637c9855cc5adeae232c5ae68232802579af2dad68e94c09d3cd6ec3f974e060468150c5ad8c01f39830a723229e8507e9a23433ff273cb6084cf12e838e76e30b2cba1e23ff7be9567726e584f5f442865553df94ddda1ea035e17e3109fcfd9ea3a1d5499a184ca7b79964ca5adfeac59b566f52fb4aa0e7b35983c71b838d16d3dff61715bafde60328e9c5c7fe5ae0e5edc8187d0167206b59df38e718deef822a0499d128d1d6a8464d0871bcdc273658d3c73ebda9c3e348b6e16838942d4630d555ff3ca6569d409b674c37e6b2412e086b644844662dd36fa79acb467207855a39e203552b9f1d2b0e1600";
        payload = "grant_type=password&scope=/api&username="+username+"&password="+encrypted_string;
        response = request
                .when()
                .contentType("application/x-www-form-urlencoded")
                .header("Authorization",authorization_64)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("BizToken",BizToken)
                .with()
                .body(payload)
                .post("/gcb/api/password/oauth2/token/bh/gcb");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        body = response.body();
        DocumentContext jsonContext_new = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token_new= jsonContext_new.read("$.access_token");

        String Authorization_new = "Bearer "+access_token_new;

        System.out.println("End of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");




        System.out.println("Start of the API >>>  /gcb/api//v1/accounts");

        request= RestAssured.given();

        response = request
                .when()
                .header("Authorization",Authorization_new)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("client_id",client_id)
                .with()
                .get("/gcb/api/v1/accounts");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        System.out.println("End of the API >>>  /gcb/api/v1/accounts");

    }


    @Test
    public void Initiate_the_GET_Card_API_with_valid_fields_and_verify_the_Expected_Result() {
        System.out.println("Start of the API >>>  /gcb/api/clientCredentials/oauth2/token/sg/gcb");

        String payload = "grant_type=client_credentials&scope=/api";
        RequestSpecification request = RestAssured.given();
        Response response = request
                .when()
                .contentType("application/x-www-form-urlencoded")
                .header("Authorization",authorization_64)
                .header("uuid",UUID.randomUUID().toString())
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .with()
                .body(payload)
                .post("/gcb/api/clientCredentials/oauth2/token/us/gcb");


        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        ResponseBody body = response.body();
        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token = jsonContext.read("$.access_token");


        System.out.println("End of API >>>>   /gcb/api/clientCredentials/oauth2/token/sg/gcb");


        System.out.println("Start of the API >>>  /gcb/api/security/e2eKey");
        request = RestAssured.given();
        String Authorization = "Bearer" + " " + access_token;
        response = request
                .when()
                .header("Authorization",Authorization)
                .header("client_id",client_id)
                .header("client_secret",client_secret)
                .header("uuid",UUID.randomUUID().toString())
                .accept("application/json")
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("countryCode","PL")
                .header("businessCode","GCB")
                .header("channelId","SSA")
                .with()
                .get("/gcb/api/security/e2eKey");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        String BizToken =  response.header("bizToken").toString();

        System.out.println("End of API >>>>   /gcb/api/security/e2eKey");


        System.out.println("Start of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");
        request = RestAssured.given();
        String username ="SandboxUser1";
        String encrypted_string = "967c2812d7c437b1b8f5dcc887ab64eb24637c9855cc5adeae232c5ae68232802579af2dad68e94c09d3cd6ec3f974e060468150c5ad8c01f39830a723229e8507e9a23433ff273cb6084cf12e838e76e30b2cba1e23ff7be9567726e584f5f442865553df94ddda1ea035e17e3109fcfd9ea3a1d5499a184ca7b79964ca5adfeac59b566f52fb4aa0e7b35983c71b838d16d3dff61715bafde60328e9c5c7fe5ae0e5edc8187d0167206b59df38e718deef822a0499d128d1d6a8464d0871bcdc273658d3c73ebda9c3e348b6e16838942d4630d555ff3ca6569d409b674c37e6b2412e086b644844662dd36fa79acb467207855a39e203552b9f1d2b0e1600";
        payload = "grant_type=password&scope=/api&username="+username+"&password="+encrypted_string;
        response = request
                .when()
                .contentType("application/x-www-form-urlencoded")
                .header("Authorization",authorization_64)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("BizToken",BizToken)
                .with()
                .body(payload)
                .post("/gcb/api/password/oauth2/token/bh/gcb");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        body = response.body();
        DocumentContext jsonContext_new = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token_new= jsonContext_new.read("$.access_token");

        String Authorization_new = "Bearer "+access_token_new;

        System.out.println("End of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");




        System.out.println("Start of the API >>>  /gcb/api//v1/cards");

        request= RestAssured.given();

        response = request
                .when()
                .header("Authorization",Authorization_new)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("client_id",client_id)
                .queryParam("cardFunction", "ALL")
                .with()
                .get("/gcb/api/v1/cards");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        System.out.println("End of the API >>>  /gcb/api/v1/cards");

    }

    @Test
    public void Initiate_the_GET_Transactions_API_with_valid_fields_and_verify_the_Expected_Result() {

        System.out.println("Start of the API >>>  /gcb/api/clientCredentials/oauth2/token/sg/gcb");

        String payload = "grant_type=client_credentials&scope=/api";
        RequestSpecification request = RestAssured.given();
        Response response = request
                .when()
                .contentType("application/x-www-form-urlencoded")
                .header("Authorization",authorization_64)
                .header("uuid",UUID.randomUUID().toString())
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .with()
                .body(payload)
                .post("/gcb/api/clientCredentials/oauth2/token/us/gcb");


        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        ResponseBody body = response.body();
        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token = jsonContext.read("$.access_token");


        System.out.println("End of API >>>>   /gcb/api/clientCredentials/oauth2/token/sg/gcb");


        System.out.println("Start of the API >>>  /gcb/api/security/e2eKey");
        request = RestAssured.given();
        String Authorization = "Bearer" + " " + access_token;
        response = request
                .when()
                .header("Authorization",Authorization)
                .header("client_id",client_id)
                .header("client_secret",client_secret)
                .header("uuid",UUID.randomUUID().toString())
                .accept("application/json")
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("countryCode","PL")
                .header("businessCode","GCB")
                .header("channelId","SSA")
                .with()
                .get("/gcb/api/security/e2eKey");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        String BizToken =  response.header("bizToken").toString();

        System.out.println("End of API >>>>   /gcb/api/security/e2eKey");


        System.out.println("Start of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");
        request = RestAssured.given();
        String username ="SandboxUser1";
        String encrypted_string = "967c2812d7c437b1b8f5dcc887ab64eb24637c9855cc5adeae232c5ae68232802579af2dad68e94c09d3cd6ec3f974e060468150c5ad8c01f39830a723229e8507e9a23433ff273cb6084cf12e838e76e30b2cba1e23ff7be9567726e584f5f442865553df94ddda1ea035e17e3109fcfd9ea3a1d5499a184ca7b79964ca5adfeac59b566f52fb4aa0e7b35983c71b838d16d3dff61715bafde60328e9c5c7fe5ae0e5edc8187d0167206b59df38e718deef822a0499d128d1d6a8464d0871bcdc273658d3c73ebda9c3e348b6e16838942d4630d555ff3ca6569d409b674c37e6b2412e086b644844662dd36fa79acb467207855a39e203552b9f1d2b0e1600";
        payload = "grant_type=password&scope=/api&username="+username+"&password="+encrypted_string;
        response = request
                .when()
                .contentType("application/x-www-form-urlencoded")
                .header("Authorization",authorization_64)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("BizToken",BizToken)
                .with()
                .body(payload)
                .post("/gcb/api/password/oauth2/token/bh/gcb");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());

        body = response.body();
        DocumentContext jsonContext_new = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token_new= jsonContext_new.read("$.access_token");

        String Authorization_new = "Bearer "+access_token_new;

        System.out.println("End of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");




        String accountId="809433290433946686651706662332f634a375573535370634d706a6574315a2b534565795270615a3533664d";
        System.out.println("Start of the API >>> /gcb/api//v1/accounts/"+accountId+"/transactions");
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization_new)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .queryParam("transactionStatus", "POSTED")
                .with()
                .get("/gcb/api/v1/accounts/"+accountId+"/transactions");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        System.out.println("End of the API >>>  /gcb/api//v1/accounts/"+accountId+"/transactions");

    }


    @Test
    public void Initiate_the_PDT_Confirmation_API_with_valid_fields_and_verify_the_Expected_Result() {

        String authorization = "Basic OGZhNTBjOTMtNjQ3MS00ZjU4LThjN2YtYmMzZmE4NDZlMjM2OnBUOHBZNWtBOGtDN3hWMHVNN3FCMHJWOGtON2xWNnRENHZNNWNLMmdKMGtOM3NMN3JVDQo=";
        String client_id = "8fa50c93-6471-4f58-8c7f-bc3fa846e236";
        String client_secret="pT8pY5kA8kC7xV0uM7qB0rV8kN7lV6tD4vM5cK2gJ0kN3sL7rU";
        String uuid= UUID.randomUUID().toString();

        System.out.println("Start of the API >>>  /gcb/api/clientCredentials/oauth2/token/sg/gcb");
        String payload = "grant_type=client_credentials&scope=/api";
        RequestSpecification request = RestAssured.given();
        Response response = request
                .when()
                .contentType("application/x-www-form-urlencoded")
                .header("Authorization",authorization)
                .header("uuid",UUID.randomUUID().toString())
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .with()
                .body(payload)
                .post("/gcb/api/clientCredentials/oauth2/token/us/gcb");

        System.out.println(response.statusCode());
        ResponseBody body = response.body();
        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token = jsonContext.read("$.access_token");
        System.out.println("End of API >>>>   /gcb/api/clientCredentials/oauth2/token/sg/gcb");


        System.out.println("Start of the API >>>  /gcb/api/security/e2eKey");
        request = RestAssured.given();
        String Authorization = "Bearer" + " " + access_token;
        response = request
                .when()
                .header("Authorization",Authorization)
                .header("client_id",client_id)
                .header("client_secret",client_secret)
                .header("uuid",UUID.randomUUID().toString())
                .accept("application/json")
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("countryCode","PL")
                .header("businessCode","GCB")
                .header("channelId","SSA")
                .with()
                .get("/gcb/api/security/e2eKey");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        String BizToken =  response.header("bizToken").toString();
        System.out.println("End of API >>>>   /gcb/api/security/e2eKey");


        System.out.println("Start of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");
        request = RestAssured.given();
        String username ="SandboxUser1";
        String encrypted_string = "967c2812d7c437b1b8f5dcc887ab64eb24637c9855cc5adeae232c5ae68232802579af2dad68e94c09d3cd6ec3f974e060468150c5ad8c01f39830a723229e8507e9a23433ff273cb6084cf12e838e76e30b2cba1e23ff7be9567726e584f5f442865553df94ddda1ea035e17e3109fcfd9ea3a1d5499a184ca7b79964ca5adfeac59b566f52fb4aa0e7b35983c71b838d16d3dff61715bafde60328e9c5c7fe5ae0e5edc8187d0167206b59df38e718deef822a0499d128d1d6a8464d0871bcdc273658d3c73ebda9c3e348b6e16838942d4630d555ff3ca6569d409b674c37e6b2412e086b644844662dd36fa79acb467207855a39e203552b9f1d2b0e1600";
        payload = "grant_type=password&scope=/api&username="+username+"&password="+encrypted_string;
        response = request
                .when()
                .contentType("application/x-www-form-urlencoded")
                .header("Authorization",authorization)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("BizToken",BizToken)
                .with()
                .body(payload)
                .post("/gcb/api/password/oauth2/token/bh/gcb");

        System.out.println(response.statusCode());
        ResponseBody body1 = response.body();
        DocumentContext jsonContext1 = com.jayway.jsonpath.JsonPath.parse(body1.print());
        String access_token1 = jsonContext1.read("$.access_token");
        System.out.println("End of the API >>>  /gcb/api/password/oauth2/token/bh/gcb");


        System.out.println("Start of the API >>>  /gcb/api/v1/accounts");
        String Authorization1 = "Bearer" + " " + access_token1;
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization1)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .with()
                .get("/gcb/api/v1/accounts");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        System.out.println("End of the API >>>  /gcb/api/v1/accounts");


        System.out.println("Start of the API >>>  /gcb/api/v1/cards?cardFunction=ALL");
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization1)
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
                .header("Authorization",Authorization1)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .with()
                .get("/gcb/api//v1/accounts/"+accountId+"/transactions?transactionStatus=POSTED");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        System.out.println("End of the API >>>  /gcb/api//v1/accounts/"+accountId+"/transactions?transactionStatus=POSTED");

        System.out.println("Start of the API >>>   /gcb/api/v1/moneyMovement/personalDomesticTransfers/destinationAccounts/sourceAccounts");
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization1)
                .accept("application/json")
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .with()
                .get("/gcb/api/v1/moneyMovement/personalDomesticTransfers/destinationAccounts/sourceAccounts");

        System.out.println(response.statusCode());
        System.out.println(response.body().prettyPrint());
        System.out.println("End of the API >>>  /gcb/api/v1/moneyMovement/personalDomesticTransfers/destinationAccounts/sourceAccounts");


        System.out.println("Start of the API >>>  /gcb/api/v1/moneyMovement/personalDomesticTransfers/preprocess");
        String payload_PDT="{\"sourceAccountId\": \"788852522b34655255764b546638573230424439313956744f4a69414b71584e6a764a654e5272762b44633d\",\n" +
                "  \"transactionAmount\": \"400\",\n" +
                "  \"transferCurrencyIndicator\": \"SOURCE_ACCOUNT_CURRENCY\",\n" +
                "  \"destinationAccountId\": \"90007745333946686651706662332f634a375574544020634d706a6574315a2b534565795270615a3533513d\",\n" +
                "  \"chargeBearer\": \"BENEFICIARY\",\n" +
                "  \"fxDealReferenceNumber\": \"123456\",\n" +
                "  \"remarks\": \"1\"}";

        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization1)
                .accept("application/json")
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .header("uuid",UUID.randomUUID().toString())
                .header("Client_id",client_id)
                .with()
                .body(payload_PDT)
                .post("/gcb/api/v1/moneyMovement/personalDomesticTransfers/preprocess");

        System.out.println(response.statusCode());
        ResponseBody body2 = response.body();
        DocumentContext jsonContext2 = com.jayway.jsonpath.JsonPath.parse(body2.print());
        String controlFlowID = jsonContext2.read("$.controlFlowId");
        System.out.println("End of the API >>>  /gcb/api/v1/moneyMovement/personalDomesticTransfers/preprocess");



        System.out.println("Start of the API >>> /gcb/api/v1/moneyMovement/personalDomesticTransfers");
        String payload_ControlFlowID="{\n" +
                "  \"controlFlowId\": \""+controlFlowID+"\"\n" +
                "}";
        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization",Authorization1)
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
