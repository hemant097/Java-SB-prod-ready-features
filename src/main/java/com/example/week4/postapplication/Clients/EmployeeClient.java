package com.example.week4.postapplication.Clients;

import com.example.week4.postapplication.DTO.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long empId);

    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);

    Boolean deleteEmployeeById(Long empId);

    EmployeeDTO updateEmployeeUsingId(Long empId, EmployeeDTO updatedEmployee);
}
