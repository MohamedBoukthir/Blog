package com.mohamed.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserInfo {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String picture;

}
