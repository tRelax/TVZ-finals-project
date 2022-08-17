package hr.tvz.project.finalsproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.project.finalsproject.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    String adminToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0bWlsYWtvdmljIiwiZXhwIjoxNjYxMzM2OTIyLCJpYXQiOjE2NjA3MzIxMjIsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTiJ9.X7yWHzTd_Cc-MUSy1Y4mhiuSfYVsWL7SUj5JEqzmhNA7LkQuUHc-0GqxqRVJr3ejWIMBP3NGdlQPdMLY1ycRHQ";
    String teamModToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtemFuaW5vdmljIiwiZXhwIjoxNjYxMzQzMzA5LCJpYXQiOjE2NjA3Mzg1MDksImF1dGhvcml0aWVzIjoiUk9MRV9URUFNX01PREVSQVRPUiJ9.6nVIYD2ENQ6b5J_0JAZMylesXBPbb9U72lg0274ZQapX-Rg7fwXuhFKCby9R9Bmq8gJjq76DIphiAQcZIGftSw";
    String userToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYmFyaWMiLCJleHAiOjE2NjEzNDM4NjMsImlhdCI6MTY2MDczOTA2MywiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIifQ.07sn_rzH03Ysarqo3TqFT6FfMi5vAXoCDB3k2seE3GF7x4pQnRc4NdUSDHZOoVy5I25AWyRHnWXXC35xlAHMoQ";
    String adminUser = "tmilakovic";
    String teamModUser = "mzaninovic";
    String userUser = "dbaric";
    String pass = "password";

    Category categorySave = new Category().builder()
            .id(404L)
            .name("Test category name")
            .description("Test description")
            .ticketList(List.of())
            .build();

    Category categorySaveInvalid = new Category().builder()
            .id(404L)
            .name(null)
            .description("Test description")
            .ticketList(List.of())
            .build();

    Category categoryUpdate = new Category().builder()
            .id(1L)
            .name("Test category name")
            .description("Test description")
            .ticketList(List.of())
            .build();

    @Test
    void findAllCategories() throws Exception {
        this.mockMvc.perform
                        (get("/category")
                            .with(user(adminUser).password(pass).roles("ADMIN"))
                            .with(csrf())
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findCategoryByName() throws Exception {
        this.mockMvc.perform
                        (get("/category")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .param("name", "backend")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findCategoryById() throws Exception {
        this.mockMvc.perform
                        (get("/category/1")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findCategoryByIdInvalid() throws Exception {
        this.mockMvc.perform
                        (get("/category/15")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        )
                .andExpect(status().isNotFound());
    }

    @Test
    void findCategoryByTicketId() throws Exception {
        this.mockMvc.perform
                        (get("/category")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .param("ticket_id", "1")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findCategoryByTicketIdInvalid() throws Exception {
        this.mockMvc.perform
                        (get("/category")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .param("ticket_id", "500")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                        )
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void save() throws Exception {
        this.mockMvc.perform(
                        post("/category/addCategory")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(categorySave))
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(categorySave.getName()))
                .andExpect(jsonPath("$.description").value(categorySave.getDescription()));
    }

    @Test
    @Transactional
    void saveInvalid() throws Exception {
        this.mockMvc.perform(
                        post("/category/addCategory")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(categorySaveInvalid))
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Transactional
    void update() throws Exception {
        this.mockMvc.perform(
                        put("/category/1")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(categoryUpdate))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void updateInvalid() throws Exception {
        this.mockMvc.perform(
                        put("/category/404")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(categoryUpdate))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteById() throws Exception {
        this.mockMvc.perform(
                        delete("/category/1")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}