import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.ClientModel;
import org.junit.Test;

import static data.ClientData.generateRandomClient;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.core.IsEqual.equalTo;
import static steps.ClientSteps.createClient;
import static steps.ClientSteps.loginClient;

public class LoginClientTest extends BaseApiTest {

    private static final Faker faker = new Faker();

    @Test
    @DisplayName("вход под существующим пользователем")

    public void loginWithExistingClientTest(){
        client = generateRandomClient();

        createClient(client)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true));

        Response response = loginClient(client)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .extract().response();
        accessToken = response.jsonPath().getString("accessToken");;

    }
    @Test
    @DisplayName("вход с неверным логином")

    public void loginWithWrongEmailTest(){
        client = generateRandomClient();

        Response response =  createClient(client)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
        .extract().response();
        accessToken = response.jsonPath().getString("accessToken");

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
        client = generateRandomClient();

        Response response =  createClient(client)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .extract().response();
        accessToken = response.jsonPath().getString("accessToken");

        ClientModel wrongEmailClient = new ClientModel(
                client.getEmail(),
                faker.regexify("[A-Za-z]{5,10}")+ "ya.ru",
                client.getName()
        );

        loginClient(wrongEmailClient)
                .then()
                .log().all()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));

    }

}
