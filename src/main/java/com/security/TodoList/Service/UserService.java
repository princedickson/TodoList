package com.security.TodoList.Service;

import com.security.TodoList.Entity.User;
import com.security.TodoList.Entity.VerificationToken;
import com.security.TodoList.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationToken(String token, User user);
    String validateRegistrationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPasswoed(User user, String oldPassword);
}
