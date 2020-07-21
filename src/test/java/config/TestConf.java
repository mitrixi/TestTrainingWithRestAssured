package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.ValidatableResponseImpl;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;

public class TestConf {
    public static RequestSpecification mwtestconsultancy_requestSpec;
    public static ResponseSpecification responseSpec;

    @BeforeClass
    public static void setup() {
        mwtestconsultancy_requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .log(LogDetail.ALL)
                .build();

        RestAssured.requestSpecification = mwtestconsultancy_requestSpec;

        responseSpec = new ResponseSpecBuilder()
//                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();

        RestAssured.responseSpecification = responseSpec;
    }
}
