package com.ecommerce.controller;

import com.ecommerce.dto.AuthRequestDTO;
import com.ecommerce.dto.AuthResponseDTO;
import com.ecommerce.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return ResponseEntity.ok(new AuthResponseDTO(jwtUtils.generateToken(authentication)));
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> perfil(Authentication authentication) {
        String username = authentication != null ? authentication.getName() : "Anonimo";
        return ResponseEntity.ok(java.util.Map.of(
                "usuario", username,
                "estudiante", "Anderson Fabrizzio Larios Tito",
                "codigo", "n003849813",
                "estado", "ACTIVO"
        ));
    }
}

