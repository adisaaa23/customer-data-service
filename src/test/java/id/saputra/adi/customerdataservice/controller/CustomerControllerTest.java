package id.saputra.adi.customerdataservice.controller;

import id.saputra.adi.customerdataservice.domain.dao.CustomerDao;
import id.saputra.adi.customerdataservice.exception.ApplicationException;
import id.saputra.adi.customerdataservice.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllExpectSuccess() throws Exception {
        when(customerService.showAll()).thenReturn(CustomerDao.builder().build());
        this.mockMvc.perform(get("/customers")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getAllExpectException() throws Exception {
        when(customerService.showAll()).thenThrow(ApplicationException.class);
        this.mockMvc.perform(get("/customers")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void getDetailExpectSuccess() throws Exception {
        when(customerService.showDetail("adisapu")).thenReturn(CustomerDao.builder().build());
        this.mockMvc.perform(get("/customers/detail/{username}", "adisapu")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getDetailExpectException() throws Exception {
        when(customerService.showDetail("adisapu")).thenThrow(ApplicationException.class);
        this.mockMvc.perform(get("/customers/detail/{username}", "adisapu")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void createExpectSuccess() throws Exception {
        when(customerService.create(any())).thenReturn(CustomerDao.builder().build());
        this.mockMvc.perform(post("/customers/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createExpectException() throws Exception {
        when(customerService.create(any())).thenThrow(ApplicationException.class);
        this.mockMvc.perform(post("/customers/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void updateExpectSuccess() throws Exception {
        when(customerService.update(any())).thenReturn(CustomerDao.builder().build());
        this.mockMvc.perform(put("/customers/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updateExpectException() throws Exception {
        when(customerService.update(any())).thenThrow(ApplicationException.class);
        this.mockMvc.perform(put("/customers/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

}
