import config.SmokeTest;
import config.TestConf;
import io.restassured.response.Response;
import model.Login;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import service.Auth;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AuthTest extends TestConf {

    Auth auth = new Auth();

    @Category(SmokeTest.class)
    @Test
    public void successAuth() {
        Login admin = new Login()
                .setUsername("admin")
                .setPassword("password123");
        Response response = auth.authAs(admin);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("token"), allOf(notNullValue(), not(equalTo(""))));
    }

    @Test
    public void authWithoutEnteredFields() {
        Login admin = new Login()
                .setUsername("")
                .setPassword("");
        Response response = auth.authAs(admin);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("token"), nullValue());
    }

    @Test
    public void authWithoutPassword() {
        Login admin = new Login()
                .setUsername("admin")
                .setPassword("");
        Response response = auth.authAs(admin);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("token"), nullValue());
        assertThat(response.jsonPath().getString("reason"), equalTo("Bad credentials"));
    }

    @Test
    public void authWithoutLogin() {
        Login admin = new Login()
                .setUsername("")
                .setPassword("password123");
        Response response = auth.authAs(admin);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("token"), nullValue());
        assertThat(response.jsonPath().getString("reason"), equalTo("Bad credentials"));
    }
}
