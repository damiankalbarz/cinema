Aplikacja stworzona w architekturze microserwisowej. Celem systemu jest zarzadzanie kinem. W aplikacji zostały przeprowadzone testy jednostkowe i integracyjne.
filmShow-service: (PostgreSQL) odpowiada za zarzadzanie seansami i rezerwacjami.
film-service: (MongoDB) odpowiada za zarzadzanie filmami
indentity-service: (MySql, JWT, Spring security) odpowiada za rejestracje i logowanie.
cinema-service: (MySql,  RabbitMQ) odpowiada za zarzadzanie kinem i salami kinowymi.
employee-service: (MySql, RabbitMQ, Kafka) odpowiada za zarzadzanie pracownikami.
