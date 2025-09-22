package br.com.chronos.controller;

import br.com.chronos.domain.user.*;
import br.com.chronos.infrastructure.security.TokenJWTDataDTO;
import br.com.chronos.repository.UserRepository;
import br.com.chronos.service.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager manager;

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserDataDTO data) {
        var authencationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authencation = manager.authenticate(authencationToken);

        var tokenJWT = tokenService.generateToken((User) authencation.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDataDTO(tokenJWT));

    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRegisterDataDTO data, UriComponentsBuilder uriBuilder) {
        if (repository.findByEmail(data.email()).isPresent()) {
            return ResponseEntity.badRequest().body("E-mail already registered");
        }

        if (!data.password().equals(data.confirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords didn't match");
        }

        var standardRole = Role.USER_ROLE;
        var encodedPassword = encoder.encode(data.password());

        User user = new User(null, data.email(), encodedPassword, standardRole);
        repository.save(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserCreatedDTO(user));

    }

}
