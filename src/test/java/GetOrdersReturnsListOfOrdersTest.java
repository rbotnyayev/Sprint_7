import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersReturnsListOfOrdersTest {
    private String firstName = "Franklin";
    private String lastName = "Clinton";
    private String address = "Los Santos";
    private String metroStation = "5";
    private String phone = "+125465258";
    private int rentTime = 5;
    private String deliveryDate = "2024-02-15";
    private String comment = "Come to Vinewood Hills";
    private List<String> color;
    Steps step;

    @Before
    public void setUp(){
        step = new Steps(); //Создаём объект класса с шагами
    }

    @Test
    @DisplayName("Список заказов")
    @Description("Проверка, что в тело ответа возвращается список заказов.")
    public void testGetOrderReturnListOfOrders(){
        step.getOrdersList() //Выполняем гет-запрос на получение списка заказов
                .then()
                .statusCode(200) //Ожидание статус-кода 200
                .and()
                .body("orders", notNullValue()); //Ожидание того, что поле orders в теле ответе не будет пустым
    }
}
