package com.example.clientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientServices clientService;

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client){
        return clientService.createClient(client);
    }

    @GetMapping
    public ResponseEntity<?> fetchClients(){
        return clientService.fetchClients();
    }

}
