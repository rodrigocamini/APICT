package br.com.ciet.api.book;

import br.com.ciet.api.config.BaseAPITests;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import br.com.ciet.api.resource.*;
import sun.security.util.IOUtils;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.*;


public class BookApi extends BaseAPITests {

    private static final String SCHEMA_BOOK = "content-vertical-schema.json";
    private static final String ENDPOINT = "/api/book";
    FileInputStream fileInputStream = new FileInputStream(new File(NewBook.json));

    @Test
    public void sendRequestBookSuccess() {
        given()
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(SCHEMA_BOOK));
    }

    @Test
    public void validateBodyDescriptionAll() {
        given()
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .body("description", everyItem(hasItem("Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\\r\\n")));
    }

    @Test
    public void postNewBook() {
        given()
                .body(IOUtils.ToString(fileInputStream,"UTF-8"))
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(200);
    }
}
