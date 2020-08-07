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

public class CITIBANK_PAYMENTS3_KRISHNA_SG {


    public CITIBANK_PAYMENTS3_KRISHNA_SG() {
    }

    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = "https://sandbox.apihub.citi.com";
    }


    @Test(priority = 1)
    public void generateTheAccessToken() {

        String authorization = "Basic ODMwZjVlMjctYmMwNy00YmVjLTk1NDMtMGMwYmY5YmE3YjljOkMxaFg2aUcwY1gyaUIzdE44eEM3eVEwZ1U2eEQwc0YxZ1Q1eUc2ZkE0cks0aEU1akEy";
        String client_id = "830f5e27-bc07-4bec-9543-0c0bf9ba7b9c"; // 3906dd6d-534b-4d20-81d7-0e78848013a3
        String uri = "/gcb/api/clientCredentials/oauth2/token/hk/gcb";

        RequestSpecification request = RestAssured.given();
        String formUrlEncodedPayload = "grant_type=client_credentials&scope=/api";
        Response response = request
                .when()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", authorization)
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .with()
                .body(formUrlEncodedPayload)
                .post(uri);


        ResponseBody body = response.body();
        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token = jsonContext.read("$.access_token");
        System.out.println("The access token is <<<<<<<<<<<<<<<  : " + access_token);


        uri = "/gcb/api/v1/apac/rewards/linkage";
        String payload = "{" +
                "\t\t\"lastFourDigitsCardNumber\": \"7411\",\n" +
                "\t\t\"citiCardHolderPhoneNumber\": \"2608191234\",\n" +
                "\t\t\"merchantCustomerReferenceId\": \"2608191234111\"\n" +
                "}";

        request = RestAssured.given();
        response = request
                .when()
                .header("Authorization", "Bearer " + access_token)
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .accept(ContentType.APPLICATION_JSON.getMimeType())
                .header("accept-language", "en-us")
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(payload)//add pet object
                .post(uri);


        body = response.body();
        jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String rewardLinkCode = jsonContext.read("$.rewardLinkCode");
        System.out.println("<<<<<<<<<<<<<<<" + rewardLinkCode);

        uri = "/gcb/api/v1/apac/rewards/" + rewardLinkCode + "/activations";

        payload = "{" +
                "      \"linkageConfirmationCode\":\"735937\"" +
                "    }";

        request = RestAssured.given();
        response = request
                .when()
                .header("Content-Type","application/json")
                .header("Authorization", "Bearer " + access_token)
                .header("Accept", "application/json")
                .header("Accept-Language", "en-us")
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(payload).log().all()
                .put(uri);
        System.out.println(response.statusCode());


        uri = "/gcb/api/v1/apac/rewards/" + rewardLinkCode + "/pointBalance";
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


        uri = "/gcb/api/v1/apac/rewards/" + rewardLinkCode + "/redemption";

        payload = "{\n" +
                "  \"transactionReferenceNumber\": \"1668454de62345\",\n" +
                "  \"redemptionOrder\": {\n" +
                "    \"transactionAmount\": 5.42,\n" +
                "    \"currencyCode\": \"HKD\",\n" +
                "    \"pointsToRedeem\": 2006,\n" +
                "    \"transactionDescription\": \"Completed\"\n" +
                "  }\n" +
                "}";

        request = RestAssured.given();
        response = request
                .when()
                .header("Content-Type","application/json")
                .header("Authorization", "Bearer " + access_token)
                .header("Accept", "application/json")
                .header("Accept-Language", "en-us")
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(payload)
                .post(uri);
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());


        uri = "/gcb/api/v1/apac/rewards/" + rewardLinkCode + "/pointBalance";
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

        uri = "/gcb/api/v1/apac/rewards/" + rewardLinkCode + "/redemption";
        payload = "{\n" +
                "  \"transactionReferenceNumber\": \"1668454de62345\",\n" +
                "  \"redemptionOrder\": {\n" +
                "    \"transactionAmount\": 5.42,\n" +
                "    \"currencyCode\": \"HKD\",\n" +
                "    \"pointsToRedeem\": 2006,\n" +
                "    \"transactionDescription\": \"Completed\"\n" +
                "  }\n" +
                "}";

        request = RestAssured.given();
        response = request
                .when()
                .header("Content-Type","application/json")
                .header("Authorization", "Bearer " + access_token)
                .header("Accept", "application/json")
                .header("Accept-Language", "en-us")
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(payload)
                .post(uri);
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());



        uri = "/gcb/api/v1/apac/rewards/" + rewardLinkCode + "/pointBalance";
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
    }
}