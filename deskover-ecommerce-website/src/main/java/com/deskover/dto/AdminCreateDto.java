package com.deskover.dto;

import com.deskover.entity.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateDto implements Serializable {
    private String username;
    private String fullname;
    @NotBlank(message = "Không để trống password")
    private String password;
    private String avatar;
}
