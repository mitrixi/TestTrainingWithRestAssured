import config.EndPoint;
import config.TestConf;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class TestSuite extends TestConf {

    String jsonToken;

    @Before
    @Test
    public void checkPing() {
        given()
                .when()
                .get(EndPoint.PING)
                .then()
                .statusCode(201)
                .log().all();
    }

    @Before
    @Test
    public void createToken() {

        String bodyUserPassword = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        jsonToken = given()
                .body(bodyUserPassword).contentType(ContentType.JSON)
        .when()
                .post(EndPoint.AUTH)
        .then()
                .extract().body().asString();
    }

    @Test
    public void getBookingIds() {
        //обязательно ли писать given?
        when()
                .get(EndPoint.BOOKING)
        .then().log().all();
    }

    @Test
    public void getBooking() {
        given()
                .pathParam("bookingId", 1)
        .when()
                .get(EndPoint.SINGLE_BOOKING)
        .then()
                .log().all();
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
        given()
                .body(bodyNewBooking)
        .when()
                .post(EndPoint.BOOKING)
        .then()
                .log().all();
    }

    @Test
    public void putUpdateBooking() {

        String bodyNewBooking = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        given()
                .body(bodyNewBooking).pathParam("bookingId", 1)
//                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .cookie("token=" + JsonPath.from(jsonToken).getString("token"))
        .when()
                .put(EndPoint.SINGLE_BOOKING)
        .then()
                .log().all();
    }

    @Test
    public void patchUpdateBooking() {

        String bodyNewBooking = "{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\"\n" +
                "}";

        given()
                .body(bodyNewBooking).pathParam("bookingId", 1)
//                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .cookie("token=" + JsonPath.from(jsonToken).getString("token"))
        .when()
                .patch(EndPoint.SINGLE_BOOKING)
        .then()
                .log().all();
    }

    @Test
    public void deleteBooking() {

        given()
                .cookie("token=" + JsonPath.from(jsonToken).getString("token"))
                .pathParam("bookingId", 1)
        .when()
                .delete(EndPoint.SINGLE_BOOKING)
        .then()
                .log().all();
    }


}
