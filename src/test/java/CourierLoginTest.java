import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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

    @Step("Create courier")
    private Response createCourier(String login, String password, String name) {
        Courier courier = new Courier(login, password, name);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Links.CREATE_REQUEST);
    }

    @Step("Authorize courier")
    private Response courierAuthorization() {
        Courier courier = new Courier(login, password);
       return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Links.LOGIN_REQUEST);
    }
    @Step("Delete courier")
    private void deletingCourier() {
        String id = courierAuthorization()
                .then()
                .extract()
                .path("id").toString();
        given()
                .header("Content-type", "application/json")
                .delete(Links.DELETE_REQUEST + id);
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Links.BASE_URL;
        createCourier(login, password, name);
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Курьер может авторизоваться")
    public void courierAuthorizationTest(){
        courierAuthorization()
                .then()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void testAuthorizationWithWrongLogin(){
            Courier courier = new Courier(wrongLogin, password);
         given()
                 .header("Content-type", "application/json")
                 .and()
                 .body(courier)
                 .when()
                 .post(Links.LOGIN_REQUEST)
                 .then()
                 .statusCode(404)
                 .and()
                 .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Логин курьера")
    @Description("Если какого-то поля нет, запрос возвращает ошибку")
    public void testAuthorizationWithEmptyLogin(){
        Courier courier = new Courier(null, password);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Links.LOGIN_REQUEST)
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @AfterClass
    public void deleteCourier(){

    }

}
