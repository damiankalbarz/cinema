package com.example.clientservice.model;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "clients")
public class Client {
    @Id
    private String id;

    @NotBlank(message = "Imię nie może być puste")
    private String name;

    @NotBlank(message = "Nazwisko nie może być puste")
    private String surname;

    @Min(value = 0, message = "Wiek nie może być ujemny")
    private int age;

    @NotBlank(message = "Płeć nie może być pusta")
    private String gender;

    @NotNull(message = "Id kina nie może być puste")
    private Integer cinemaId;
}
