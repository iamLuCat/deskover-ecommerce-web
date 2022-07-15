package com.deskover.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminCreateDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8424932481273998193L;
	private String username;
    private String fullname;
    private String password;
    private String avatar;
}
