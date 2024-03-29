package com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;

public class ReusingFilters_17 {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        PrintStream fileOutputStream = new PrintStream("filterLogging.log");
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter(fileOutputStream))
                .addFilter(new ResponseLoggingFilter(fileOutputStream));
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void logging_with_filter(){
        given(requestSpecification)
                .baseUri("https://postman-echo.com")
        .when()
                .get("/get")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200);
    }
}
