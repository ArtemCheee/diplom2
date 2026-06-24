import io.restassured.RestAssured;
import model.ClientModel;
import org.junit.After;
import org.junit.Before;

import static steps.ClientSteps.deleteClient;

public class BaseApiTest {

    protected ClientModel client;
    protected String accessToken;
    public static final String BASE_URI = "https://stellarburgers.education-services.ru/";

    @Before

    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @After
    public void deleteClientAfterTest() {

        if (accessToken != null && !accessToken.isEmpty()) {
            deleteClient(accessToken);
        }
    }
}
