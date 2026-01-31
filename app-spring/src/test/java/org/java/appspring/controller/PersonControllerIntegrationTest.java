package org.java.appspring.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.java.appspring.AbstractIntegrationTest;
import org.java.appspring.Person;
import org.java.appspring.PersonRepository;
import org.java.appspring.configs.TestConfigs;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

// Define que os testes seguirão uma ordem específica ditada pela anotação @Order
// A gente aproveita o que foi feito no teste anterior, no proximo teste
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// webEnvironment = DEFINED_PORT faz com que o servidor rode na porta real (ex: 8080) em vez de uma aleatória.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// O Spring descarta o contexto ao final da classe
// Os dados não vazam pra outros testes
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) // TOMAR CUIDADO COM ISSO AQUI
public class PersonControllerIntegrationTest extends AbstractIntegrationTest {
    @LocalServerPort
    private int port;

    // Objeto do RestAssured para configurar a requisição (URL, Headers, Logs)
    private static RequestSpecification specification;
    // Objeto da biblioteca Jackson usado para converter JSON em Objeto Java e vice-versa
    private static ObjectMapper mapper;
    // Objeto que representa a entidade que será enviada/recebida nos testes
    private static Person person;

    @Autowired
    private static PersonRepository personRepository;

    // Este bloco roda uma única vez antes de todos os testes da classe
    @BeforeAll()
    static void setup() {
        // Inicializa o objeto Person que será usado como "mock" ou payload nos testes
        person = new Person();
        person.setName("John");
        person.setEmail("john@email.com");

        mapper = new ObjectMapper();
        // Configura o Jackson para não dar erro se o JSON de resposta tiver campos que não existem na classe Java
        // Isso evita que o teste quebre por bobagens não relacionadas ao teste em si
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    // Altere para @BeforeEach para garantir que a porta injetada já esteja disponível
    @BeforeEach
    void setUp() {
        // Constrói a configuração padrão das requisições HTTP
        specification = new RequestSpecBuilder()
                .setBasePath("/person") // Define o caminho base da API (ex: /api/v1)
                .setPort(port) // Define a porta onde a API está rodando
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) // Loga tudo que você ENVIA no console
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) // Loga tudo que você RECEBE no console
                .build(); // Finaliza a construção do objeto de especificação
    }

    @Test()
    @Order(1)
    @DisplayName("createOnePerson")
    void createOnePerson() throws Exception{
        var content = RestAssured.given(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        Person createdPerson = mapper.readValue(content, Person.class);
        person = createdPerson;
        Assertions.assertNotNull(createdPerson.getId());
        Assertions.assertNotNull(createdPerson.getEmail());
        Assertions.assertTrue(createdPerson.getId() > 0);
        Assertions.assertEquals("John", createdPerson.getName());
    }

    @Test()
    @Order(2)
    @DisplayName("updatePerson")
    void updatePerson() throws Exception{
        person.setName("update name");
        person.setEmail("update email");
        var content = RestAssured.given(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person updatedPerson = mapper.readValue(content, Person.class);
        person = updatedPerson;
        Assertions.assertNotNull(updatedPerson.getId());
        Assertions.assertNotNull(updatedPerson.getEmail());
        Assertions.assertTrue(updatedPerson.getId() > 0);
        Assertions.assertEquals("update name", updatedPerson.getName());
        Assertions.assertEquals("update email", updatedPerson.getEmail());
    }

    @Test()
    @Order(3)
    @DisplayName("findById")
    void findById() throws Exception{
        var content = RestAssured.given(specification)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        Person foundPerson = mapper.readValue(content, Person.class);
        Assertions.assertNotNull(foundPerson.getId());
        Assertions.assertNotNull(foundPerson.getEmail());
        Assertions.assertTrue(foundPerson.getId() > 0);
        Assertions.assertEquals("update name", foundPerson.getName());
        Assertions.assertEquals("update email", foundPerson.getEmail());
    }

    @Test()
    @Order(4)
    @DisplayName("findAll")
    void findAll() throws Exception{
        Person newPerson = new Person();
        newPerson.setName("Other");
        newPerson.setEmail("other@email.com");
        var secondPerson = RestAssured.given(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(newPerson)
                .when()
                .post()
                .then()
                .statusCode(200);

        var content = RestAssured.given(specification)
                .when()
                .get("/all")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<Person> people = Arrays.asList(mapper.readValue(content, Person[].class));

        Person foundPersonOne = people.get(0);
        Person foundPersonTwo = people.get(1);

        Assertions.assertNotNull(foundPersonOne.getId());
        Assertions.assertNotNull(foundPersonOne.getEmail());
        Assertions.assertTrue(foundPersonOne.getId() > 0);
        Assertions.assertEquals("update name", foundPersonOne.getName());
        Assertions.assertEquals("update email", foundPersonOne.getEmail());

        Assertions.assertNotNull(foundPersonTwo.getId());
        Assertions.assertNotNull(foundPersonTwo.getEmail());
        Assertions.assertTrue(foundPersonTwo.getId() > 0);
        Assertions.assertEquals("Other", foundPersonTwo.getName());
        Assertions.assertEquals("other@email.com", foundPersonTwo.getEmail());
    }

    @Test()
    @Order(5)
    @DisplayName("delete")
    void delete() throws Exception{
        RestAssured.given(specification)
            .pathParam("id", person.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);
}
}
