package com.alkemy.java.controller;

import com.alkemy.java.dto.UserDto;
import com.alkemy.java.dto.UserLoginDto;
import com.alkemy.java.model.User;
import com.alkemy.java.security.JwtResponse;
import com.alkemy.java.security.JwtTokenService;
import com.alkemy.java.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Api(value="Auth Controller", description="Operaciones de loggin y de crear nuevo usuario")
public class AuthController {

    @Autowired
    UserService userservice;
    @Autowired
    MessageSource messageSource;

    @Autowired
    private JwtTokenService jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String OK = "Operacion realizada con exito.";
    private static final String BAD_REQEST = "Credenciales Invalidas.";
    private static final String UNAUTHORIZED = "Necesita autorizacion para realizar esta operacion..";
    private static final String NOT_FOUND = "El recurso buscado no existe o no se encuentra disponible.";
    private static final String INTERNAL_SERVER ="Campo faltante, email repetido o invalido";


    @PostMapping("auth/login")
    @ApiOperation(value = "${auth.operation.login}")
    @ApiResponses(value = {
            @ApiResponse(code = 200,  message =OK),
            @ApiResponse(code = 401, message = BAD_REQEST),
            @ApiResponse(code = 404, message = NOT_FOUND)
    }
    )

    public ResponseEntity<?> createAuthenticationToken(@RequestBody  @ApiParam(value = "${auth.body.loginuser}") UserLoginDto authenticationRequest)
            throws Exception {

        final Authentication auth = authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok(new JwtResponse(jwtTokenUtil.generateToken(auth)));
    }

    @PostMapping("/auth/register")
    @ApiOperation(value = "${auth.operation.register}",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,  message =OK),
            @ApiResponse(code = 400, message = BAD_REQEST),
            @ApiResponse(code = 404, message = NOT_FOUND),
            @ApiResponse(code = 500, message = INTERNAL_SERVER)
    }
    )
    public ResponseEntity<?> NewUser(@Valid @ApiParam(value = "${auth.body.register}") @RequestBody UserDto newUserDto) throws Exception {

        try {
            userservice.save(newUserDto);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
        UserLoginDto authenticationRequest = new UserLoginDto(newUserDto.getEmail(), newUserDto.getPassword(),
                newUserDto.getRoleId());
        return createAuthenticationToken(authenticationRequest);
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    @GetMapping("/auth/me")
    @ApiOperation(value = "${auth.operation.me}",response = Iterable.class)
    public User showUser(HttpServletRequest request){
        return userservice.showUser(request);
    }

}
