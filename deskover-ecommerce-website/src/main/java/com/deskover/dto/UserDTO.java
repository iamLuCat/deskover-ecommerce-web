package com.deskover.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class UserDTO implements Serializable {
    private final Long id;
    private final String username;
    private final String password;
    private final String fullname;
    private final String address;
    private final String phone;
    private final String photo;
    private final Instant createdDate;
    private final Instant updateDate;
    private final Boolean enabled;
}
