import config.EndPoint;
import config.SmokeTest;
import config.TestConf;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HealthTest extends TestConf {

    @Category(SmokeTest.class)
    @Test
    public void checkPing() {

        Response response= given()
                .when()
                .get(EndPoint.PING)
                .then().extract().response();

        assertThat(201, equalTo(response.statusCode()));
    }
}
