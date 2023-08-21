package recipes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.model.user.UserRegisterRequest;
import recipes.service.UserService;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request) {
        log.info("CHECK USER : {}", request);
        return userService.register(request);
    }
}
