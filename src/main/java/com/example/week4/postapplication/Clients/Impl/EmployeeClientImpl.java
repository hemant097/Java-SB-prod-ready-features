package com.example.week4.postapplication.Clients.Impl;

import com.example.week4.postapplication.Advice.APIResponse;
import com.example.week4.postapplication.Clients.EmployeeClient;
import com.example.week4.postapplication.DTO.EmployeeDTO;
import com.example.week4.postapplication.Exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;


    @Override
    public List<EmployeeDTO> getAllEmployees() {

        try {
            APIResponse<List<EmployeeDTO>> employeeDTOS = restClient
                    .get()
                    .uri("/all")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            //using body() of RestClient to get the body from the ResponseEntity which is being returned

            return employeeDTOS.getData();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long empId) {
        try {
            ResponseEntity<APIResponse<EmployeeDTO>> employeeDTOResponseEntity = restClient
                    .get()
                    .uri("/{empId}",empId)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<>() {
                    });

            //using toEntity of RestClient, to get the entire ResponseEntity

            return employeeDTOResponseEntity.getBody().getData();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        try {
          APIResponse<EmployeeDTO> employeeDTOAPIResponse = restClient
                    .post()
                    .body(employeeDTO)
                    .retrieve()
                  .onStatus(HttpStatusCode::is4xxClientError, (req,res)->{

                      throw new ResourceNotFound(new String(res.getBody().readAllBytes()));
                  })
                    .body(new ParameterizedTypeReference<>() {
                    });


            return employeeDTOAPIResponse.getData();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteEmployeeById(Long empId) {
        try {
            APIResponse<Boolean> employeeDTOAPIResponse = restClient
                    .delete()
                    .uri("/{empId}",empId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            //using toEntity of RestClient, to get the entire ResponseEntity

            return employeeDTOAPIResponse.getData();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO updateEmployeeUsingId(Long empId, EmployeeDTO updatedEmployee) {
        try {
            APIResponse<EmployeeDTO> employeeDTOAPIResponse = restClient
                    .put()
                    .uri("/{empId}",empId)
                    .body(updatedEmployee)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            //using toEntity of RestClient, to get the entire ResponseEntity

            return employeeDTOAPIResponse.getData();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
