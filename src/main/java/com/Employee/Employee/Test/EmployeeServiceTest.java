package com.Employee.Employee.Test;

import com.Employee.Employee.Employee;
import com.Employee.Employee.EmployeeRepository;
import com.Employee.Employee.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    //Working
    @Test
    public void testGetAllEmployees() {
        Page<Employee> expectedEmployeePage = new PageImpl<>(
                List.of( new Employee("Ribal", "Baghdadi", "ribal@baghdadi.com", "Engineer"),
                        new Employee("Samer", "James", "james@samer.com", "Manager"))
        ,PageRequest.of(0,5),2);

        Mockito.when(employeeRepository.findAll(PageRequest.of(0,5))).thenReturn(expectedEmployeePage);

        Page<Employee> employeesPage = employeeService.getAllEmployees(PageRequest.of(0,5));
        List<Employee> employees = employeesPage.getContent();

        Assertions.assertEquals(2,employeesPage.getTotalElements());
        Assertions.assertEquals(2,employeesPage.getNumberOfElements());
        Assertions.assertEquals(1,employeesPage.getTotalPages());
        Assertions.assertEquals("Ribal", employees.get(0).getFirstName());
        Assertions.assertEquals("Baghdadi", employees.get(0).getLastName());
        Assertions.assertEquals("ribal@baghdadi.com", employees.get(0).getEmail());
        Assertions.assertEquals("Engineer", employees.get(0).getDesignation());

        Assertions.assertEquals("Samer", employees.get(1).getFirstName());
        Assertions.assertEquals("James", employees.get(1).getLastName());
        Assertions.assertEquals("james@samer.com", employees.get(1).getEmail());
        Assertions.assertEquals("Manager", employees.get(1).getDesignation());
    }

    //Working
    @Test
    public void testGetEmployeeByID() {
        Employee employee = new Employee("Ribal", "Baghdadi", "Ribal@baghdadi.com", "Engineer");
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> actualEmployee = employeeService.getEmployeeById(1L);

        Assertions.assertTrue(actualEmployee.isPresent());
        Assertions.assertEquals(actualEmployee.get(), employee);
    }

    //Working
    @Test
    public void testCreateEmployee() {

        Employee newEmployee = new Employee("Marcus", "chami", "marcus@chami.com", "Developer");

        Mockito.when(employeeRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(new Employee("Marcus", "chami", "marcus@chami.com", "Developer"));


        Employee savedEmployee = employeeService.createEmployee(newEmployee);


        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals("Marcus", savedEmployee.getFirstName());
        Assertions.assertEquals("chami", savedEmployee.getLastName());
        Assertions.assertEquals("marcus@chami.com", savedEmployee.getEmail());
        Assertions.assertEquals("Developer", savedEmployee.getDesignation());
    }

    //Working
    @Test
    public void testUpdateEmployee() {

        Employee oldEmployee = new Employee("Ribal", "Baghdadi", "ribal@example.com", "Senior Engineer");
        Employee updatedEmployee = new Employee("Samer", "James", "samer@james.com", "Senior Engineer");
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(oldEmployee));

        Mockito.when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);


        Employee savedEmployee = employeeService.updateEmployee(1L, updatedEmployee);


        Assertions.assertNotNull(savedEmployee);
        Assertions.assertEquals("Samer", savedEmployee.getFirstName());
        Assertions.assertEquals("James", savedEmployee.getLastName());
        Assertions.assertEquals("samer@james.com", savedEmployee.getEmail());
        Assertions.assertEquals("Senior Engineer", savedEmployee.getDesignation());
    }

    //Working
    @Test
    public void testDeleteEmployee() {

        Mockito.doNothing().when(employeeRepository).deleteById(1L);


        employeeService.deleteEmployee(1L);


        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(1L);
    }


}

