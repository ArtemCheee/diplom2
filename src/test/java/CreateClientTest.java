
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.Response;
import model.ClientModel;
import org.junit.Test;


import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.ClientSteps.createClient;
import static data.ClientData.generateRandomClient;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateClientTest extends BaseApiTest {
    private static final Faker faker = new Faker();

    @Test
    @DisplayName("создать уникального пользователя")

    public void createClientTest() {

        createClientBefore();
        clientResponse.then()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("создать пользователя, который уже зарегистрирован")

    public void createExistingClientTest() {

        createClientBefore();
        createClient(client)
                .then()
                .log().all()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("создать пользователя и не заполнить одно из обязательных полей")

    public void createClientWithoutEmailTest() {

        ClientModel clientWithoutEmail = new ClientModel(
                null,
                faker.regexify("0-9]{8}"),
                faker.regexify("[a-zA-Z]{5,10}")
        );

        createClient(clientWithoutEmail)
                .then()
                .log().all()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("создать пользователя и не заполнить одно из обязательных полей")

    public void createClientWithoutPasswordTest() {

        ClientModel clientWithoutEmail = new ClientModel(
                faker.regexify("[a-z]{5,10}") + "@ya.ru",
                null,
                faker.regexify("[a-zA-Z]{5,10}")
        );

        createClient(clientWithoutEmail)
                .then()
                .log().all()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("создать пользователя и не заполнить одно из обязательных полей")

    public void createClientWithoutNameTest() {

        ClientModel clientWithoutEmail = new ClientModel(
                faker.regexify("[a-zA-Z]{5,10}") + "@ya.ru",
                faker.regexify("0-9]{8}"),
                null
        );

        createClient(clientWithoutEmail)
                .then()
                .log().all()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));

    }
}




