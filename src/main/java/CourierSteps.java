import io.qameta.allure.Step;
import io.restassured.response.Response;


public class CourierSteps {
    ProjectApiClient client = new ProjectApiClient();

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
}
