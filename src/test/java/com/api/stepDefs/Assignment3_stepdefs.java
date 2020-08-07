package com.api.stepDefs;

import com.api.service.CITIBANK_API_ENGINE;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Assignment3_stepdefs {

	CITIBANK_API_ENGINE engine = new CITIBANK_API_ENGINE();
	
	
	@Given("Initiate the setup required")
	public void initiate_the_setup_required() {
	    // Write code here that turns the phrase above into concrete actions
		engine.baseURI_setup();
	}

	@When("Initiate the Client_Credentials_API {string} {string}-{string}")
	public void initiate_the_Client_Credentials_API(String CCmethod, String Grant, String scope) {
		engine.API_clientCredentials(CCmethod,Grant,scope);
	}

	@Then("Get the access token")
	public void get_the_access_token() {
		engine.Print_AccessToken();
	}

	@When("Initiate the Client_Credentials_API {string} and get the accessToken")
	public void API_credentials(String CCmethod) {
		engine.API_clientCredentials(CCmethod);
	}

	@When("Initiate the EtoEkey_API '(.*)' and get the Biz_token$")
	public void API_e2ekey(String EEmethod) {
		engine.API_e2ekey(EEmethod);
	}

	@When("Initiate the Resource_Owner '(.*)' and get the encrypted password$")
	public void API_ResourceOwner(String ROmethod) {
		engine.API_ResourceOwner(ROmethod);
	}
	
	@Then("Initiate the ResourceOwner {string} {string} {string} {string} and get the encrypted password")
	public void API_ResourceOwner(String ROmethod, String username,String password,String control) {
		engine.API_ResourceOwner(ROmethod,username,password,control);
	}

	@Then("Initiate the Accounts_API '(.*)' '(.*)' '(.*)' '(.*)' to get all the accounts$")
	public void API_Accounts(String ACCmethod,String Authorization_neg,String clientid_neg,String control) {
		engine.API_Accounts(ACCmethod,Authorization_neg,clientid_neg,control);
	}

	@Then("Initiate the API_Accounts_Transactions_API {string} {string} {string} to get all the accounts")
	public void API_Accounts_Transactions(String ACCmethod,String Trnx_status, String control) { 
		engine.API_Accounts_Transactions(ACCmethod,Trnx_status,control);	
		}


	@Then("Initiate the Domestic_Funds_Transfer_API {string} {string} {string} {string} {string} to get all the accounts")
	public void PDT_Confirmation(String ACCmethod, String control_Auth,String control_dest_account,String control_chargebearer,String control_flowid) { 
		engine.PDT_Confirmation_API(ACCmethod,control_Auth,control_dest_account,control_chargebearer,control_flowid);	
		}

	@Then("Initiate the Cards_API {string} {string} to get all the cards")
	public void cards_api(String cardMethod,String cardfunction) {
		engine.API_CARD_Details(cardMethod,cardfunction);
	}
}

	
	


