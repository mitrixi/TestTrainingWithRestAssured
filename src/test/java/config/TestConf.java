package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestConf {
    public static RequestSpecification urlParam_requestSpec;
    public static RequestSpecification restfulbooker_requestSpec;
    public static RequestSpecification restfulbookerLocal_requestSpec;
    public static ResponseSpecification responseSpec;

    @Rule
    public TestRule rule = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            System.out.println("Test " + description.getMethodName() + " succeeded");
        }

        @Override
        protected void failed(Throwable e, Description description) {
            System.out.println("Test " + description.getMethodName() + " failed");
        }

        @Override
        protected void starting(Description description) {
            System.out.println("Test " + description.getMethodName() + " has started");
        }

        @Override
        protected void finished(Description description) {
            System.out.println("Test " + description.getMethodName() + " has finished\n");
        }
    };

    @BeforeClass
    public static void setup() {

        urlParam_requestSpec = new RequestSpecBuilder()
                .setBaseUri(System.getProperty("url"))
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        restfulbooker_requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        restfulbookerLocal_requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:3001")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        if (System.getProperty("url") != null)
            RestAssured.requestSpecification = urlParam_requestSpec;
        else
            RestAssured.requestSpecification = restfulbooker_requestSpec;


        responseSpec = new ResponseSpecBuilder()
                .build();

        RestAssured.responseSpecification = responseSpec;
    }
}
