package org.java.appspring;

import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest{
    @LocalServerPort
    private int port;
    @Test
    public void testShouldDisplaySwaggerUi() {
        var content = RestAssured.given().basePath("/swagger-ui/index.html")
            .port(port)
                .when().get()
                .then().statusCode(200)
                .extract().body().asString();
    }
}
