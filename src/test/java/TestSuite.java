import config.EndPoint;
import config.TestConf;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestSuite extends TestConf {
    static String jsonToken;
    static String id;

    @BeforeClass
    public static void checkPingAndAuthorization() {
        int statusCode = given()
                .when()
                .get(EndPoint.PING)
                .then()
                .extract().statusCode();

        assertThat(201, equalTo(statusCode));

        //authorization
        String bodyUserPassword = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        jsonToken = given()
                .body(bodyUserPassword).contentType(ContentType.JSON)
                .when()
                .post(EndPoint.AUTH)
                .then()
                .extract().jsonPath().getString("token");
    }

    @Test
    public void getBookingIds() {
        String expected = "[\n" +
                "    {\n" +
                "      \"bookingid\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"bookingid\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"bookingid\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"bookingid\": 4\n" +
                "    }\n" +
                "]";

        String result = when()
                .get(EndPoint.BOOKING)
        .then()
                .extract().body().jsonPath().prettyPrint();

        assertThat(result, equalTo(expected));
    }


    @Test
    public void getBooking() {

        String expected = "{\n" +
                "    \"firstname\": \"Sally\",\n" +
                "    \"lastname\": \"Brown\",\n" +
                "    \"totalprice\": 111,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"bookingdates\": {\n" +
                "        \"checkin\": \"2013-02-23\",\n" +
                "        \"checkout\": \"2014-10-23\"\n" +
                "    },\n" +
                "    \"additionalneeds\": \"Breakfast\"\n" +
                "}";
        String result = given()
                .pathParam("bookingId", 1)
        .when()
                .get(EndPoint.SINGLE_BOOKING)
        .then()
                .extract().jsonPath().prettyPrint();

        assertThat(result, equalTo(expected));
    }

    @Test
    public void createBooking() {

        String bodyNewBooking = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        JsonPath jsonPath = given()
                .body(bodyNewBooking)
        .when()
                .post(EndPoint.BOOKING)
        .then()
                .extract().jsonPath();


        id = jsonPath.getString("bookingid");

        String expected = "{\n" +
                "    \"bookingid\": " + id + ",\n" +
                "    \"booking\": {\n" +
                "        \"firstname\": \"Jim\",\n" +
                "        \"lastname\": \"Brown\",\n" +
                "        \"totalprice\": 111,\n" +
                "        \"depositpaid\": true,\n" +
                "        \"bookingdates\": {\n" +
                "            \"checkin\": \"2018-01-01\",\n" +
                "            \"checkout\": \"2019-01-01\"\n" +
                "        },\n" +
                "        \"additionalneeds\": \"Breakfast\"\n" +
                "    }\n" +
                "}";

        assertThat(jsonPath.prettify(), equalTo(expected));
    }

    @Test
    public void putUpdateBooking() {

        String bodyNewBooking = "{\n" +
                "    \"firstname\": \"James\",\n" +
                "    \"lastname\": \"Brown\",\n" +
                "    \"totalprice\": 111,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"bookingdates\": {\n" +
                "        \"checkin\": \"2018-01-01\",\n" +
                "        \"checkout\": \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\": \"Breakfast\"\n" +
                "}";

        String result = given()
                .body(bodyNewBooking).pathParam("bookingId", 1)
                .cookie("token", jsonToken)
        .when()
                .put(EndPoint.SINGLE_BOOKING)
        .then()
                .extract().jsonPath().prettify();

        assertThat(result, equalTo(bodyNewBooking));
    }

    @Test
    public void patchUpdateBooking() {

        String expected = "{\n" +
                "    \"firstname\": \"James\",\n" +
                "    \"lastname\": \"Brown\",\n" +
                "    \"totalprice\": 111,\n" +
                "    \"depositpaid\": true,\n" +
                "    \"bookingdates\": {\n" +
                "        \"checkin\": \"2018-01-01\",\n" +
                "        \"checkout\": \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\": \"Breakfast\"\n" +
                "}";

        String bodyNewBooking = "{\n" +
                "    \"firstname\": \"James\",\n" +
                "    \"lastname\": \"Brown\"\n" +
                "}";

        String result = given()
                .body(bodyNewBooking).pathParam("bookingId", 1)
                .cookie("token", jsonToken)
        .when()
                .patch(EndPoint.SINGLE_BOOKING)
        .then()
                .extract().jsonPath().prettify();

        assertThat(result, equalTo(expected));
    }

    @Test
    public void deleteBooking() {

        String response = given()
                .cookie("token", jsonToken)
                .pathParam("bookingId", id)
        .when()
                .delete(EndPoint.SINGLE_BOOKING)
        .then()
                .extract().response().asString();

        assertThat(response, equalTo("Created"));
    }
}

