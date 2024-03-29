package com.example.clientservice.controller;

import com.example.clientservice.model.Client;
import com.example.clientservice.service.ClientServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientServices clientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchClientById(@PathVariable String id){
        return clientService.fetchClientById(id);
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client){
        return clientService.createClient(client);
    }

    @GetMapping()
    public ResponseEntity<?> fetchClients(){
        return clientService.fetchClients();
    }

}
