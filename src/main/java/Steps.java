import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Steps {
    CourierApiClient client = new CourierApiClient();

    protected Response courierCreating(String login, String password) {
        Courier courier = new Courier(login, password);
        return client.createCourier(courier);
    }

    protected Response courierLogin(String login, String password){
        Courier courier = new Courier(login, password);
        return client.loginCourier(courier);
    }

    protected void deleteCourier(String login, String password){
        String id = courierLogin(login, password).jsonPath().getString("id");
        client.deleteCourier(id);
    }
}
