package service;

import config.EndPoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Login;

import static io.restassured.RestAssured.given;

public class Auth {
    private final RequestSpecification reqSpec;

    public Auth(RequestSpecification reqSpec) {
        this.reqSpec = reqSpec;
    }

    public Response authAs(Login login) {

        String bodyUserPassword = "{\n" +
                "    \"username\" : \"" + login.getUsername() + "\",\n" +
                "    \"password\" : \"" + login.getPassword() + "\"\n" +
                "}";

        return given()
                .body(bodyUserPassword).contentType(ContentType.JSON)
                .when()
                .post(EndPoint.AUTH)
                .then().extract().response();
    }

}
