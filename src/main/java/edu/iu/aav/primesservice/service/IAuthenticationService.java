package edu.iu.aav.primesservice.service;

import java.io.IOException;

import edu.iu.aav.primesservice.model.Customer;

public interface IAuthenticationService {

    boolean register(Customer customer) throws IOException;

    boolean login(String username, String password) throws IOException;

}