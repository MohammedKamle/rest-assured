package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidation_15 {

    @Test
    public void validate_json_schema(){
        given()
                .baseUri("https://postman-echo.com")
                .log().all()
        .when()
                .get("/get")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                // it will automatically take the json schema from resources
                .body(matchesJsonSchemaInClasspath("EchoGetJsonSchema.json"));
    }

}
