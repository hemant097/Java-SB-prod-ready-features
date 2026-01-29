package com.example.week4.postapplication.Clients.Impl;

import com.example.week4.postapplication.Advice.APIResponse;
import com.example.week4.postapplication.Clients.EmployeeClient;
import com.example.week4.postapplication.DTO.EmployeeDTO;
import com.example.week4.postapplication.Exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);


    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.trace("trying to retrieve employees in getAllEmployees");
        try {
            log.info("Attempting to call the RestClient method in getAllEmployees");
            APIResponse<List<EmployeeDTO>> employeeDTOS = restClient
                    .get()
                    .uri("/all")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req,res)->{

                        log.error(new String(res.getBody().readAllBytes()));

                        throw new ResourceNotFound("could not find employees");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            //using body() of RestClient to get the body from the ResponseEntity which is being returned

            log.debug("successfully retrieved the employees in getAllEmployees");
            log.trace("retrieved employee list in getAllEmployees : {}",employeeDTOS.getData());

            return employeeDTOS.getData();
        }
        catch (Exception e){
            log.error("exception occurred in getAllEmployees",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long empId) {
        log.trace("trying to get employees by id in getEmployeeById with empId :{}",empId);
        try {
            ResponseEntity<APIResponse<EmployeeDTO>> employeeDTOResponseEntity = restClient
                    .get()
                    .uri("/{empId}",empId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req,res)->{

                        log.debug("4XXClientError occurred during getEmployeeById");
                        log.error(new String(res.getBody().readAllBytes()));

                        throw new ResourceNotFound("could not find employee with id "+empId);
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });

            //using toEntity of RestClient, to get the entire ResponseEntity

            return employeeDTOResponseEntity.getBody().getData();
        }
        catch (Exception e){
            log.error("exception occurred in getEmployeeById :{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        log.trace("trying to create employee in createNewEmployee :{}",employeeDTO);
        try {
          APIResponse<EmployeeDTO> employeeDTOAPIResponse = restClient
                    .post()
                    .body(employeeDTO)
                    .retrieve()
                  .onStatus(HttpStatusCode::is4xxClientError, (req,res)->{

                      log.debug("4XXClientError occurred during createNewEmployee");;
                      String errorMessage = new String(res.getBody().readAllBytes());
                      log.error(errorMessage);

                      throw new ResourceNotFound("could not create this employee due to"+errorMessage);
                  })
                    .body(new ParameterizedTypeReference<>() {
                    });

            log.trace("successfully created an employee :{}",employeeDTOAPIResponse.getData());
            return employeeDTOAPIResponse.getData();
        }
        catch (Exception e){
            log.error("exception occurred in createNewEmployee",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteEmployeeById(Long empId) {
        log.trace("trying to delete an employee in deleteEmployeeById with id:{}",empId);
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
            log.error("exception occurred in deleteEmployeeById", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO updateEmployeeUsingId(Long empId, EmployeeDTO updatedEmployee) {
        log.trace("trying to update an employee in updateEmployeeUsingId with id:{}",empId);
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
            log.error("exception occurred in updateEmployeeUsingId",e);
            throw new RuntimeException(e);
        }
    }
}
