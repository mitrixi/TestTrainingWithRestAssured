import config.EndPoint;
import config.SmokeTest;
import config.TestConf;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Booking;
import model.Bookingdates;
import model.Login;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import service.Auth;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestSuite extends TestConf {

    private static Auth auth;

    @BeforeClass
    public static void authorization() {
        auth = new Auth();
        auth.setLogin(new Login()
                .setUsername("admin")
                .setPassword("password123"));
    }

    @Category(SmokeTest.class)
    @Test
    public void getBookingIds() {

        Response response = when()
                .get(EndPoint.BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("bookingid"), notNullValue());
    }

    @Category(SmokeTest.class)
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
                .queryParam("firstname", "Mary")
                .queryParam("lastname", "Jackson")
                .when()
                .get(EndPoint.BOOKING)
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
                .get(EndPoint.BOOKING + "?firstname={fn}&lastname={ln}")
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("bookingid").size(), is(0));
    }

    @Test
    public void getBookingWithDate() {

        Response response = given()
                .queryParam("checkin", "2017-11-19")
                .queryParam("checkout", "2019-11-11")
                .when()
                .get(EndPoint.BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("bookingid").contains(1), equalTo(true));
    }

    @Test
    public void getWrongBookingWithDate() {

        Response response = given()
                .queryParam("checkin", "9999-01-01")
                .queryParam("checkout", "1111-01-01")
                .when()
                .get(EndPoint.BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getList("bookingid").size(), is(0));
    }

    @Category(SmokeTest.class)
    @Test
    public void createBooking() {

        Booking booking = new Booking()
                .setFirstname("Jim")
                .setLastname("Brown")
                .setTotalprice(111)
                .setDepositpaid(true)
                .setBookingdates(new Bookingdates("2018-01-01", "2019-01-01"))
                .setAdditionalneeds("Breakfast");

        Response response = given()
                .body(booking)
                .when()
                .post(EndPoint.BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Category(SmokeTest.class)
    @Test
    public void putUpdateBooking() {

        Booking booking = new Booking()
                .setFirstname("James")
                .setLastname("Brown")
                .setTotalprice(111)
                .setDepositpaid(true)
                .setBookingdates(new Bookingdates("2018-01-01", "2019-01-01"))
                .setAdditionalneeds("Breakfast");

        Response response = given()
                .body(booking).pathParam("bookingId", 2)
                .cookie("token", auth.getToken())
                .when()
                .put(EndPoint.SINGLE_BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
    }

    @Category(SmokeTest.class)
    @Test
    public void patchUpdateBooking() {

        String jsonBooking = "{\n" +
                "    \"firstname\": \"Foo\",\n" +
                "    \"lastname\": \"Bar\"\n" +
                "}";

        Response response = given()
                .body(jsonBooking).pathParam("bookingId", 2)
                .cookie("token", auth.getToken())
                .when()
                .patch(EndPoint.SINGLE_BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("firstname"), equalTo(JsonPath.from(jsonBooking).getString("firstname")));
        assertThat(response.jsonPath().getString("lastname"), equalTo(JsonPath.from(jsonBooking).getString("lastname")));
    }

    @Category(SmokeTest.class)
    @Test
    public void deleteBooking() {

        Response response = given()
                .cookie("token", auth.getToken())
                .pathParam("bookingId", 8)
                .when()
                .delete(EndPoint.SINGLE_BOOKING)
                .prettyPeek();
        assertThat(response.statusCode(), equalTo(201));
    }

    @Test
    public void deleteNonexistentBooking() {

        Response response = given()
                .cookie("token", auth.getToken())
                .pathParam("bookingId", Integer.MAX_VALUE)
                .when()
                .delete(EndPoint.SINGLE_BOOKING)
                .prettyPeek();

        assertThat(response.statusCode(), equalTo(405));
    }
}

