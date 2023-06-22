package com.example.configuration.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@RequiredArgsConstructor

public class AuthenticationResponse {

    private String token;



    //DTO or ViewModel
    //look table
    @JsonProperty("access_token")
    private String userAccessToken;
    @JsonProperty("refresh_token")
    private String userRefreshToken;
}
