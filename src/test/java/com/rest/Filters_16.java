package com.rest;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
// filters are used to print log details
public class Filters_16 {


    @Test
    public void filter_logging(){
        given()
                .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.BODY))
                .filter(new ResponseLoggingFilter(LogDetail.BODY))
        .when()
                .get("/get")
        .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void logging_into_file() throws FileNotFoundException {
        PrintStream fileOutputStream = new PrintStream(new File("filter_logging.log"));
        given()
                .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(fileOutputStream))
                .filter(new ResponseLoggingFilter(fileOutputStream))
        .when()
                .get("/get")
        .then()
                .assertThat()
                .statusCode(200);
    }
}
