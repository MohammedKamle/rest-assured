package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesRegex;

public class JacksonApi_JsonObject_18 {

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.postman.com")
                .addHeader("X-Api-key", "PMAK-616285d31f6ba90052628ba4-4cf7a27ad04573552bca07f02e2dcc2c32")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }


    @Test(enabled = false)
    public void serialise_map_to_json_using_jackson() throws JsonProcessingException {
        Map<String, Object> masterObject = new HashMap<>();

        Map<String, String> nestedObject = new HashMap<>();
        nestedObject.put("name", "ZEE");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "This is created using API from Postman");

        masterObject.put("workspace", nestedObject);

        // Mapping Java Object to Json String using ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        String masterObjectString = objectMapper.writeValueAsString(masterObject);
        System.out.println("Master string :: "+masterObjectString);
        /*
         * VVV IMP
         * For the below process to work, we have added jackson library in pom
         * */

        given()
                .body(masterObjectString)
        .when()
                .post("/workspaces")
        .then()
                .log().body()
                .assertThat()
                .body("workspace.name", equalTo("ZEE"),
                        "workspace.id", matchesRegex("^[a-z0-9-]{36}$"));
    }


    @Test(enabled = false)
    public void serialise_json_using_jackson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode nestedObjectNode = objectMapper.createObjectNode();
        nestedObjectNode.put("name", "ZEE");
        nestedObjectNode.put("type", "personal");
        nestedObjectNode.put("description", "This is created using API from Postman");

        ObjectNode masterObjectNode = objectMapper.createObjectNode();
        masterObjectNode.set("workspace", nestedObjectNode);

        String masterObjectString = objectMapper.writeValueAsString(masterObjectNode);

        given()
                .body(masterObjectString)
        .when()
                .post("/workspaces")
        .then()
                .log().body()
                .assertThat()
                .body("workspace.name", equalTo("ZEE"),
                        "workspace.id", matchesRegex("^[a-z0-9-]{36}$"));
    }



}


