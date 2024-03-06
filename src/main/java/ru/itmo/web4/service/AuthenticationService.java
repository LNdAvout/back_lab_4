package ru.itmo.web4.service;

import ru.itmo.web4.controller.AuthenticationRequest;
import ru.itmo.web4.controller.AuthenticationResponse;
import ru.itmo.web4.controller.RegisterRequest;
import ru.itmo.web4.exception.UserExistException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request) throws UserExistException;
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
