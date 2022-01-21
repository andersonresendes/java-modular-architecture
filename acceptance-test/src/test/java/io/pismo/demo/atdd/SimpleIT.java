package io.pismo.demo.atdd;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class SimpleIT extends AbstractContainerBaseTest {

  @Test
  void whenHasV1OpenApiThenReturn200() {
    given()
            .when()
            .get("/v1/openapi")
            .then()
            .statusCode(HttpStatus.SC_OK);
  }

}

