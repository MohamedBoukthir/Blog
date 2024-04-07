package com.mohamed.DTO;

public record UserDto(
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        int age

) {
}
