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
    Steps step;
    @Before
    public void setUp(){
      step = new Steps();
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Курьера можно создать")
    public void courierCreatingTest(){
       step.courierCreating(login, password)
               .then()
               .statusCode(201)
               .and()
               .assertThat().body("ok", equalTo(true));

       step.courierLogin(login, password)
               .then().assertThat()
               .body("id", notNullValue())
               .and().statusCode(200);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Нельзя создать двух одинаковых курьеров")
    public void duplicateCourierCreatingTest(){
        step.courierCreating(login, password);
        step.courierCreating(login, password)
                .then()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Если одного из полей нет, запрос возвращает ошибку")
    public void testCreatingCourierWithMissingFields(){
        step.courierCreating(null, password);
    }

    @After
    public void deleteCourier(){
        try {
            step.deleteCourier(login, password);
        } catch (NullPointerException e) {
            System.out.println("Ошибка! Невозможно удалить несуществующего курьера");
        }
    }
}
