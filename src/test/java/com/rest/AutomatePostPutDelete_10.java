package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesRegex;

public class AutomatePostPutDelete_10 {

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
    public void validate_post_request(){
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"d8ba6e2e-082d-476e-84f1-0b922a055922\",\n" +
                "        \"name\": \"MdkWorkspace_1\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is created using API from Postman\"\n" +
                "    }\n" +
                "}";
        given()
                .body(payload)
        .when()
                .post("/workspaces")
        .then()
                .log().body()
                .assertThat()
                .body("workspace.name", equalTo("MdkWorkspace_1"),
                        "workspace.id", matchesRegex("^[a-z0-9-]{36}$"));
    }

    @Test(enabled = false)
    public void validate_post_request_with_payload_from_external_file(){
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        given()
                .body(file)
        .when()
                .post("/workspaces")
        .then()
                .log().body()
                .assertThat()
                .body("workspace.name", equalTo("zee 25"),
                        "workspace.id", matchesRegex("^[a-z0-9-]{36}$"));
    }

    @Test()
    public void validate_post_request_with_payload_using_map(){
        Map<String, Object> masterObject = new HashMap<>();

        Map<String, String> nestedObject = new HashMap<>();
        nestedObject.put("name", "ZEE");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "This is created using API from Postman");

        /*
        * VVV IMP
        * For the below process to work, we have added jackson library in pom
        * */
        masterObject.put("workspace", nestedObject);
        given()
                .body(masterObject)
        .when()
                .post("/workspaces")
        .then()
                .log().body()
                .assertThat()
                .body("workspace.name", equalTo("ZEE"),
                        "workspace.id", matchesRegex("^[a-z0-9-]{36}$"));
    }

    @Test(enabled = false)
    public void validate_post_with_non_bdd(){
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"d8ba6e2e-082d-476e-84f1-0b922a055922\",\n" +
                "        \"name\": \"MdkWorkspace_2\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is created using API from Postman\"\n" +
                "    }\n" +
                "}";

        Response response = with().body(payload).post("/workspaces");

        assertThat(response.path("workspace.name"), equalTo("MdkWorkspace_2"));
        assertThat(response.path("workspace.id"),matchesRegex("^[a-z0-9-]{36}$") );
    }

    @Test(enabled = false)
    public void validate_put_request_bdd(){
        String workspaceId = "f1aa931f-e7a7-41f8-875c-0f51c8205d69";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"d8ba6e2e-082d-476e-84f1-0b922a055922\",\n" +
                "        \"name\": \"MdkWorkspace_11\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is created using API from Postman\"\n" +
                "    }\n" +
                "}";

        given()
                .body(payload)
                .pathParam("workspaceId", workspaceId)
        .when()
                .put("/workspaces/{workspaceId}")
        .then()
                .assertThat()
                .body("workspace.name", equalTo("MdkWorkspace_11"),
                        "workspace.id", matchesRegex("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));
    }

    @Test(enabled = false)
    public void validate_delete_request(){
        String workspaceId = "a60c3880-4c28-4389-9064-c35f2faef0a3";
        given()
                .pathParam("workspaceId", workspaceId)
        .when()
                .delete("/workspaces/{workspaceId}")
        .then()
                .assertThat()
                .body("workspace.id", matchesRegex("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));
    }
}
