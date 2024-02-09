import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    String login = "trevor";
    String password = "1234";
    String wrongLogin = "franklin"; //неверный логин
    Steps step;

    @Before
    public void setUp(){
        step = new Steps(); //Создаём объект класса с шагами
        step.courierCreating(login, password); //Создаём курьера
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Курьер может авторизоваться")
    public void courierAuthorizationTest(){
        step.courierLogin(login, password) //Авторизация курьера по логину и паролю
                .then()
                .statusCode(200) //Ожидание статус-кода 200
                .and()
                .body("id", notNullValue()); //Проверка, что поле id в теле ответа не пустое
    }

    @Test
    @DisplayName("Логин курьера")
    @Description("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void testAuthorizationWithWrongLogin(){
            step.courierLogin(wrongLogin, password) //Попытка авторизоваться под несуществующим пользователем
                 .then()
                 .statusCode(404) //Ожидание статус-кода 404
                 .and()
                 .body("message", equalTo("Учетная запись не найдена")); //Проверка тела ответа на соответствующий текст
    }
    @Test
    @DisplayName("Логин курьера")
    @Description("Если какого-то поля нет, запрос возвращает ошибку")
    public void testAuthorizationWithEmptyLogin(){
        step.courierLogin(null, password) //Попытка авторизации с пустым полем "Логин"
                .then()
                .statusCode(400) //Ожидание статус-кода 400
                .and()
                .body("message", equalTo("Недостаточно данных для входа")); //Проверка тела ответа на соответствующий текст
    }

    @After
    public void deleteCourier(){ //Удаляем курьера после каждого тестового метода
        step.deleteCourier(login, password);
    }

}
