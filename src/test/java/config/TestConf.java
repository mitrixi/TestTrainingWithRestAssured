package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;

public class TestConf {
    public static RequestSpecification restfulbooker_requestSpec;
    public static RequestSpecification restfulbookerLocal_requestSpec;
    public static ResponseSpecification responseSpec;

    @BeforeClass
    public static void setup() {

        restfulbooker_requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .log(LogDetail.ALL)
                .build();

        restfulbookerLocal_requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost:3001")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .log(LogDetail.ALL)
                .build();

        RestAssured.requestSpecification = restfulbookerLocal_requestSpec;

        responseSpec = new ResponseSpecBuilder()
//                .log(LogDetail.ALL)
                .build();

        RestAssured.responseSpecification = responseSpec;
    }
}
