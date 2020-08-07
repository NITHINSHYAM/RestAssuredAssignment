package com.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.DocumentContext;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matchers;

public class CITIBANK_PAYMENTS {


    public CITIBANK_PAYMENTS() {
    }

    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = "https://sandbox.apihub.citi.com";
    }


    @Test(priority = 1)
    public void givenValidDetails_Create_PetData_Check_StatusCode200() {

        String countryCode = "au";    //au,id,my,hk,th,vn,sg,cn
        String currenyCode = "AUD";   //AUD,MYR,HKD,SGD
        String authorization = "Basic N2E3YzE0MTYtNWViMi00ZDkzLTg4ZmEtZGVkNDI5ZDJmZWIxOlkxaEUxYVUyYVA0a082cFk2a0wwZU4wbUExdVU3aFg1aVYzcE8yeVIwdlMycUMwdkUy";
        String client_id = "7a7c1416-5eb2-4d93-88fa-ded429d2feb1"; // 3906dd6d-534b-4d20-81d7-0e78848013a3
        String uri = "/gcb/api/clientCredentials/oauth2/token/" + countryCode + "/gcb";
        String formUrlEncodedPayload = "grant_type=client_credentials&scope=/api";

        File file = new File("files/accesstoken_responsejsonschema.json");
        String jsonSchemaFromFile = readFileContents("files/accesstoken_responsejsonschema.json");
        System.out.println(jsonSchemaFromFile);

        RequestSpecBuilder builder = new RequestSpecBuilder(); // Demonstration for RequestSpecBuilder
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
        builder.addHeader("Authorization", authorization);
        builder.addHeader("Accept", ContentType.APPLICATION_JSON.getMimeType());
        RequestSpecification request = builder.build();


        given()
                .spec(request)
                .when()
                .with()
                .body(formUrlEncodedPayload)
                .post(uri)
                .then()
                .assertThat()
                .body(matchesJsonSchema(file));

        String access_token1 = given()
                .spec(request)
                .when()
                .with()
                .body(formUrlEncodedPayload)
                .post(uri)
                .then().extract().path("access_token");

        System.out.println("The access token is >>>>>>" + access_token1);


        given()
                .spec(request)
                .when()
                .with()
                .body(formUrlEncodedPayload)
                .post(uri)
                .then()
                .body("token_type", equalTo("Bearer"));

/*
        List<Map<String, String>> as = given()
                .spec(request)
                .when()
                .with()
                .body(formUrlEncodedPayload)
                .post(uri)
                .as(new TypeRef<List<Map<String, String>>>() {
                });

         System.out.println("The size of the response : >>>>>>>>>>>>>>>." + as.size());
*/

        Response response =
                given()
                        .spec(request)
                        .when()
                        .with()
                        .body(formUrlEncodedPayload)
                        .post(uri);


        ResponseBody body = response.body();

        DocumentContext jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String access_token = jsonContext.read("$.access_token");
        System.out.println("The access token is <<<<<<<<<<<<<<<  : " + access_token);


        uri = "/gcb/api/v1/apac/rewards/linkage";
        /*String payload = "{" +
                "\t\t\"lastFourDigitsCardNumber\": \"7411\",\n" +
                "\t\t\"citiCardHolderPhoneNumber\": \"2608191234\",\n" +
                "\t\t\"merchantCustomerReferenceId\": \"2608191234111\"\n" +
                "}";
        System.out.println("The linkage payload is : >>>>>>>>>>>>>>>>>>>>>>>" + payload);
        */

        JSONObject customerDetailsPayload = new JSONObject();
        customerDetailsPayload.put("lastFourDigitsCardNumber","7411");
        customerDetailsPayload.put("citiCardHolderPhoneNumber","2608191234");
        customerDetailsPayload.put("merchantCustomerReferenceId","2608191234111");


        response =
                given()
                        .spec(request)
                        .header("Authorization", "Bearer " + access_token)
                        .contentType(ContentType.APPLICATION_JSON.getMimeType())
                        .header("client_id", client_id)
                        .body(customerDetailsPayload.toString())//add pet object
                        .post(uri);


        body = response.body();
        jsonContext = com.jayway.jsonpath.JsonPath.parse(body.print());
        String rewardLinkCode = jsonContext.read("$.rewardLinkCode");
        System.out.println("<<<<<<<<<<<<<<<" + rewardLinkCode);

        uri = "/gcb/api/v1/apac/rewards/" + rewardLinkCode + "/activations";

        /*
        payload = "{" +
                "      \"linkageConfirmationCode\":\"735937\"" +
                "    }";
        */
        JSONObject otpPayload = new JSONObject();
        otpPayload.put("linkageConfirmationCode","735937");


        request = RestAssured.given();
        response = request
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + access_token)
                .header("Accept", "application/json")
                .header("Accept-Language", "en-us")
                .header("uuid", UUID.randomUUID().toString())
                .header("client_id", client_id)
                .with()
                .body(otpPayload.toString()).log().all()
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

        String payload = "{\n" +
                "  \"transactionReferenceNumber\": \"1668454de62345\",\n" +
                "  \"redemptionOrder\": {\n" +
                "    \"transactionAmount\": 5.42,\n" +
                "    \"currencyCode\": \"" + currenyCode + "\",\n" +
                "    \"pointsToRedeem\": 2006,\n" +
                "    \"transactionDescription\": \"Completed\"\n" +
                "  }\n" +
                "}";

        request = RestAssured.given();
        response = request
                .when()
                .header("Content-Type", "application/json")
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
                "    \"transactionReferenceNumber\":\"1668454de62346\",\n" +
                "\t\t\"redemptionOrder\":{\n" +
                "\t\t\"transactionAmount\":\"5.42\",\n" +
                "\t\t\"currencyCode\":\"AUD\",\n" +
                "\t\t\"pointsToRedeem\":\"2006\",\n" +
                "\t\t\"transactionDescription\":\"Completed\"\n" +
                "\t\t}\n" +
                "}";

        System.out.println("Transaction Reference : ||||||||||||||||||||||||||||" + payload);

/*
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Transaction transaction = new Transaction();
        RedemptionOrder redemptionOrder = new RedemptionOrder();

        redemptionOrder.setCurrencyCode("AUD");
        redemptionOrder.setTransactionAmount("5.42");
        redemptionOrder.setPointsToRedeem("2006");
        redemptionOrder.setTransactionDescription("Completed");

        transaction.setRedemptionOrder(redemptionOrder);
        transaction.setTransactionReferenceNumber("1668454de62346");

        String transactionString = gson.toJson(transaction, Transaction.class);
        System.out.println("The transaction strings are : >>>>>>>>>>>>>>>>>>>>>>>" + transactionString);
*/

        request = RestAssured.given();
        response = request
                .when()
                .header("Content-Type", "application/json")
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

    public String readFileContents(String path) {
        // Read the content from file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = bufferedReader.readLine();
            String totalFileContent = line;
            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
                totalFileContent = totalFileContent + line;
            }
            return totalFileContent;
        } catch (FileNotFoundException e) {
            // Exception handling
        } catch (IOException e) {
            // Exception handling
        }

        return null;
    }
}