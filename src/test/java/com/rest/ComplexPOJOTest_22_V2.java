package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.collection.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ComplexPOJOTest_22_V2 {

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


    @org.testng.annotations.Test
    public void complex_pojo_create_collection() throws JsonProcessingException, JSONException {
        Header header = new Header("Content-Type", "application/json");
        List<Header> headerList = new ArrayList<>();
        headerList.add(header);

        Body body = new Body("raw", "{\"data\": \"123\"}");

        RequestRequest request = new RequestRequest("https://postman-echo.com/post", "POST", headerList,
                body, "This is a sample POST Request");

        RequestRootRequest requestRoot = new RequestRootRequest("Sample POST Request", request);
        List<RequestRootRequest> requestRootList = new ArrayList<>();
        requestRootList.add(requestRoot);

        FolderRequest folder = new FolderRequest("This is a folder", requestRootList);
        List<FolderRequest> folderList = new ArrayList<>();
        folderList.add(folder);

        Info info = new Info("Sample Collection 909", "This is just a sample collection.",
                "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        CollectionRequest collection = new CollectionRequest(info, folderList);

        CollectionRootRequest collectionRoot = new CollectionRootRequest(collection);
        // Getting the uid of newly after creating a collection
        String collectionUid =
                given()
                        .body(collectionRoot)
                        .when()
                        .post("/collections")
                        .then()
                        .spec(responseSpecification)
                        .extract()
                        .path("collection.uid");

        // Getting
        CollectionRootResponse deserializedCollectionRoot =
                given()
                        .pathParam("collectionUid", collectionUid)
                        .when()
                        .get("/collections/{collectionUid}")
                        .then()
                        .spec(responseSpecification)
                        .extract()
                        .response()
                        .as(CollectionRootResponse.class);

        // Serializing
        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootString = objectMapper.writeValueAsString(collectionRoot);
        String deserializedCollectionRootString = objectMapper.writeValueAsString(deserializedCollectionRoot);

        // assertion
//        JSONAssert.assertEquals(collectionRootString, deserializedCollectionRootString,
//                new CustomComparator(JSONCompareMode.LENIENT,
//                        new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
//                            public boolean equal(Object o1, Object o2) {
//                                return true;
//                            }
//                        })));

        // Alternative assertion
        List<String> urlRequestList = new ArrayList<>();
        List<String> urlResponseList = new ArrayList<>();

        for(RequestRootRequest requestRootRequest: requestRootList){
            System.out.println("URL from request :: "+requestRootRequest.getRequest().getUrl());
            urlRequestList.add(requestRootRequest.getRequest().getUrl());
        }

        List<FolderResponse> folderResponseList = deserializedCollectionRoot.getCollection().getItem();
        for (FolderResponse folderResponse: folderResponseList){
            List<RequestRootResponse> requestRootResponseList = folderResponse.getItem();
            for (RequestRootResponse requestRootResponse: requestRootResponseList){
                URL url = requestRootResponse.getRequest().getUrl();
                System.out.println("URL from response :: "+ url.getRaw());
                urlResponseList.add(url.getRaw());
            }
        }

        assertThat(urlResponseList, containsInAnyOrder(urlRequestList.toArray()));



    }

}
