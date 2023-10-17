package com.pedroresende.rateiofacil.controllers.dtos;

import com.pedroresende.rateiofacil.models.entities.User;

public record UpdateUserDto(String name, String username, String email, String role) {
    public User toEntity() {
        return new User(null, name, email, username, null,role, null );
    }
}
