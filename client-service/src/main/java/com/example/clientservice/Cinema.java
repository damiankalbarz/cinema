package com.example.clientservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cinema {
    private Long id;
    private String cinemaName;
    private String location;
}
