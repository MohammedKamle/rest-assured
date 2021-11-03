package com.browserstack;

import java.io.File;

import static io.restassured.RestAssured.given;

public class uploadBrowserStackApp {

    @org.testng.annotations.Test
    public void uploadAppToBrowserStack(){
        given()
                .baseUri("https://api-cloud.browserstack.com")
                .auth()
                .preemptive()
                .basic("mohammedk1", "spBCpUJaVTnvxxssFtEJ")
                .multiPart("file", new File("/Users/mohammed/Downloads/presentation-edCastApp-release.apk"))
        .when()
                .post("/app-automate/upload")
        .then()
                .log().all();

    }

    @org.testng.annotations.Test
    public void uploadAppToBrowserStackUsingCustomId(){
        given()
                .baseUri("https://api-cloud.browserstack.com")
                .auth()
                .preemptive()
                .basic("mohammedk1", "spBCpUJaVTnvxxssFtEJ")
                .multiPart("file", new File("/Users/mohammed/Downloads/presentation-edCastApp-release.apk"))
                .multiPart("custom_id", "EdCastApp")
       .when()
                .post("/app-automate/upload")
       .then()
                .log().all();
    }
}
