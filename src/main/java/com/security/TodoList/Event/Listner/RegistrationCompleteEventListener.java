package com.security.TodoList.Event.Listner;

import com.security.TodoList.Entity.User;
import com.security.TodoList.Event.RegistrationCompleteEvent;
import com.security.TodoList.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
        @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create verification token for user
            User user = event.getUser ();
            String token = UUID.randomUUID ().toString ();
            userService.saveVerificationToken(token, user);

        // send mail to user
        String url = event.getApplicationUrl ()
                + "/verifyRegistration?token="
                + token;
        //-----send verification email------\\
        log.info ( "Click the link to verify your account: {}",url );
    }
}
