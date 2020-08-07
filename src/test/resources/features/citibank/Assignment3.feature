Feature: CITIBANK SANDBOX API Test Cases


Background: 
Given Initiate the setup required

@Client_Credentials_API
Scenario Outline: Initiate_the_Client_Credentials_API_with_valid_fields_and_verify_the_Expected_Result
When Initiate the Client_Credentials_API '<CC_Method>' '<grant_type>'-'<scope>'
Then Get the access token
Examples:
#positive cases 
|CC_Method|grant_type|scope|
|CC_method|client_credentials|/api|
#negative cases 
|CC_method|loan_authorization|/api|   
|CC_method|client_credentials|/apis|


@Password_API
Scenario Outline: Initiate the Password API with valid fields and verify the Expected Result

When Initiate the Client_Credentials_API '<CC_Method>' and get the accessToken
And Initiate the EtoEkey_API '<EE_method>' and get the Biz_token
Then Initiate the ResourceOwner '<RO_method>' '<Username>' '<Password>' '<Control>' and get the encrypted password
Examples:
|CC_Method|EE_method|RO_method|Username|Password|Control|
|CC_method|EE_method|RO_method|SandboxUser1|password|No|
#Negative cases
|CC_method|EE_method|RO_method|SandboxUser123|password|No|
|CC_method|EE_method|RO_method|SandboxUser1|password|Yes|


@Account_API
Scenario Outline: Initiate_the_Account_API_with_valid_fields_and_verify_the_Expected_Result

When Initiate the Client_Credentials_API '<CC_Method>' and get the accessToken
And Initiate the EtoEkey_API '<EE_method>' and get the Biz_token
And Initiate the Resource_Owner '<RO_method>' and get the encrypted password
Then Initiate the Accounts_API '<ACC_method>' '<Auth>' '<Clientid>' '<Control>' to get all the accounts

Examples:
|CC_Method|EE_method|RO_method|ACC_method|Auth|Clientid|Control|
|CC_method|EE_method|RO_method|ACC_method|null|null|null|
#Negative cases
|CC_method|EE_method|RO_method|ACC_method|145678|null|Yes|
|CC_method|EE_method|RO_method|ACC_method|null|123456|No|

@Cards_API
 Scenario Outline: Initiate_the_Cards_API_with_valid_fields_and_verify_the_Expected_Result

 When Initiate the Client_Credentials_API '<CC_Method>' and get the accessToken
 And Initiate the EtoEkey_API '<EE_method>' and get the Biz_token
 And Initiate the Resource_Owner '<RO_method>' and get the encrypted password
 Then Initiate the Cards_API '<card_method>' '<cardfunction>' to get all the cards

 Examples:
 |CC_Method|EE_method|RO_method|card_method|cardfunction|
 |CC_method|EE_method|RO_method|Card_method|ALL|
#Negative Case
 |CC_method|EE_method|RO_method|Card_method|cards|
 
 

@Account_Transactions_API
 Scenario Outline: Initiate_the_Account_Transactions_API_with_valid_fields_and_verify_the_Expected_Result

 When Initiate the Client_Credentials_API '<CC_Method>' and get the accessToken
 And Initiate the EtoEkey_API '<EE_method>' and get the Biz_token
 And Initiate the Resource_Owner '<RO_method>' and get the encrypted password
 Then Initiate the API_Accounts_Transactions_API '<ACC_method>' '<Tranactionstatus>' '<control>' to get all the accounts
   
 Examples:
|CC_Method|EE_method|RO_method|ACC_method|Tranactionstatus|control|
|CC_method|EE_method|RO_method|ACC_method|POSTED|NULL|
#Negative
|CC_method|EE_method|RO_method|ACC_method|POSTED|Yes|
|CC_method|EE_method|RO_method|ACC_method|Acc_posted|NULL|



@PDT_Process_API
Scenario Outline: Initiate_the_Domestic_Funds_Transfer_API_with_valid_fields_and_verify_the_Expected_Result

When Initiate the Client_Credentials_API '<CC_Method>' and get the accessToken
And Initiate the EtoEkey_API '<EE_method>' and get the Biz_token
And Initiate the Resource_Owner '<RO_method>' and get the encrypted password
Then Initiate the Domestic_Funds_Transfer_API '<ACC_method>' '<control_Auth>' '<control_dest_account>' '<control_chargebearer>' '<control_flowid>' to get all the accounts

Examples:
|CC_Method|EE_method|RO_method|ACC_method|control_Auth|control_dest_account|control_chargebearer|control_flowid|
|CC_method|EE_method|RO_method|ACC_method|NUll|NUll|NUll|NUll|
#Negative cases
|CC_method|EE_method|RO_method|ACC_method|NUll|NUll|NUll|Yes|
|CC_method|EE_method|RO_method|ACC_method|Yes|NUll|NUll|NUll|
|CC_method|EE_method|RO_method|ACC_method|NUll|Yes|NUll|NUll|
|CC_method|EE_method|RO_method|ACC_method|NUll|NUll|Yes|NUll|



