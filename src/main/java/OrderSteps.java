import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderSteps {
    ProjectApiClient client = new ProjectApiClient();
    @Step("Создание заказа")
    protected Response createOrder(Order order) {
        return client.orderCreate(order);
    }
    @Step("Получение данных о заказе через его трэк-номер")
    protected Response getOrderData(int track) {
        return client.getOrderByTrack(track);
    }

    @Step("Получение трэк-номера заказа")
    protected int getTrackNum(Response response){
        return response.jsonPath().getInt("track");
    }

    @Step("Получение списка заказов")
    protected Response getOrdersList(){
        return client.getOrdersList();
    }

    @Step("Отмена заказа")
    protected void cancelOrder(Integer trackNum){
        client.orderCancelling(trackNum);
    }
}
