# Identity Service

 The purpose of this service is for **user management, authentication and authorization process.**
## Server port
- **Identity-Service** : ``8080``
- **Grpc-Mail-Service**: ``9090``
- **Redis**: ``6379``
- **PostgresSql**: ``5432``

 
 ## Tech Stack
 
| Language, Framework $ Libraries| Server & Commnuication|
|------|-------|
| <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/java.png" width="50"/> Java 21 | <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/jetty.png" width="50"/> Jetty 4.0.0 |
| <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/spring_boot.png" width="50"/> Spring Boot 4.0.0 | <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/kafka.png" width="50"/> Kafka 4.1.1 |
| <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/hibernate.png" width="50"/> Hibernate 7.1.8 Final | <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/grpc.png" width="50"/> Grpc 1.0.0 |
| <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/lombok.png" width="50"/> Lombok 1.8.42 | |
| <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/mapstruct.png" width="50"/> Mapstruct 1.6.3 | |


<hr/>

### Auth Provider
- Google
- Microsoft

### Get manual google authentication code (exchange code)
```declarative
  https://accounts.google.com/o/oauth2/v2/auth
    ?client_id=${AppConfig.GOOGLE_CLIENT_ID} 
    &redirect_uri= http://localhost:8080/login/oauth2/code/google
    &response_type=code
    &scope=email%20profile 
    &access_type=offline
    &prompt=consent
```

