package com.example.clientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<?> createClient(Client client){
        try{
            return new ResponseEntity<Client>(clientRepository.save(client), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> fetchClients(){
        List<Client> clients = clientRepository.findAll();
        if(clients.size() > 0){
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No clients",HttpStatus.NOT_FOUND);
        }
    }


}
