package org.java.appspring;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @Autowired MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockitoBean
    private PersonService personService;

    Person person;
    Person person2;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        person = new Person();
        person.setName("Test name");
        person.setEmail("Test email");

        person2 = new Person();
        person2.setName("Test name2");
        person2.setEmail("Test email2");
    }

    @Test
    void testInstaciation() throws Exception {
        // Se 'person' mudar lá dentro do service, o mock pode retornar uma versão desatualizada
        // Mockito.when(repository.save(any())).thenReturn(person);

        // Não importa o que o Service faça com a 'person', o mock sempre devolve a versão atual
        Mockito.when(personService.createPerson(ArgumentMatchers.any(Person.class)))
            .thenAnswer((invocation) -> invocation.getArguments()[0]
        );

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/person") // Importado estaticamente de MockMvcRequestBuilders
            .contentType(MediaType.APPLICATION_JSON) // Diz que você está enviando JSON
//            .accept(MediaType.APPLICATION_JSON)      // Diz que você espera receber JSON
            .content(objectMapper.writeValueAsString(person))); // O segredo é o .content()
//            .andExpect(MockMvcResultMatchers.status().isCreated()) // Valida se retornou 201 Created
//            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("João")); // Valida o retorno

        response
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is(person.getName())));
    }

    @Test
    void testFindAll() throws Exception {
        Mockito.when(personService.findAll()).thenReturn(List.of(person, person2));
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/person/all"));

        response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(Matchers.is(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(Matchers.is(person.getName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(Matchers.is(person2.getName())));
    }

    @Test
    void testFindByIdPositiveScenario() throws Exception {
        long personId = 1L;
        ReflectionTestUtils.setField(person, "id", personId);
        Mockito.when(personService.findById(personId)).thenReturn(person);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}", personId));
        response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is(person.getName())));
    }

    @Test
    void testFindByIdNegativeScenario() throws Exception {
        long personId = 1L;
        ReflectionTestUtils.setField(person, "id", personId);
        Mockito.when(personService.findById(personId)).thenThrow(RuntimeException.class);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}", personId));
        response.andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testUpdatePositiveScenario() throws Exception {
        long personId = 1L;
        ReflectionTestUtils.setField(person, "id", personId);
        Mockito.when(personService.findById(personId)).thenReturn(person);
        Mockito.when(personService.update(ArgumentMatchers.any(Person.class)))
            .thenAnswer((invocation) -> invocation.getArguments()[0]);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/person") // Removi o /{id}
                .content(objectMapper.writeValueAsString(person2))
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is(person2.getName())));
    }

    @Test
    void testUpdateNegativeScenario() throws Exception {
        long personId = 1L;
        ReflectionTestUtils.setField(person, "id", personId);
        Mockito.when(personService.findById(personId)).thenReturn(person);
        Mockito.when(personService.update(ArgumentMatchers.any(Person.class)))
                .thenThrow(new RuntimeException("Not Found"));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/person") // Removi o /{id}
                .content(objectMapper.writeValueAsString(person2))
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeletePositiveScenario() throws Exception {
        long personId = 1L;
        ReflectionTestUtils.setField(person, "id", personId);
        Mockito.when(personService.findById(personId)).thenReturn(person);
        Mockito.doNothing().when(personService).delete(personId);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/person/{id}", personId));
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
