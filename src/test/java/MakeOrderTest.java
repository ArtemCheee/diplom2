import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.Test;
import java.util.List;

import static java.net.HttpURLConnection.*;
import static steps.OrderSteps.createOrderWithAuth;
import static data.BurgerBuilder.burgerIngredientList;
import static data.ClientData.generateRandomClient;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static steps.ClientSteps.createClient;
import static steps.ClientSteps.loginClient;
import static steps.OrderSteps.createOrderWithoutAuth;


public class MakeOrderTest extends BaseApiTest {
    @Test
    @DisplayName("Создание заказа с авторизацией")

    public void createOrderWithAuthorization() {
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
        accessToken = response.jsonPath().getString("accessToken");

        List<String> burgerIngredients = burgerIngredientList();

        OrderModel order = new OrderModel();
        order.setIngredients(burgerIngredients);

        createOrderWithAuth(order, accessToken)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());


    }

    @Test
    @DisplayName("Создание заказа ,без авторизации")
    public void createOrderWithutAuthorization() {


        List<String> burgerIngredients = burgerIngredientList();

        OrderModel order = new OrderModel();
        order.setIngredients(burgerIngredients);

        createOrderWithoutAuth(order)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());


    }

    @Test
    @DisplayName("Создание заказа c ингредиентами")

       public void createOrderWithIngredientsTest(){

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
            accessToken = response.jsonPath().getString("accessToken");

            List<String> burgerIngredients = burgerIngredientList();

            OrderModel order = new OrderModel();
            order.setIngredients(burgerIngredients);

            createOrderWithAuth(order, accessToken)
                    .then()
                    .log().all()
                    .statusCode(HTTP_OK)
                    .body("success", equalTo(true))
                    .body("order.number", notNullValue());


        }
    @Test
    @DisplayName("Создание заказа без ингредиентов")

    public void createOrderWithoutIngredientsTest(){
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
        accessToken = response.jsonPath().getString("accessToken");

        OrderModel order = new OrderModel();

        createOrderWithAuth(order, accessToken)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")

    public void createOrderWithWrongIngredientHash(){

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
        accessToken = response.jsonPath().getString("accessToken");

        List<String> burgerIngredients = burgerIngredientList();
        String wrongIngredientHash = "pink_goose"; // а на русском "розовый гусь" даст 200, сервер просто берет 2 ингр-та
        burgerIngredients.set(0, wrongIngredientHash);

        OrderModel order = new OrderModel();
        order.setIngredients(burgerIngredients);

        createOrderWithAuth(order, accessToken)
                .then()
                .log().all()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

}
