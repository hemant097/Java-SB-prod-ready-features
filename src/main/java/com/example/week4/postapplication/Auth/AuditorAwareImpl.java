package com.example.week4.postapplication.Auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {

        //get security context
        //get authentication
        //get principal
        //get username, these steps will be added later
        return Optional.of("Hemant Sharma");
    }
}
