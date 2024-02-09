import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class CourierCreatingTest {
    String login = "trevorfillipscorp";
    String password = "1234";
    Steps step;
    @Before
    public void setUp(){
      step = new Steps();
    } //Создаём объект класса с шагами

    @Test
    @DisplayName("Создание курьера")
    @Description("Курьера можно создать")
    public void courierCreatingTest(){
       step.courierCreating(login, password) //Создаём курьера
               .then()
               .statusCode(201) //Проверяем статус-код
               .and()
               .assertThat().body("ok", equalTo(true)); //Проверяем тело ответа

       step.courierLogin(login, password) //Проверка того, что курьер создан - авторизация
               .then().assertThat()
               .body("id", notNullValue()) //Ответ с телом id курьера
               .and().statusCode(200); //Статус-код авторизации
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Нельзя создать двух одинаковых курьеров")
    public void duplicateCourierCreatingTest(){
        step.courierCreating(login, password); //Создаём курьера
        step.courierCreating(login, password) //Пытаемся повторно создать курьера
                .then()
                .statusCode(409) //Статус-код о существуюшем курьере
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой.")); //Тело ответа
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Если одного из полей нет, запрос возвращает ошибку")
    public void testCreatingCourierWithMissingFields(){
        step.courierCreating(null, password)
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи")); //Создаём курьера с null вместо логина
    }

    @After
    public void deleteCourier(){ //Удаляем курьера после каждого тестаового метода
        try {
            step.deleteCourier(login, password);
        } catch (NullPointerException e) {
            System.out.println("Ошибка! Невозможно удалить несуществующего курьера");
        }
    }
}
