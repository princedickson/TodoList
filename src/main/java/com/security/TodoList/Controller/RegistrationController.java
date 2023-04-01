package com.security.TodoList.Controller;


import com.security.TodoList.Entity.User;
import com.security.TodoList.Event.RegistrationCompleteEvent;
import com.security.TodoList.Model.UserModel;
import com.security.TodoList.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/register")
    public String RegisterUser(@RequestBody UserModel userModel, final HttpServletRequest request){
            User user = userService.registerUser ( userModel );
            applicationEventPublisher.publishEvent ( new RegistrationCompleteEvent (
                user,
                applicationUrl(request)
            ));


        return "success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateRegistrationToken ( token );
        if (result.equalsIgnoreCase ( "verify" )) {
            return "user verify successfully";
        }
        return "Bad User";
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName ()
                +":"
                +request.getServerPort ()
                +request.getContextPath ();
    }
}
