package com.security.TodoList.Event;

import com.security.TodoList.Entity.uUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private uUser user;
    private String applicationUrl;

    public RegistrationCompleteEvent(uUser user, String applicationUrl) {
        super ( user );
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
