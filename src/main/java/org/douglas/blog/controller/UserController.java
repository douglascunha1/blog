package org.douglas.blog.controller;

import jakarta.validation.Valid;
import org.douglas.blog.dto.UserRequestDTO;
import org.douglas.blog.dto.UserResponseDTO;
import org.douglas.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserResponseDTO> findUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<UserResponseDTO> findUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable("id") Long id, @RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
