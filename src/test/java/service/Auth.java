package service;

import config.EndPoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Login;

import static io.restassured.RestAssured.given;

public class Auth {
    private final RequestSpecification reqSpec;
    private Login login;

    public Auth(RequestSpecification reqSpec) {
        this.reqSpec = reqSpec;
    }

    public Response authAs(Login login) {
        this.login = login;
        return given()
                .body(login.getJson())
                .when()
                .post(EndPoint.AUTH)
                .prettyPeek();
    }

    public void setLogin(Login login) {
        this.login = login;
        given()
                .body(login.getJson())
                .when()
                .post(EndPoint.AUTH);
    }

    public String getToken() {
        return given()
                .body(login.getJson())
                .when()
                .post(EndPoint.AUTH)
                .then().extract().jsonPath().getString("token");
    }
}
