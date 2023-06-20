package pl.edu.wat.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.backend.dtos.JwtAccountDto;
import pl.edu.wat.backend.entities.AccountEntity;
import pl.edu.wat.backend.exceptions.UnauthorizedException;
import pl.edu.wat.backend.security.JwtTokenUtil;
import pl.edu.wat.backend.security.JwtUser;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login (@RequestBody AccountEntity accountEntity, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(accountEntity.getEmail(), accountEntity.getPassword()));
            final JwtUser userDetails = (JwtUser)authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(userDetails);
            response.setHeader("Token", token);
            return new ResponseEntity<>(new JwtAccountDto(userDetails.getAccountEntity(), token), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new UnauthorizedException(), HttpStatus.UNAUTHORIZED);
        }
    }
}
