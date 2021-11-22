package com.rest;

import io.restassured.config.EncoderConfig;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.config;
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

    @Test
    public void form_url_encoded(){
        given()
                .baseUri("https://postman-echo.com")
                .config(config().encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .formParam("key1", "value1")
                .formParam("key2", "value2")
                .log().all()
        .when()
                .post("/post")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
}
