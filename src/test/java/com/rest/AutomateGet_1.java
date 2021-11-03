package com.rest;

import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



/*
* Use the below as groovy playground
* https://www.jdoodle.com/execute-groovy-online/
*
* Json
* https://jsonpathfinder.com/
* http://jsonpath.com/
*
* */
public class AutomateGet_1 {

    @Test
    public void validate_get_status_code() {
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-616285d31f6ba90052628ba4-4cf7a27ad04573552bca07f02e2dcc2c32").
        when()
                .get("/workspaces").
        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("workspaces.name", hasItems("My Workspace 2", "myWorkspace", "My Workspace"))
                .body("workspaces.type", hasItems("personal","personal", "personal"))
                .body("workspaces[0].name", equalTo("My Workspace 2"))
                .body("workspaces.size", equalTo(3));
    }

    @Test
    public void extractResponse(){
        Response res= given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-616285d31f6ba90052628ba4-4cf7a27ad04573552bca07f02e2dcc2c32")
        .when()
                .get("/workspaces")
        .then()
                .extract()
                .response();

        //Extracting using JsonPath
        JsonPath jsonPath = new JsonPath(res.asString());
        System.out.println("workspace = "+jsonPath.getString("workspaces[0].name"));

        //Extracting using Json.from()
        System.out.println("workspace = "+JsonPath.from(res.asString()).getString("workspaces[0].name"));

        //Extracting using Groovy path
        System.out.println("workspace = "+res.path("workspaces[0].name"));

        // assertion using hamcrest
        assertThat(res.path("workspaces[0].name"), equalTo("My Workspace 2"));

        //assertion using testng
        Assert.assertEquals(res.path("workspaces[0].name"), "My Workspace 2");
    }

    @Test
    public void validate_response_body_with_hamcrest(){
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-616285d31f6ba90052628ba4-4cf7a27ad04573552bca07f02e2dcc2c32")
        .when()
                .get("/workspaces")
        .then()
                .assertThat()
                .statusCode(200)
                .body("workspaces.name", contains("My Workspace 2", "myWorkspace", "My Workspace"),
                        "workspaces.name", containsInAnyOrder("myWorkspace", "My Workspace", "My Workspace 2"),
                        "workspaces.name", not(empty()),
                        "workspaces.name", hasSize(3),
                        "workspaces[0]", hasKey("name"),
                        "workspaces[0]", hasValue("personal"),
                        "workspaces[0]", hasEntry("name", "My Workspace 2")
                );

    }

    /*
    * Explore different log methods here
    * */
    @Test
    public void request_response_logging(){
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-616285d31f6ba90052628ba4-4cf7a27ad04573552bca07f02e2dcc2c32")
                .log().all().
        when()
                .get("/workspaces").
        then()
                .log().all();
    }

    /*
    * Below will log the request by blacklisting headers
    * */
    @Test
    public void prevent_logging_sensitive_headers(){
        given()
                .baseUri("https://api.postman.com")
                .header("X-Api-Key", "PMAK-616285d31f6ba90052628ba4-4cf7a27ad04573552bca07f02e2dcc2c32")
                .config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key"))).
                log().all().
        when()
                .get("/workspaces").
        then()
                .assertThat()
                .statusCode(200);
    }





}
