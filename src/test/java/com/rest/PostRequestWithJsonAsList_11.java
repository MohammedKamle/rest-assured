package com.rest;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostRequestWithJsonAsList_11 {

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
    public void validate_post_request_payload_json_array_as_list(){
        Map<String, String> obj1 = new HashMap<>();
        obj1.put("id", "5001");
        obj1.put("type","None");

        Map<String, String> obj2 = new HashMap<>();
        obj2.put("id", "5002");
        obj2.put("type","Glaze");

        List<Map> listOfObjects = new ArrayList<>();

        listOfObjects.add(obj1);
        listOfObjects.add(obj2);
        given()
                .body(listOfObjects)
        .when()
                .post("/post")
        // .spec() is requires when we use customResponseSpecification
        .then()
                .spec(customResponseSpecification)
                .assertThat()
                .body("msg", equalTo("Success"));

    }

    @Test()
    public void validate_post_request_payload_complex_json(){
        Map<String, Object> batterHashMap1 = new HashMap<>();
        batterHashMap1.put("id", "1001");
        batterHashMap1.put("type", "Regular");

        List<Integer> idArrayList = new ArrayList<>();
        idArrayList.add(5);
        idArrayList.add(9);

        Map<String, Object> batterHashMap2 = new HashMap<>();
        batterHashMap2.put("id", idArrayList);
        batterHashMap2.put("type", "Chocolate");

        List<Map<String, Object>> battersArrayList = new ArrayList<>();
        battersArrayList.add(batterHashMap1);
        battersArrayList.add(batterHashMap2);

        Map<String, List<Map<String, Object>>> battersHaspMap = new HashMap<>();
        battersHaspMap.put("batter", battersArrayList);

        Map<String, Object> toppingsHashMap1 = new HashMap<>();
        toppingsHashMap1.put("id", "5001");
        toppingsHashMap1.put("type", "None");

        List<String> typesArrayList = new ArrayList<>();
        typesArrayList.add("test1");
        typesArrayList.add("test2");

        Map<String, Object> toppingsHashMap2 = new HashMap<>();
        toppingsHashMap2.put("id", "5002");
        toppingsHashMap2.put("type", typesArrayList);

        List<Map<String, Object>> toppingsArrayList = new ArrayList<>();
        toppingsArrayList.add(toppingsHashMap1);
        toppingsArrayList.add(toppingsHashMap2);

        Map<String, Object> mainJson = new HashMap<>();
        mainJson.put("ppu", "0.55");
        mainJson.put("batters", battersHaspMap);
        mainJson.put("name", "Cake");
        mainJson.put("id", "0001");
        mainJson.put("type", "donut");
        mainJson.put("topping", toppingsArrayList);


        given()
                .body(mainJson)
        .when()
                .post("/postComplexJSON")
                // .spec() is requires when we use customResponseSpecification
        .then()
                .spec(customResponseSpecification)
                .assertThat()
                .body("msg", equalTo("Success"));
    }

}
