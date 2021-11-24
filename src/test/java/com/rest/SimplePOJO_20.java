package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simple.SimplePOJO;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimplePOJO_20 {
   ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void simple_pojo_serialization(){
        SimplePOJO simplePOJO = new SimplePOJO("valeu1", "value2");
        // RestAssured will automatically serialse the POJO to json object
        given()
                .body(simplePOJO)
        .when()
                .post("/postSimplePOJO")
        .then()
                .spec(responseSpecification)
                .assertThat()
                .body("key1", equalTo(simplePOJO.getKey1()),
                        "key2", equalTo(simplePOJO.getKey2()));
    }

    @Test
    public void simple_pojo_deserialization() throws JsonProcessingException {
        SimplePOJO simplePOJO = new SimplePOJO("valeu1", "value2");

        SimplePOJO simplePOJODeserialized =
        // RestAssured will automatically serialse the POJO to json object
        given()
                .body(simplePOJO)
        .when()
                .post("/postSimplePOJO")
        .then()
                .spec(responseSpecification)
                .extract()
                .response()
                .as(SimplePOJO.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String simplePojoString = objectMapper.writeValueAsString(simplePOJO);
        String deserializedPojoString = objectMapper.writeValueAsString(simplePOJODeserialized);

        // asserting simplePojoString and deserializedPojoString
        assertThat(objectMapper.readTree(deserializedPojoString), equalTo(objectMapper.readTree(simplePojoString)));

    }

}
