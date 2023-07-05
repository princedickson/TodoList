package com.security.TodoList.Controller;


import com.security.TodoList.Entity.User;
import com.security.TodoList.Entity.VerificationToken;
import com.security.TodoList.Event.RegistrationCompleteEvent;
import com.security.TodoList.Model.PasswordModel;
import com.security.TodoList.Model.UserModel;
import com.security.TodoList.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class  RegistrationController {
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
        if (!result.equalsIgnoreCase ( "valid" )) {
            return "user verify successfully";
        }
        return "Bad User";
    }

    @GetMapping("/resendToken")
    public String resendVerificationToken(@RequestParam ("token") String oldToken,
                                          HttpServletRequest httpServletRequest)
    {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(httpServletRequest), verificationToken);
        return "verification link sent";
    }

    private void resendVerificationTokenMail(User user, String applicationURL,
                                             VerificationToken verificationToken) {

        String url = applicationURL
                + "/verifyRegistration?token="
                + verificationToken.getToken();
        //-----send verification email------\\
        log.info ( "Click the link to verify your account: {}",url );
    }

    @PostMapping("/restPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel,
                                HttpServletRequest httpServletRequest){
        User user = userService.findByEmail(passwordModel.getEmail());
        String url = "";
        if(user!=null){
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(httpServletRequest), token);
        }
            return  url;
    }

    private String  passwordResetTokenMail(User user, String httpServletRequest, String token) {
        String url = httpServletRequest
                + "/savePassword?token="
                + token;
        //-----send verification email------\\
        log.info ( "Click the link to reset your password: {}",url );
        return  url;
    }
    @PostMapping("/savePassword")
    public String savePassword(@RequestParam ("token") String token,
                               @RequestBody PasswordModel passwordModel){
        String result = userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")){
            return "invalid token";
        }
             Optional<User> user = userService.getUserByPasswordToken(token);
        if(user.isPresent()){
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "password change successfully ";
        }else {
            return "invalid";
        }
    }
    @PostMapping("/changePassword")
    public String changePassword( @RequestBody PasswordModel passwordModel){
        User user = userService.findByEmail(passwordModel.getEmail());
        if(!userService.checkIfValidOldPasswoed(user, passwordModel.getOldPassword())){
            return "invalid old password";
        }
        userService.changePassword(user,passwordModel.getNewPassword());
        return "password change successfully";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName ()
                +":"
                +request.getServerPort ()
                +request.getContextPath ();
    }
}
