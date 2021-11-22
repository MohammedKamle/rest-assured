package com.rest;

import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;

public class DownloadFile_14 {

    @Test
    public void download_file() throws IOException {
       byte[] bytes = given()
                .baseUri("https://github.com/appium")
                .log().all()
        .when()
                .get("/appium/raw/master/sample-code/apps/ApiDemos-debug.apk")
        .then()
                .log().all()
                .extract()
                .response().asByteArray();

        OutputStream os = new FileOutputStream(new File("ApiDemos-debug.apk"));
        os.write(bytes);
        os.close();

    }
}
