package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FormData_13 {

    @Test
    public void multi_part_form_data(){
        given()
                .baseUri("https://postman-echo.com")
                .multiPart("foo1", "bar1")
                .multiPart("foo2", "bar2")
                .log().all()
        .when()
                .post("/post")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
}
