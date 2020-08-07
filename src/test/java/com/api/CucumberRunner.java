package com.api;

import com.maveric.core.cucumber.CucumberBaseTest;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"./src/test/resources/features"},
        tags = "@Password_API",
        glue = "com.api.stepDefs"
)
public class CucumberRunner extends CucumberBaseTest {

}


/*List of API Tags
@Client_Credentials_API
@Password_API
@Account_API
@Cards_API
@Account_Transactions_API
@PDT_Process_API*/