package br.ce.yuri.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void deveRetornarTarefas() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void test() {
		RestAssured.given()
	
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
		;
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{\"task\": \"Teste via API\", \"dueDate\": \"2030-01-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void nãoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{\"task\": \"Teste via API\", \"dueDate\": \"2010-01-30\"}")
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


