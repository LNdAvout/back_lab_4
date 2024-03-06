package ru.itmo.web4.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.web4.config.JwtService;
import ru.itmo.web4.controller.AuthenticationRequest;
import ru.itmo.web4.controller.AuthenticationResponse;
import ru.itmo.web4.controller.RegisterRequest;
import ru.itmo.web4.exception.UserExistException;
import ru.itmo.web4.model.UserEntity;
import ru.itmo.web4.repository.UserRepository;
import ru.itmo.web4.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) throws UserExistException{
        var user = UserEntity.builder()
                .name(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        if (repository.findByName(user.getUsername()).isPresent()){
            throw new UserExistException("Имя пользователя уже занято!");
        }
        repository.save(user);
        var jwtToken = jwtService.generateToken(user, user.getId());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByName(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user, user.getId());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
