import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class CourierCreatingTest {


    String login = "trevorfillipscorp";
    String password = "1234";
    String name = "Alexander";

    @Step("Create courier")
    private Response createCourier(String login, String password, String name){
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
                .jsonPath()
                .getString("id");
        given()
                .header("Content-type", "application/json")
                .delete(Links.DELETE_REQUEST + id);
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = Links.BASE_URL;
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Курьера можно создать")
    public void courierCreatingTest(){
       createCourier(login, password, name).then().
               statusCode(201)
               .and()
               .assertThat().body("ok", equalTo(true));

       //Проверка созданного курьера - логин с его данными в системе
       courierAuthorization()
               .then().assertThat()
               .body("id", notNullValue())
               .and().statusCode(200);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Нельзя создать двух одинаковых курьеров")
    public void duplicateCourierCreatingTest(){
        createCourier(login, password, name);
        createCourier(login, password, name)
                .then()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Если одного из полей нет, запрос возвращает ошибку")
    public void testCreatingCourierWithMissingFields(){
        createCourier(null, password, name).then().statusCode(400);
    }

    @After
    public void deleteCourier(){
        try {
            deletingCourier();
        } catch (NullPointerException e) {
            System.out.println("Ошибка! Невозможно удалить несуществующего курьера");
        }
    }
}
