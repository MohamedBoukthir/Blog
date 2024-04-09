package com.mohamed.Authentication;

import com.mohamed.DTO.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String message;
    private UserDto userDto;
    private String error;

}
