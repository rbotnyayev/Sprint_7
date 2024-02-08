import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    String login = "trevor";
    String password = "1234";
    String name = "Trevor";
    String wrongLogin = "franklin";
    Steps step;

    @Before
    public void setUp(){
        step = new Steps();
        step.courierCreating(login, password);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Курьер может авторизоваться")
    public void courierAuthorizationTest(){
        step.courierLogin(login, password)
                .then()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void testAuthorizationWithWrongLogin(){
            step.courierLogin(wrongLogin, password)
                 .then()
                 .statusCode(404)
                 .and()
                 .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Логин курьера")
    @Description("Если какого-то поля нет, запрос возвращает ошибку")
    public void testAuthorizationWithEmptyLogin(){
        step.courierLogin(null, password)
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void deleteCourier(){
        step.deleteCourier(login, password);
    }

}
