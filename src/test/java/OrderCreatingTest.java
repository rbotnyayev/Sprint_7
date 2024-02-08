import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

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
    Steps step;

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

    @Parameterized.Parameters
    public static Object[][] getCreditnails() {
        return new Object[][] {
                {"John", "Conner", "Peterson rd. 32, CA", "5", "+15246852485", 5, "2024-02-10", "Thanx", List.of("BLACK")},
                {"John", "Conner", "Peterson rd. 32, CA", "5", "+15246852485", 5, "2024-02-10", "Thanx", List.of("GREY")},
                {"John", "Conner", "Peterson rd. 32, CA", "5", "+15246852485", 5, "2024-02-10", "Thanx", List.of("BLACK", "GREY")},
                {"John", "Conner", "Peterson rd. 32, CA", "5", "+15246852485", 5, "2024-02-10", "Thanx", null}
        };
    }

    @Before
    public void setUp(){
        step = new Steps();
        order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
    @Test
    @DisplayName("Создание заказа")
    public void testOrderCreation(){
        Response response = step.createOrder(order);
        response
                .then()
                .statusCode(201)
                .and()
                .body("track", notNullValue());

        int trackNum = response.jsonPath().getInt("track");
        step.getOrderData(trackNum)
                .then()
                .body("order.color", equalTo(color));
    }
}
