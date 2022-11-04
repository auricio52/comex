package br.com.alura.comex.controllers;

import br.com.alura.comex.controllers.dtos.LoginDtoInput;
import br.com.alura.comex.controllers.dtos.TokenDtoOutput;
import br.com.alura.comex.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticarController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticarController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDtoOutput> autenticar(@RequestBody @Valid LoginDtoInput login) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login
                .getSenha());

        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            String token = tokenService.gerarToken(authentication);
            TokenDtoOutput tokenDto = new TokenDtoOutput();
            tokenDto.setToken(token);
            tokenDto.setType("Bearer");
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
