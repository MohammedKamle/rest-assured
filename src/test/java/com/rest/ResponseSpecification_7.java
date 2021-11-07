package com.rest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

/*
* For Validating repetative response assertions
* */
public class ResponseSpecification_7 {
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-key", "PMAK-616285d31f6ba90052628ba4-4cf7a27ad04573552bca07f02e2dcc2c32");
        RestAssured.requestSpecification = requestSpecBuilder.build();

        // for code reusability
        responseSpecification = RestAssured.expect()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void validate_status_code(){
        get("/workspaces")
                .then().spec(responseSpecification);
    }

    @Test
    public void validate_response_body(){
        Response response = get("/workspaces")
                .then().spec(responseSpecification)
                .extract().response();
        assertThat(response.path("workspaces[0].name"), equalTo("My Workspace 2"));
    }
}
