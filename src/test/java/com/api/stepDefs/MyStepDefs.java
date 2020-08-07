package com.api.stepDefs;

import com.api.service.Reqres;
import com.maveric.core.cucumber.CucumberBaseTest;
import io.cucumber.java8.En;
import io.cucumber.testng.CucumberOptions;

public class MyStepDefs implements En {

    Reqres reqres = new Reqres();

    public MyStepDefs() {

        Then("I create user", () -> {

            reqres.createUserTest();

        });
        And("I get User Details", () -> {

            reqres.getUserDetailsTest();
        });
        Then("I update User Details", () -> {
            reqres.updateUserDetailsTest();
        });
        Then("I Delete User", () -> {

            reqres.deleteUserTest();

        });
    }

    @CucumberOptions(
            features = {"./src/test/resources/features/Assignment3.feature"},
            glue ={ "com.stepdefs"}
    )

    public static class CucumberRunner_Assignment3 extends CucumberBaseTest {



        }
}
