package com.am.urlShortnerBackend.security;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
}
