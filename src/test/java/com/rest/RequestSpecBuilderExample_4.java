package com.rest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;


public class RequestSpecBuilderExample_4 {
    RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-key", "PMAK-616285d31f6ba90052628ba4-4cf7a27ad04573552bca07f02e2dcc2c32");
        requestSpecification = requestSpecBuilder.build();
    }

    @Test
    public void validate_status_code(){
        // Non BDD style
        Response response = given(requestSpecification).get("/workspaces");
        assertThat(response.statusCode(), is(equalTo(200)));
    }

    @Test
    public void validate_response_body(){
        Response response = given(requestSpecification).get("/workspaces");
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name"), equalTo("My Workspace 2"));
    }


}
