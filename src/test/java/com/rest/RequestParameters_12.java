package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


public class RequestParameters_12 {

    @Test
    public void single_query_parameter(){
        given()
                .baseUri("https://postman-echo.com/")
                .queryParam("foo1", "bar1")
                .log().all()
        .when()
                .get("/get")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void multiple_query_parameter(){
        given()
                .baseUri("https://postman-echo.com/")
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .log().all()
        .when()
                .get("/get")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void multi_value_query_params(){
        given()
                .baseUri("https://postman-echo.com/")
                .queryParam("foo1", "bar1,bar2,bar3,bar4")
                .log().all()
        .when()
                .get("/get")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    // Path parameter
    @Test
    public void path_parameter_example(){
        given()
                .baseUri("https://reqres.in")
                .pathParam("userId", "2")
                .log().all()
        .when()
                .get("/api/users/{userId}")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
}
