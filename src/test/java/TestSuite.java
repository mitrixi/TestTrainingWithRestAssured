import config.EndPoint;
import config.TestConf;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestSuite extends TestConf {
    static String jsonToken;
    static String id;

    @Test
    public void getBookingIds() {

        Response response = when()
                .get(EndPoint.BOOKING)
                .then()
                .extract().response();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("bookingid"), notNullValue());
    }


    @Test
    public void getBooking() {
        Response response = given()
                .pathParam("bookingId", 1)
                .when()
                .get(EndPoint.SINGLE_BOOKING)
                .then()
                .extract().response();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void getWrongBooking() {
        Response response = given()
                .pathParam("bookingId", 99)
                .when()
                .get(EndPoint.SINGLE_BOOKING)
                .then()
                .extract().response();

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(response.jsonPath().prettify());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        assertThat(response.statusCode(), equalTo(404));
    }

    @Test
    public void getBookingIdWithName() {

        Response response = given()
                .pathParam("fn", "1")
                .pathParam("ln", "Brown")
                .when()
                .get(EndPoint.BOOKING + "?firstname={fn}&lastname={ln}" )
                .then()
                .extract().response();

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(response.jsonPath().prettify());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("bookingid"), notNullValue());

    }

    @Test
    public void getWrongBookingIdWithName() {

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

        Response response = given()
                .body(bodyNewBooking)
                .when()
                .post(EndPoint.BOOKING)
                .then()
                .extract().response();

        assertThat(response.statusCode(), equalTo(200));
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

