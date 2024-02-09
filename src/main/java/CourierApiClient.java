import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class CourierApiClient extends BaseHttpClient {
    private final String COURIER_CREATE_REQUEST = "api/v1/courier";
    private final String COURIER_LOGIN_REQUEST = "api/v1/courier/login";
    private final String DELETE_REQUEST = "api/v1/courier/";
    private final String CREATE_ORDER_REQUEST = "api/v1/orders/";
    private final String GET_ORDER_BY_TRACK = "api/v1/orders/track?t=";
    private final String GET_ORDERS_LIST = "/api/v1/orders/";

    private final String CANCEL_ORDER = "api/v1/orders/cancel?track=";



    public Response createCourier(Courier courier) {
        return doPostRequest(COURIER_CREATE_REQUEST, courier);
    }

    public Response loginCourier(Courier courier) {
        return doPostRequest(COURIER_LOGIN_REQUEST, courier);
    }

    public Response orderCreate(Order order) {
        return doPostRequest(CREATE_ORDER_REQUEST, order);
    }

    public Response getOrderByTrack(int track) {
       return doGetRequest(GET_ORDER_BY_TRACK + track);
    }

    public void deleteCourier(String id){
        doDeleteRequest(DELETE_REQUEST + id);
    }

    public Response getOrdersList(){
        return doGetRequest(GET_ORDERS_LIST);
    }

    public void orderCancelling(Integer trackNum){
        doPutRequest(CANCEL_ORDER + trackNum);
    }
}
