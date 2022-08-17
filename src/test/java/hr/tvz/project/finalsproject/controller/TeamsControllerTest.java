package hr.tvz.project.finalsproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.project.finalsproject.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TeamsControllerTest {

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

    Team teamSave = Team.builder()
            .id(404L)
            .name("Test team name")
            .description("Test team description")
            .membersList(List.of())
            .build();
    Team teamSaveInvalid = Team.builder()
            .id(404L)
            .name(null)
            .description("Test team description")
            .membersList(List.of())
            .build();

    Team teamUpdate = Team.builder()
            .id(1L)
            .name("Test team name")
            .description("Test team description")
            .membersList(List.of())
            .build();

    @Test
    void findAllTeams() throws Exception {
        this.mockMvc.perform(
                get("/team")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findTeamByName() throws Exception {
        this.mockMvc.perform(
                get("/team")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("name", "backend")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    void findTeamByUserId() throws Exception {
        this.mockMvc.perform(
                get("/team")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("user_id", "1")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findTeamById() throws Exception {
        this.mockMvc.perform(
                get("/team/1")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findTeamByIdInvalid() throws Exception {
        this.mockMvc.perform(
                get("/team/404")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isNotFound());}

    @Test
    @Transactional
    void save() throws Exception {
        this.mockMvc.perform(
                post("/team/addTeam")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("member_list", new String[]{"1"})
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(teamSave))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(teamSave.getName()))
                .andExpect(jsonPath("$.description").value(teamSave.getDescription()));
    }

    @Test
    @Transactional
    void saveInvalid() throws Exception {
        this.mockMvc.perform(
                post("/team/addTeam")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("member_list", new String[]{"1"})
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(teamSaveInvalid))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Transactional
    void update() throws Exception {
        this.mockMvc.perform(
                put("/team/1")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(teamUpdate))
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
                    .content(objectMapper.writeValueAsString(teamUpdate))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateUserTeamsAdd() throws Exception {
        this.mockMvc.perform(
                patch("/team")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("add_id", "1")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("1")
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void updateUserTeamsAddInvalid() throws Exception {
        this.mockMvc.perform(
                patch("/team")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("add_id", "404")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("404")
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateUserTeamsRemove() throws Exception {
        this.mockMvc.perform(
                patch("/team")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("remove_id", "1")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("1")
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void updateUserTeamsRemoveInvalid() throws Exception {
        this.mockMvc.perform(
                patch("/team")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("remove_id", "404")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("404")
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteById() throws Exception {
        this.mockMvc.perform(
                delete("/team/1")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}