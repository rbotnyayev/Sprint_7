import io.restassured.response.Response;

public class CourierApiClient extends BaseHttpClient {
    private final String COURIER_CREATE_REQUEST = "api/v1/courier";
    private final String COURIER_LOGIN_REQUEST = "api/v1/courier/login";
    private final String DELETE_REQUEST = "api/v1/courier/";
    private final String CREATE_ORDER_REQUEST = "api/v1/orders/";
    private final String GET_ORDER_BY_TRACK = "v1/orders/track?t=";



    public Response createCourier(Courier courier) {
        return doPostRequest(COURIER_CREATE_REQUEST, courier);
    }

    public Response loginCourier(Courier courier) {
        return doPostRequest(COURIER_LOGIN_REQUEST, courier);
    }

    public Response orderCreate(Order order) {
        return doPostRequest(CREATE_ORDER_REQUEST, order);
    }

    public Response getOrderByTrack(String track) {
       return doGetRequest(GET_ORDER_BY_TRACK + track);
    }

    public Response deleteCourier(String id){
        return doDeleteRequest(DELETE_REQUEST + id);
    }
}
