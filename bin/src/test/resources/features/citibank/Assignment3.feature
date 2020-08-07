@CITIBANKapi

Feature: CITIBANK SANDBOX API Test Cases


Background: 
Given Initiate the setup required

Scenario Outline: Initiate_the_Account_API_with_valid_fields_and_verify_the_Expected_Result

When Initiate the Client_Credentials_API '<CC_Method>' and get the accessToken
And Initiate the EtoEkey_API '<EE_method>' and get the Biz_token
And Initiate the Resource_Owner '<RO_method>' and get the encrypted password
Then Initiate the Accounts_API '<ACC_method>' to get all the accounts

Examples:
|CC_Method|EE_method|RO_method|ACC_method|
|/gcb/api/clientCredentials/oauth2/token/us/gcb|/gcb/api/security/e2eKey|/gcb/api/password/oauth2/token/bh/gcb|/gcb/api/v1/accounts|


  Scenario Outline: Initiate_the_Account_Transactions_API_with_valid_fields_and_verify_the_Expected_Result

    When Initiate the Client_Credentials_API '<CC_Method>' and get the accessToken
    And Initiate the EtoEkey_API '<EE_method>' and get the Biz_token
    And Initiate the Resource_Owner '<RO_method>' and get the encrypted password
    Then Initiate the API_Accounts_Transactions_API '<ACC_method>' to get all the accounts

    Examples:
      |CC_Method|EE_method|RO_method|ACC_method|
      |/gcb/api/clientCredentials/oauth2/token/us/gcb|/gcb/api/security/e2eKey|/gcb/api/password/oauth2/token/bh/gcb|/gcb/api/v1/accounts|


  Scenario Outline: Initiate_the_Domestic_Funds_Transfer_API_with_valid_fields_and_verify_the_Expected_Result

    When Initiate the Client_Credentials_API '<CC_Method>' and get the accessToken
    And Initiate the EtoEkey_API '<EE_method>' and get the Biz_token
    And Initiate the Resource_Owner '<RO_method>' and get the encrypted password
    Then Initiate the Domestic_Funds_Transfer_API '<ACC_method>' to get all the accounts

    Examples:
      |CC_Method|EE_method|RO_method|ACC_method|
      |/gcb/api/clientCredentials/oauth2/token/us/gcb|/gcb/api/security/e2eKey|/gcb/api/password/oauth2/token/bh/gcb|/gcb/api/v1/accounts|


  Scenario Outline: Initiate_the_Cards_API_with_valid_fields_and_verify_the_Expected_Result

    When Initiate the Client_Credentials_API '<CC_Method>' and get the accessToken
    And Initiate the EtoEkey_API '<EE_method>' and get the Biz_token
    And Initiate the Resource_Owner '<RO_method>' and get the encrypted password
    Then Initiate the Cards_API '<card_method>' to get all the cards

    Examples:
      |CC_Method|EE_method|RO_method|card_method|
      |/gcb/api/clientCredentials/oauth2/token/us/gcb|/gcb/api/security/e2eKey|/gcb/api/password/oauth2/token/bh/gcb|/gcb/api/v1/cards|





