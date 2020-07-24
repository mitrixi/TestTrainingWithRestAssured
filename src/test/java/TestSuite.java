import config.EndPoint;
import config.TestConf;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import service.Auth;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestSuite extends TestConf {

    @Test
    public void getBookingIds() {

        Response response = when()
                .get(EndPoint.BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("bookingid"), notNullValue());
    }


    @Test
    public void getBooking() {
        Response response = given()
                .pathParam("bookingId", 1)
                .when()
                .get(EndPoint.SINGLE_BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
    }


    @Test
    public void getWrongBooking() {
        Response response = given()
                .pathParam("bookingId", Integer.MAX_VALUE)
                .when()
                .get(EndPoint.SINGLE_BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(404));
    }

    @Test
    public void getBookingWithName() {

        Response response = given()
                .pathParam("fn", "Mary")
                .pathParam("ln", "Jackson")
                .when()
                .get(EndPoint.BOOKING + "?firstname={fn}&lastname={ln}" )
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("bookingid").size(), not(0));
    }

    @Test
    public void getWrongBookingWithName() {
        Response response = given()
                .pathParam("fn", "zvs13s1v5s4f")
                .pathParam("ln", "as4h8s9v5n27")
                .when()
                .get(EndPoint.BOOKING + "?firstname={fn}&lastname={ln}" )
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("bookingid").size(), is(0));
    }

    @Test
    public void getBookingWithDate() {

        Response response = given()
                .pathParam("ci", "2017-11-19")
                .pathParam("co", "2019-11-11")
                .when()
                .get(EndPoint.BOOKING + "?checkin={ci}&checkout={co}" )
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("bookingid").contains(1), equalTo(true));
    }

    @Test
    public void getWrongBookingWithDate() {

        Response response = given()
                .pathParam("fn", "9999-01-01")
                .pathParam("ln", "1111-01-01")
                .when()
                .get(EndPoint.BOOKING + "?checkin={fn}&checkout={ln}" )
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("bookingid").size(), is(0));
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
                .prettyPeek();

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

        Response response = given()
                .body(bodyNewBooking).pathParam("bookingId", 2)
                .cookie("token", Auth.getToken())
                .when()
                .put(EndPoint.SINGLE_BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().prettyPrint(), equalTo(bodyNewBooking));
    }


    @Test
    public void patchUpdateBooking() {

        String bodyNewBooking = "{\n" +
                "    \"firstname\": \"foo\",\n" +
                "    \"lastname\": \"bar\"\n" +
                "}";

        Response response = given()
                .body(bodyNewBooking).pathParam("bookingId", 2)
                .cookie("token", Auth.getToken())
                .when()
                .patch(EndPoint.SINGLE_BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("firstname"), equalTo(JsonPath.from(bodyNewBooking).getString("firstname")));
        assertThat(response.jsonPath().getString("lastname"), equalTo(JsonPath.from(bodyNewBooking).getString("lastname")));
    }


    @Test
    public void deleteBooking() {

        Response response = given()
                .cookie("token", Auth.getToken())
                .pathParam("bookingId", 12)
                .when()
                .delete(EndPoint.SINGLE_BOOKING)
                .prettyPeek();
        assertThat(response.statusCode(), equalTo(201));
    }


    @Test
    public void deleteNonexistentBooking() {

        Response response = given()
                .cookie("token", Auth.getToken())
                .pathParam("bookingId", Integer.MAX_VALUE)
                .when()
                .delete(EndPoint.SINGLE_BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(405));
    }
}

