package com.deskover.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminPasswordDto implements Serializable {
    private static final long serialVersionUID = -1375560384006807504L;
    private Long id;
    private String password;
    private Timestamp modifiedAt;
}
