package com.tp39;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// UserDTO.java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class userDTO {
    private String name;
    private String role;
    private Profile profile;
}


