package data;

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static steps.OrderSteps.getIngredientList;

public class BurgerBuilder {

    private static final String BUN_1 = "61c0c5a71d1f82001bdaaa6d";
    private static final String BUN_2 = "61c0c5a71d1f82001bdaaa6c";
    private static final Random random = new Random();

    public static List<String> burgerIngredientList() {

        List<String> allIngredients = getIngredientList()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("data._id");

        List<String> otherIngredients = new ArrayList<>(allIngredients);
        otherIngredients.remove(BUN_1);
        otherIngredients.remove(BUN_2);

        List<String> randomIngredients = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
        int randomIndex = random.nextInt(otherIngredients.size());
        randomIngredients.add(otherIngredients.get(randomIndex));
}
        String requiredIngredient;

        if (random.nextBoolean()) {
            requiredIngredient = BUN_1;
        } else {
            requiredIngredient = BUN_2;
        }

        List<String> burgerIngredients = new ArrayList<>();
        burgerIngredients.add(requiredIngredient);
        burgerIngredients.addAll(randomIngredients);

        return burgerIngredients;
    }

}