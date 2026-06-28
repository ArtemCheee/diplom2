import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.ClientModel;
import org.junit.After;
import org.junit.Before;

import static data.ClientData.generateRandomClient;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.core.IsEqual.equalTo;
import static steps.ClientSteps.*;

public class BaseApiTest {

    protected ClientModel client;
    protected String accessToken;
    public static final String BASE_URI = "https://stellarburgers.education-services.ru/";
    protected Response clientResponse;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    protected void createClientBefore() {
        client = generateRandomClient();


        clientResponse = createClient(client)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();


        accessToken = clientResponse.jsonPath().getString("accessToken");
    }

    protected void loginClientBefore() {

        Response response = loginClient(client)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .extract().response();
        accessToken = response.jsonPath().getString("accessToken");
    }

    @After
    public void deleteClientAfterTest() {

        if (accessToken != null && !accessToken.isEmpty()) {
            deleteClient(accessToken);
        }
    }
}
