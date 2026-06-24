package data;

import com.github.javafaker.Faker;
import model.ClientModel;

public class ClientData {

    static Faker user = new Faker();

    public static String generateRandomEmail() {
        return user.regexify("[a-zA-Z]{5,10}") + user.number().randomNumber(4, true) + "@ya.ru";
    }

    public static String generateRandomPassword() {
        return user.regexify("[0-9]{8}");
    }

    public static String generateRandomName() {
        return user.name().name();
    }

    public static ClientModel generateRandomClient() {
        return new ClientModel(
                generateRandomEmail(),
                generateRandomPassword(),
                generateRandomName()
        );
    }


    public static final String CREATE_CLIENT_PATH = "api/auth/register";
    public static final String LOGIN_CLIENT_PATH = "api/auth/login";
    public static final String DELETE_CLIENT_PATH = "api/auth/user";
}
