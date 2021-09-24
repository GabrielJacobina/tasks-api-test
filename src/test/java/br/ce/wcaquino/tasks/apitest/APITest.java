package br.ce.wcaquino.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setuo() {
        RestAssured.baseURI = "http://192.168.0.107:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
                .when()
                .get("/todo")
                .then()
                .statusCode(200)
        ;
    }

    @Test
    public void deveAdicionarTarefaComSuceso() {
        RestAssured.given()
                .body("{ \"task\": \"Teste via API\", \"dueDate\": \"2021-12-30\" }\n")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(201)
        ;
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        RestAssured.given()
                .body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-12-30\" }\n")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }
}

