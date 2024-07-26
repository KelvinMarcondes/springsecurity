package com.marcondes.springsecurity.controllers.dto;

public record LoginResponse(String AcessToken, Long expiresIn) {
}
