package com.example.helloworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.helloworld.service.PlayerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/")
public class CraftController {

    private final PlayerService service;

    @Autowired
    public CraftController(PlayerService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String sendGreetings() {
        return "Hello, World!";
    }

    @GetMapping("/players")
    public ResponseEntity<?> retriveAllPlayers() {
        return new ResponseEntity<>(service.getAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/players/{playerId}")
    public ResponseEntity<?> retrivePlayer(@PathVariable String playerId) {
        return new ResponseEntity<>(service.findPlayer(playerId), HttpStatus.OK);
    }
}
