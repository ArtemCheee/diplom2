package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static data.MakeOrderData.CREATE_ORDER_PATH;
import static data.MakeOrderData.GET_INTGREDIENT_LIST_PATH;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    private static String accessToken;

    @Step("Список ингридиентов")

    public static Response getIngredientList(){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(GET_INTGREDIENT_LIST_PATH)
                .then()
                .extract().response();
    }

    @Step("Создать заказ с авторизацией")

    public static Response createOrderWithAuth(OrderModel order, String accessToken){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Создать заказ без авторизацией")

    public static Response createOrderWithoutAuth(OrderModel order){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }
}
