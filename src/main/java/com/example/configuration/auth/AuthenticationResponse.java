package com.example.configuration.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class AuthenticationResponse {

    //DTO or ViewModel
    //look table
    @JsonProperty("access_token")
    private String userAccessToken;
    @JsonProperty("refresh_token")
    private String userRefreshToken;
}
