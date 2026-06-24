import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.Test;
import steps.OrderSteps;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static steps.OrderSteps.createOrderWithAuth;
import static data.BurgerBuilder.burgerIngredientList;
import static data.ClientData.generateRandomClient;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static steps.ClientSteps.createClient;
import static steps.ClientSteps.loginClient;


public class MakeOrderTest extends BaseApiTest{
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
}

//
//
////        Response ingredientsResponse = getIngredientList()
////                .then()
////                .log().all()
////                .statusCode(HTTP_OK)
////                .extract().response();
////        List<String> ingredientIds = ingredientsResponse.jsonPath().getList("data._id");
////        Collections.shuffle(ingredientIds);
////        List<String> orderIngredients = ingredientIds.subList(0, 3);
//////        List<String> orderIngredients = Arrays.asList(
//////                ingredientIds.get(0),
//////                ingredientIds.get(1)
//////        );
////        OrderModel order = new OrderModel();
////        order.setIngredients(orderIngredients);
////
////        OrderSteps.createOrderWithAuth(order, accessToken)
////                .then()
////                .log().all()
////                .statusCode(HTTP_OK)
////                .body("success", equalTo(true))
////                .body("order", org.hamcrest.Matchers.notNullValue())
////                .extract().response();
//
//    }
//}
