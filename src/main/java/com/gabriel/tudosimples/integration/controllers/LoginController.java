package com.gabriel.tudosimples.integration.controllers;

import com.gabriel.tudosimples.configs.security.JwtUtil;
import com.gabriel.tudosimples.models.AuthRequest;
import com.gabriel.tudosimples.models.Usuario;
import com.gabriel.tudosimples.usecases.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {


    private final AuthenticationManager authenticationManager;
    private final UserRepository usuarioRepository;
    private final JwtUtil jwtService;

    @PostMapping
    public String login(@RequestBody AuthRequest request){
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        this.authenticationManager.authenticate(authentication);
        Usuario usuario = this.usuarioRepository.findByUsername(request.username()).orElseThrow();
        return this.jwtService.createToken(usuario);
    }
}
