package com.security.TodoList.Service;

import com.security.TodoList.Entity.User;
import com.security.TodoList.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationToken(String token, User user);
    String validateRegistrationToken(String token);
}
