import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.ClientModel;
import org.junit.Test;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static steps.ClientSteps.loginClient;

public class LoginClientTest extends BaseApiTest {

    private static final Faker faker = new Faker();

    @Test
    @DisplayName("вход под существующим пользователем")

    public void loginWithExistingClientTest(){

        createClientBefore();
        loginClientBefore();
        assertTrue(accessToken.startsWith("Bearer "));

    }
    @Test
    @DisplayName("вход с неверным логином")

    public void loginWithWrongEmailTest(){

        createClientBefore();
        ClientModel wrongEmailClient = new ClientModel(
                faker.regexify("[A-Za-z]{5,10}")+ "ya.ru",
                client.getPassword(),
                client.getName()
        );

        loginClient(wrongEmailClient)
                .then()
                .log().all()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));

    }

    @Test
    @DisplayName("вход с неверным паролем")

    public void loginWithWrongPasswordTest(){



        createClientBefore();
        ClientModel wrongPasswordClient = new ClientModel(
                client.getEmail(),
                faker.regexify("[A-Za-z]{5,10}")+ "ya.ru",
                client.getName()
        );

        loginClient(wrongPasswordClient)
                .then()
                .log().all()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));

    }

}
