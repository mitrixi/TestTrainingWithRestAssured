package service;

import config.EndPoint;
import io.restassured.response.Response;
import model.Login;

import static io.restassured.RestAssured.given;

public class Auth {
    private Login login;

    public Response authAs(Login login) {
        this.login = login;
        return given()
                .body(login)
                .when()
                .post(EndPoint.AUTH)
                .prettyPeek();
    }

    public void setLogin(Login login) {
        this.login = login;
        given()
                .body(login)
                .when()
                .post(EndPoint.AUTH);
    }

    public String getToken() {
        return given()
                .body(login)
                .when()
                .post(EndPoint.AUTH)
                .then().extract().jsonPath().getString("token");
    }
}
