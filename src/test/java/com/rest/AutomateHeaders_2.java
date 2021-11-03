package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;
import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class AutomateHeaders_2 {

    @Test
    public void validate_header1(){
        given()
                .baseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io")
                .header("headerName", "value1")
                .header("x-mock-match-request-headers", "headerName").
        when()
                .get("getH").
        then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void validate_header2(){
        given()
                .baseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io")
                .header("headerName", "value2")
                .header("x-mock-match-request-headers", "headerName").
        when()
                .get("getH").
        then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void passing_multiple_headers(){
        Header header1 = new Header("headerName", "value1");
        Header header2 = new Header("x-mock-match-request-headers", "headerName");

        Headers headers = new Headers(header1,header2);

        given()
                .baseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io")
                .headers(headers).
        when()
                .get("getH").
        then()
                .log().all()
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void passing_multiple_headers_with_map(){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("headerName", "value2");
        headers.put("x-mock-match-request-headers", "headerName");

        given()
                .baseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io")
                .headers(headers).
        when()
                .get("getH").
        then()
                .log().all()
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void multi_value_header_in_the_request(){
        Header header1 = new Header("multiValueHeader", "value1");
        Header header2 = new Header("multiValueHeader", "value2");

        Headers headers = new Headers(header1,header2);

        given()
                .baseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io")
                .headers(headers)
                .log().headers().
       when()
                .get("getH").
       then()
                .log().all()
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void validate_response_header(){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("headerName", "value1");
        headers.put("x-mock-match-request-headers", "headerName");

        given()
                .baseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io")
                .headers(headers).
        when()
                .get("getH").
        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .header("responseHeader", "resValue1");

    }

    @Test
    public void extract_response_headers(){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("headerName", "value1");
        headers.put("x-mock-match-request-headers", "headerName");

        Headers extractedHeaders =

        given()
                .baseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io")
                .headers(headers).
        when()
                .get("getH").
        then()
                .assertThat()
                .statusCode(200)
                .extract()
                .headers();

        System.out.println(extractedHeaders.get("responseHeader").getName());
        System.out.println(extractedHeaders.get("responseHeader").getValue());

    }

    @Test
    public void extract_multi_value_header_in_response(){

        Headers extractedHeaders =

                given()
                        .baseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io").
                when()
                        .get("getH").
                then()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .headers();

        List<String> values = extractedHeaders.getValues("multiValueHeader");

        for (String value: values ){
            System.out.println(value);
        }

    }





}
