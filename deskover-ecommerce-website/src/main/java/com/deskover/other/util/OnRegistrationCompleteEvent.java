package com.deskover.other.util;

import java.util.Locale;
import org.springframework.context.ApplicationEvent;
import com.deskover.model.entity.database.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private String appUrl;
    private Locale locale;
    private Users user;

    public OnRegistrationCompleteEvent(
      Users user, Locale locale, String appUrl) {
        super(user);
        
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
