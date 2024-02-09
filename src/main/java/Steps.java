import io.qameta.allure.Step;
import io.restassured.response.Response;

public class Steps {
    CourierApiClient client = new CourierApiClient();

    @Step("Создание курьера")
    protected Response courierCreating(String login, String password) {
        Courier courier = new Courier(login, password);
        return client.createCourier(courier);
    }

    @Step("Логин курьера")
    protected Response courierLogin(String login, String password){
        Courier courier = new Courier(login, password);
        return client.loginCourier(courier);
    }

    @Step("Получение id курьера")
    protected String getCourierId(String login, String password){
        return courierLogin(login, password).jsonPath().getString("id");
    }

    @Step("Удаление курьера")
    protected void deleteCourier(String login, String password){
        client.deleteCourier(getCourierId(login, password));
    }

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
