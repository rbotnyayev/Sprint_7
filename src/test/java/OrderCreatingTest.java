import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class OrderCreatingTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    Order order;
    OrderSteps step;
    Integer trackNum;

    public OrderCreatingTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name="Тестовые данные, цвет:{8}")
    public static Object[][] getCreditnails() {
        return new Object[][] {
                {"John", "Connor", "Peterson rd. 32, CA", "5", "+15246852485", 5, "2024-02-10", "Thanx", List.of("BLACK")},
                {"John", "Connor", "Peterson rd. 32, CA", "5", "+15246852485", 5, "2024-02-10", "Thanx", List.of("GREY")},
                {"John", "Connor", "Peterson rd. 32, CA", "5", "+15246852485", 5, "2024-02-10", "Thanx", Arrays.asList("BLACK", "GREY")},
                {"John", "Connor", "Peterson rd. 32, CA", "5", "+15246852485", 5, "2024-02-10", "Thanx", null}
        };
    }

    @Before
    public void setUp(){
        step = new OrderSteps();
        order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
    @Test
    @DisplayName("Создание заказа")
    public void testOrderCreationWithColorOption(){
        Response response = step.createOrder(order);
        trackNum = step.getTrackNum(response);
        step.getOrderData(trackNum)
                .then()
                .body("order.color", equalTo(color));
    }
    @After
    public void orderCancelling(){
        step.cancelOrder(trackNum); //Отмена ранее созданного заказа по его трэк-номеру
    }
}
