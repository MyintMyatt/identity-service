# Identity Service

 The purpose of this service is for **user management, authentication and authorization process.**
### Server port: ``8080``
 
 ## Tech Stack

<div class="parent-div" style="display: flex; gap: 150px;">
<div class="left-div">
    <div style="display: flex; gap: 10px;">
    <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/java.png" width="50" height="50"/>
        <h4>Java 21</h4>
    </div>
    <div style="display: flex; gap: 10px;">
    <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/spring_boot.png" width="50" height="50"/>
        <h4>Spring Boot 4.0.0</h4>
    </div>
    <div style="display: flex; gap: 10px;">
    <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/hibernate.png" width="50" height="50"/>
        <h4>Hibernate 7.1.8 Final</h4>
    </div>
    <div style="display: flex; gap: 10px;">
    <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/lombok.png" width="50" height="50"/>
        <h4>Lombok 1.8.42</h4>
    </div>
    <div style="display: flex; gap: 10px;">
    <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/mapstruct.png" width="50" height="50"/>
        <h4>Mapstruct 1.6.3</h4>
    </div>
</div>
    <div class="right-div">
        <div style="display: flex; gap: 10px;">
        <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/jetty.png" width="50" height="50"/>
            <h4>Jetty 4.0.0</h4>
        </div>
        <div style="display: flex; gap: 10px;">
            <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/kafka.png" width="50" height="50"/>
                <h4>Kafka 4.1.1</h4>
        </div>
        <div style="display: flex; gap: 10px;">
            <img src="https://raw.githubusercontent.com/marwin1991/profile-technology-icons/refs/heads/main/icons/grpc.png" width="50" height="50"/>
                <h4>Grpc 1.0.0</h4>
        </div>
    </div>
</div>


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

