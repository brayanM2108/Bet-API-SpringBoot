package com.melo.bets.web.controller;

import com.melo.bets.domain.dto.user.UserDto;
import com.melo.bets.domain.dto.user.UserRegisterDto;
import com.melo.bets.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity< List<UserDto>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity <UserDto> getById(@PathVariable ("id") UUID id) {
        return ResponseEntity.of(userService.getById(id));
    }


    @PostMapping
    public ResponseEntity<UserRegisterDto> save(@RequestBody UserRegisterDto user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ("id") UUID id) {
        return new ResponseEntity<>(userService.delete(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }
}