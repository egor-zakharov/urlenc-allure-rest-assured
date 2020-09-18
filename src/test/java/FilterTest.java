import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.filter.UrlencAllureRestAssured;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class FilterTest {

    private static RequestSpecification requestSpecification;

    @BeforeAll
    public static void setUp() {
        HashMap<String, String> formParamsMap = new HashMap<>();
        formParamsMap.put("param1", "value1");
        formParamsMap.put("param2", "value2");

        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://petstore.swagger.io")
                .setBasePath("v2/pet")
                .addHeader("api_key", "mySecretKey")
                .setContentType(ContentType.URLENC.withCharset("UTF-8"))
                .addFormParams(formParamsMap)
                .log(LogDetail.ALL).build();
    }

    @Test
    public void defaultFilterTest() {
        given(requestSpecification)
                .filter(new AllureRestAssured())
                .post();
    }

    @Test
    public void customFilterTest() {
        given(requestSpecification)
                .filter(new UrlencAllureRestAssured())
                .post();
    }
}
