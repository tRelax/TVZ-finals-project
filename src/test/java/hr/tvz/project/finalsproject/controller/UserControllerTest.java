package hr.tvz.project.finalsproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.project.finalsproject.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

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

    User userSave = User.builder()
            .id(404L)
            .username("TestUsername")
            .name("TestName")
            .surname("TestSurname")
            .build();

    User userSaveInvalid = User.builder()
            .id(404L)
            .username(null)
            .name("TestName")
            .surname("TestSurname")
            .build();

    User userUpdate = User.builder()
            .id(1L)
            .username("TestUsername")
            .name("TestName")
            .surname("TestSurname")
            .build();

    @Test
    void findAllUser() throws Exception {
        this.mockMvc.perform(
                get("/user/getUsers")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findUsersByTeamId() throws Exception {
        this.mockMvc.perform(
                get("/user")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("team_id", "1")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findUserById() throws Exception {
        this.mockMvc.perform(
                get("/user/1")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findUserByIdInvalid() throws Exception {
        this.mockMvc.perform(
                get("/user/404")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAssigneeByTicketId() throws Exception {
        this.mockMvc.perform(
                get("/user")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("assignee_ticket_id", "1")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findAssigneeByTicketIdInvalid() throws Exception {
        this.mockMvc.perform(
                get("/user")
                        .with(user(adminUser).password(pass).roles("ADMIN"))
                        .param("assignee_ticket_id", "404")
                        .with(csrf())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void findTesterByTicketId() throws Exception {
        this.mockMvc.perform(
                        get("/user")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .param("tester_ticket_id", "1")
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findTesterByTicketIdInvalid() throws Exception {
        this.mockMvc.perform(
                get("/user")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("tester_ticket_id", "404")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void save() throws Exception {
        this.mockMvc.perform(
                post("/user/addUser")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("password", "password123")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(userSave))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(userSave.getName()));

    }

    @Test
    @Transactional
    void saveInvalid() throws Exception {
        this.mockMvc.perform(
                post("/user/addUser")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("password", "password123")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(userSaveInvalid))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isInternalServerError());

    }

    @Test
    @Transactional
    void update() throws Exception {
        this.mockMvc.perform(
                        put("/user/1")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userUpdate))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void updateInvalid() throws Exception {
        this.mockMvc.perform(
                        put("/user/404")
                                .with(user(adminUser).password(pass).roles("ADMIN"))
                                .with(csrf())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userUpdate))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteById() throws Exception {
        this.mockMvc.perform(
                delete("/user/1")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}