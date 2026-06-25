package steps;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import model.ClientModel;


import static data.ClientData.*;
import static io.restassured.RestAssured.given;

public class ClientSteps {

    @Step
    @DisplayName("Создание клиента")
    public static Response createClient(ClientModel client) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(client)
                .when()
                .post(CREATE_CLIENT_PATH)
                .then()
                .extract().response();

    }


    @Step
    @DisplayName("Удаление клиента")

    public static Response deleteClient(String accessToken) {
        return given()
                .log().all()
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE_CLIENT_PATH)
                .then()
                .log().all()
                .extract().response();

    }
    @Step
    @DisplayName("Логин клиента")
    public static Response loginClient(ClientModel client){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(client)
                .when()
                .post(LOGIN_CLIENT_PATH)
                .then()
                .extract().response();

    }

}
