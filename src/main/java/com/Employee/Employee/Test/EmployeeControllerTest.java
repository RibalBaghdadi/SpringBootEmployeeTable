package com.Employee.Employee.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.Employee.Employee.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    //Working
    @Test
    public void testGetAllEmployees() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    //Working
    @Test
    public void testGetEmployeeById() throws Exception {

        Employee employee = new Employee("Ribal", "Baghdadi", "ribal@baghdadi.com", "Engineer");
        ResultActions createResult = mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        String responseContent = createResult.andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(responseContent).get("id").asLong();


        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    //Working
    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = new Employee("Ribal", "Baghdadi", "ribal@baghdadi.com", "Engineer");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    //Working
    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employee = new Employee("Ribal", "Baghdadi", "ribal@baghdadi.com", "Engineer");
        ResultActions createResult = mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }


    //Working
    @Test
    public void testDeleteEmployee() throws Exception {

        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}


