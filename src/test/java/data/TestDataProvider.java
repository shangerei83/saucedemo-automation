package data;

public class TestDataProvider {

     public static Object[][] browsers() {
        return new Object[][]{
                {"firefox"},
                {"edge"}
        };
    }

       public static Object[][] loginTestData() {
        return new Object[][]{
                {"", "", "Username is required"},
                {"standard_user", "", "Password is required"},
                {"standard_user", "secret_sauce", "Swag Labs"},
                {"invalid_user", "wrong_password", "Username and password do not match"}
        };
    }
}