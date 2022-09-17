package hr.tvz.project.finalsproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.project.finalsproject.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${adminBearerToken}")
    String adminToken;
    String teamModToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtemFuaW5vdmljIiwiZXhwIjoxNjYxMzQzMzA5LCJpYXQiOjE2NjA3Mzg1MDksImF1dGhvcml0aWVzIjoiUk9MRV9URUFNX01PREVSQVRPUiJ9.6nVIYD2ENQ6b5J_0JAZMylesXBPbb9U72lg0274ZQapX-Rg7fwXuhFKCby9R9Bmq8gJjq76DIphiAQcZIGftSw";
    String userToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYmFyaWMiLCJleHAiOjE2NjEzNDM4NjMsImlhdCI6MTY2MDczOTA2MywiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIifQ.07sn_rzH03Ysarqo3TqFT6FfMi5vAXoCDB3k2seE3GF7x4pQnRc4NdUSDHZOoVy5I25AWyRHnWXXC35xlAHMoQ";
    String adminUser = "tmilakovic";
    String teamModUser = "mzaninovic";
    String userUser = "dbaric";
    String pass = "password";
    Date date = new Date();

    Ticket ticketSave = Ticket.builder()
            .id(404L)
            .name("Test ticket name")
            .description("Test ticket description")
            .start_date(date)
            .due_date(date)
            .progress(0)
            .priority(0)
            .build();

    Ticket ticketSaveInvalid = Ticket.builder()
            .id(404L)
            .name(null)
            .description("Test ticket description")
            .start_date(date)
            .due_date(date)
            .progress(0)
            .priority(0)
            .build();

    Ticket ticketUpdate = Ticket.builder()
            .id(1L)
            .name("Test ticket name")
            .description("Test ticket description")
            .start_date(date)
            .due_date(date)
            .progress(0)
            .priority(0)
            .build();

    @Test
    void findAllTickets() throws Exception {
        this.mockMvc.perform(
            get("/tickets")
                .with(user(adminUser).password(pass).roles("ADMIN"))
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer "
                        + adminToken))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findTicketByCategoryId() throws Exception {
        this.mockMvc.perform(
                get("/tickets")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("category_id", "1")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findTicketByAssigneeId() throws Exception {
        this.mockMvc.perform(
                get("/tickets")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("assignee_id", "1")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findTicketById() throws Exception {
        this.mockMvc.perform(
                get("/tickets/1")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findTicketByIdInvalid() throws Exception {
        this.mockMvc.perform(
                get("/tickets/404")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void save() throws Exception {
        this.mockMvc.perform(
            post("/tickets/addTicket")
                .with(user(adminUser).password(pass).roles("ADMIN"))
                .param("assignee_id", "1")
                .param("tester_id", "1")
                .param("category_id", "1")
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer "
                        + adminToken)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(ticketSave))
                .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
            .andExpect(content()
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.name")
                    .value(ticketSave.getName()))
            .andExpect(jsonPath("$.description")
                    .value(ticketSave.getDescription()));
    }

    @Test
    @Transactional
    void saveInvalid() throws Exception {
        this.mockMvc.perform(
                post("/tickets/addTicket")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("assignee_id", "404")
                    .param("tester_id", "404")
                    .param("category_id", "404")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(ticketSaveInvalid))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void update() throws Exception {
        this.mockMvc.perform(
            put("/tickets/updateTicket")
                .with(user(adminUser).password(pass).roles("ADMIN"))
                .param("ticket_id", "1")
                .param("assignee_id", "1")
                .param("tester_id", "1")
                .param("category_id", "1")
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer "
                        + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(ticketUpdate))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void updateInvalid() throws Exception {
        this.mockMvc.perform(
                put("/tickets/updateTicket")
                    .with(user(adminUser).password(pass).roles("ADMIN"))
                    .param("ticket_id", "404")
                    .param("assignee_id", "404")
                    .param("tester_id", "404")
                    .param("category_id", "404")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ticketUpdate))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deleteById() throws Exception {
        this.mockMvc.perform(
            delete("/tickets/1")
                .with(user(adminUser).password(pass).roles("ADMIN"))
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer "
                        + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}