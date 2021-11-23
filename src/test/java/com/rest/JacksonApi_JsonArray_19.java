package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class JacksonApi_JsonArray_19 {

    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://9f3620cd-d3f7-4920-8973-22b5120420e2.mock.pstmn.io")
                .addHeader("x-mock-match-request-body", "true")
                //.setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);;
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .log(LogDetail.ALL);
        // RestAssured.responseSpecification = responseSpecBuilder.build();
        // logging works with below and not above
        customResponseSpecification = responseSpecBuilder.build();
    }

    @Test()
    public void serialise_list_to_json_using_jackson() throws JsonProcessingException {
        Map<String, String> obj1 = new HashMap<>();
        obj1.put("id", "5001");
        obj1.put("type","None");

        Map<String, String> obj2 = new HashMap<>();
        obj2.put("id", "5002");
        obj2.put("type","Glaze");

        List<Map> listOfObjects = new ArrayList<>();
        listOfObjects.add(obj1);
        listOfObjects.add(obj2);

        //Serialising List object to string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonListString = objectMapper.writeValueAsString(listOfObjects);

        given()
                .body(jsonListString)
        .when()
                .post("/post")
                // .spec() is requires when we use customResponseSpecification
        .then()
                .spec(customResponseSpecification)
                .assertThat()
                .body("msg", equalTo("Success"));

    }

    @Test()
    public void serialise_json_array_using_jackson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNodeList = objectMapper.createArrayNode();

        ObjectNode obj1 = objectMapper.createObjectNode();
        obj1.put("id", "5001");
        obj1.put("type","None");

        ObjectNode obj2 = objectMapper.createObjectNode();
        obj2.put("id", "5002");
        obj2.put("type","Glaze");

        arrayNodeList.add(obj1);
        arrayNodeList.add(obj2);


        String jsonListString = objectMapper.writeValueAsString(arrayNodeList);

        given()
                .body(arrayNodeList)
                .when()
                .post("/post")
                // .spec() is requires when we use customResponseSpecification
                .then()
                .spec(customResponseSpecification)
                .assertThat()
                .body("msg", equalTo("Success"));

    }
}
