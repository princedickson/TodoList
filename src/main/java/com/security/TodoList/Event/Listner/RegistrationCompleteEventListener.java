package com.security.TodoList.Event.Listner;

import com.security.TodoList.Entity.uUser;
import com.security.TodoList.Event.RegistrationCompleteEvent;
import com.security.TodoList.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import java.util.UUID;
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create verification token for user
            uUser user = event.getUser ();
            String token = UUID.randomUUID ().toString ();
            userService.saveVerificationToken(token, user);

       
    }
}
