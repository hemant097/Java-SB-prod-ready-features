package com.example.week4.postapplication.Controller;


import com.example.week4.postapplication.Clients.EmployeeClient;
import com.example.week4.postapplication.DTO.EmployeeDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/empcl")
public class EmployeeClientController {

    private final EmployeeClient employeeClient;

    @GetMapping
    List<EmployeeDTO> getEmployees(){

        return employeeClient.getAllEmployees();
    }

    @GetMapping(path = "/{empid}")
    EmployeeDTO getSingleEmployeeById(@PathVariable(name = "empid") Long empId){
        return employeeClient.getEmployeeById(empId);
    }

    @PostMapping()
    EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO employeeDTO){
        return employeeClient.createNewEmployee(employeeDTO);
    }

    @DeleteMapping(path = "/{empid}")
    String deleteEmployeeById(@PathVariable(name = "empid") Long empId){
        Boolean b = employeeClient.deleteEmployeeById(empId);
        if(b)
            return "successfully deleted";
        else
            return "unable to delete";

    }

    @PutMapping(path = "/{empid}")
    EmployeeDTO updateEmployeeById(@RequestBody  EmployeeDTO updatedEmployee,
                                   @PathVariable(name = "empid") Long empId){
        return employeeClient.updateEmployeeUsingId(empId,updatedEmployee);
    }

}
