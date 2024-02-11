import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public abstract class BaseHttpClient {

    private RequestSpecification baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .addHeader("Content-type", "application/json")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

    protected Response doGetRequest(String path){ //Надстройка для GET-запроса
        return given()
                .spec(baseRequestSpec())
                .get(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body){ //Надстройка для POST-запроса
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .post(path);
    }

    protected Response doDeleteRequest(String path){ //Надстройка для DELETE-запроса
        return given()
                .spec(baseRequestSpec())
                .delete(path);
    }

    protected Response doPutRequest(String path){ //Надстройка для PUT-запроса
        return given()
                .spec(baseRequestSpec())
                .put(path);
    }
}
