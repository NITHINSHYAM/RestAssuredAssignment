package RestAssuredScubaTraining;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredApiExample {
	
	 @BeforeTest
	    public void setUp() {
	        RestAssured.baseURI = "https://qa.sandbox.apihub.citi.com/gcb/gp/api/";
	          
	        }   
	        //lastFourDigitsCardNumber ="";
	   

	   @Test
		public void Get_all_employee_data()  {
			System.out.println("Start of the API >>>http://dummy.restapiexample.com/api/v1/employees");
		
			String form_Param_payload = "{\n" +
	                "  \"id\": \"1\",\n" +
	                "  \"employee_name\": \"8653\",\n" +
	                "  \"employee_age\": \"6590020056\",\n" +
	               // "  \"merchantCustomerReferenceId\": \"CB072000128065\"\n" +
	                "}";
			/*
			 id": "1",
"employee_name": "Tiger Nixon",
"employee_salary": "320800",
"employee_age": "61",
"profile_image": "" 
			 
			 */
			
			RequestSpecification request = RestAssured.given();
			Response response = request
			.when()
			.contentType("application/json")
	        //.header("")
	        //.header("")
	        //.header("")
	        //.header("uuid",UUID.randomUUID().toString())
	        //.accept()
	       .with()
	       .body(form_Param_payload)
	       .post();
		
			
			System.out.println(response.statusCode());
	       System.out.println(response.body().prettyPrint());

	        System.out.println("End of API >>>>  http://dummy.restapiexample.com/api/v1/employees");
			
		}
	   @Test
	   public void Get_a_single_employee_data() {
		   
		   System.out.println("Start of the API >>>http://dummy.restapiexample.com/api/v1/employee/1");
		   
		   String form_Param_payload = "{\n" +
	                "  \"id\": \"1\",\n" +
	                "  \"employee_name\": \"Tiger Nixon\",\n" +
	                "   \"employee_salary\": \"320800\",\n" +
	                "  \"employee_age\": \"61\",\n" +
	                "	\"profile_image\": \"\",\n" +
	               // "  \"merchantCustomerReferenceId\": \"CB072000128065\"\n" +
	                "}";
		   RequestSpecification request = RestAssured.given();
			Response response = request
			.when()
			.contentType("application/json")
	        //.header("")
	        //.header("")
	        //.header("")
	        //.header("uuid",UUID.randomUUID().toString())
	        //.accept()
	       .with()
	       .body(form_Param_payload)
	       .post();
		
			
			System.out.println(response.statusCode());
	       System.out.println(response.body().prettyPrint());
	       System.out.println("Start of the API >>>http://dummy.restapiexample.com/api/v1/employee/1");
	   }

}
