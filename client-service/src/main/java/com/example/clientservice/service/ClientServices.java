package com.example.clientservice.service;

import com.example.clientservice.dto.Cinema;
import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.model.Client;
import com.example.clientservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<?> fetchClientById(String id){
        Optional<Client> client =  clientRepository.findById(id);
        if(client.isPresent()){
            Cinema cinema = restTemplate.getForObject("http://CINEMA-SERVICE/cinema/" + client.get().getCinemaId(), Cinema.class);
            ClientResponse clientResponse = new ClientResponse(
                    client.get().getId(),
                    client.get().getName(),
                    client.get().getSurname(),
                    client.get().getAge(),
                    client.get().getGender(),
                    cinema
            );
            return new ResponseEntity<>(clientResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No client Found",HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> fetchClients(){
        List<Client> clients = clientRepository.findAll();
        if(clients.size()>0){
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No clients",HttpStatus.NOT_FOUND);
        }
    }


}
