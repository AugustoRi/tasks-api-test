package br.ce.wcaquino.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class APITest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured
                .given()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(200)
        ;
    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        RestAssured
                .given()
                    .body("{\"task\": \"Teste via API\", \"dueDate\": \"2024-12-15\"}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(201)
        ;
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        RestAssured
                .given()
                    .body("{\"task\": \"Teste via API\", \"dueDate\": \"2020-12-15\"}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .statusCode(400)
                .body("message", is("Due date must not be in past"))
        ;
    }
}
