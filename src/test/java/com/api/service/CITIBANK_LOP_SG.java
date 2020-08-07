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


public class CITIBANK_LOP_SG extends BaseLOP{

	
	//String authorization,client_id;
	
    public CITIBANK_LOP_SG() {
    	
    }

    @Test(priority=1)
    public void Initiate_the_cardAuth_authorize_API_with_valid_fields_and_verify_the_Expected_Result() {
    	
    	Card_Authentication();
    }
  
    @Test(priority=2)
    public void Initiate_the_cardAuth_access_API_with_valid_fields_and_verify_the_Expected_Result() {
    	//Card_Authentication();
    	Card_AccessToken();
    }
    
    @Test(priority=3)
    public void Initiate_the_loan_eligibility_API_with_valid_fields_and_verify_the_Expected_Result() {
    	
    	Card_Authentication();
    	Card_AccessToken();
    	Loan_Eligibility();
    	
    }
    
    @Test(priority=4)
    public void Initiate_the_Loan_RePYMENT_API_with_valid_fields_and_verify_the_Expected_Result() {
    	
    	Card_Authentication();
    	Card_AccessToken();
    	Loan_Eligibility();
    	Disbursment();
    	LOP_PaymentPlans();
    	Loan_RePayment();
    }
    
    @Test(priority=5)
    public void Initiate_the_LoanBooking_API_with_valid_fields_and_verify_the_Expected_Result() {
    	
    	Card_Authentication();
    	Card_AccessToken();
    	Loan_Eligibility();
    	Disbursment();
    	LOP_PaymentPlans();
    	Loan_RePayment();
    	Loan_Booking();
    }
    
    @Test(priority=6)
    public void Initiate_the_E2EKEY_API_with_valid_fields_and_verify_the_Expected_Result() {
    	
    	Card_Authentication();
    	Card_AccessToken();
    	Loan_Eligibility();
    	Disbursment();
    	LOP_PaymentPlans();
    	Loan_RePayment();
    	Loan_Booking();
    	e2eKey();
    }
    
    @Test(priority=7)
   public void Initiate_the_ValidateOTP_API_with_valid_fields_and_verify_the_Expected_Result() {
    	
    	Card_Authentication();
    	Card_AccessToken();
    	Loan_Eligibility();
    	Disbursment();
    	LOP_PaymentPlans();
    	Loan_RePayment();
    	Loan_Booking();
    	e2eKey();
    	ValidateOTP();
    }
   
    @Test(priority=8)
   public void Initiate_the_LoanBooking_Confirmation_API_with_valid_fields_and_verify_the_Expected_Result() {
   	
   	Card_Authentication();
   	Card_AccessToken();
   	Loan_Eligibility();
   	Disbursment();
   	LOP_PaymentPlans();
   	Loan_RePayment();
   	Loan_Booking();
   	e2eKey();
   	ValidateOTP();
   	Loan_Booking_Confirmation();
   	LOP_Summary_Repayment();
   	
   }
   
    //Negative Scenario
   /*@Test(priority=9)
   public void To_verify_the_request_with_invalid_client_id_and_Authorization() {
	   	
	   super.authorization = "Basic ZjAwNzVmYWItOGQ3Zi00YzcwLTk5NjQtNDU4MzA4ZWZiZmM1OmRXN2dHNmVUNmlFNW1XNWRLNGdPOHhVMWRFOGhMNHRWOHBOMHhGNnRFNGdCOG5TMHlP";
	   super.client_id="b0075fab-8d7f-4c70-9964-458308efbfc6\\n";
	   	Card_Authentication();
	   	Card_AccessToken();
	   	Loan_Eligibility();
	   	Disbursment();
	   	LOP_PaymentPlans();
	   	Loan_RePayment();
	   	Loan_Booking();
	   	e2eKey();
	   	ValidateOTP();
	   	Loan_Booking_Confirmation();
	   	LOP_Summary_Repayment();
	 }
    
    @Test(priority=10)
    public void To_verify_the_request_with_invalid_accountId() {
 	   	
 	   super.authorization = "Basic ZjAwNzVmYWItOGQ3Zi00YzcwLTk5NjQtNDU4MzA4ZWZiZmM1OmRXN2dHNmVUNmlFNW1XNWRLNGdPOHhVMWRFOGhMNHRWOHBOMHhGNnRFNGdCOG5TMHlP";
 	   super.client_id="b0075fab-8d7f-4c70-9964-458308efbfc6\\n";
 	   	Card_Authentication();
 	   	Card_AccessToken();
 	   	Loan_Eligibility();
 	   	
 	   	super.accountId="";
 	   	
 	   	Disbursment();
 	   	LOP_PaymentPlans();
 	   	Loan_RePayment();
 	   Loan_Booking();
 	   	e2eKey();
 	   	ValidateOTP();
 	   	Loan_Booking_Confirmation();
 	   	LOP_Summary_Repayment();
 	 }*/
    
}
