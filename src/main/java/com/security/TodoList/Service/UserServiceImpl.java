package com.security.TodoList.Service;


import com.security.TodoList.Entity.User;
import com.security.TodoList.Entity.VerificationToken;
import com.security.TodoList.Model.UserModel;
import com.security.TodoList.Repository.UserRepository;
import com.security.TodoList.Repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements  UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    public UserServiceImpl(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository){
        this.userRepository = userRepository;
        this.verificationTokenRepository =verificationTokenRepository;

    }




    @Override
    public User registerUser(UserModel userModel) {
        User user = new User ();
        user.setFirstname ( userModel.getFirstname () );
        user.setEmail ( userModel.getEmail () );
        user.setLastname ( userModel.getLastname () );
        user.setPassword (passwordEncoder.encode ( userModel.getPassword () )  );
        user.setRoles ( "user" );
        userRepository.save ( user );
        return user;
    }

    @Override
    public void saveVerificationToken(String token, User user) {
         VerificationToken verificationToken = new VerificationToken (user,token);
        verificationTokenRepository.save ( verificationToken );
    }

    @Override
    public String validateRegistrationToken(String token) {
        VerificationToken verificationToken =
                verificationTokenRepository.findByToken ( token );

        if(verificationToken == null){
            return "invalid";
        }
        //---- Get user and calculate the exipration time----\\
        User user = verificationToken.getUser ();
        Calendar calendar  = Calendar.getInstance ();

        if ((verificationToken.getExpirationTime ().getTime ()- calendar.getTime ().getTime ()) <=0){
            verificationTokenRepository.delete ( verificationToken );
            return "expired";
        }
        user.setEnable ( true );
        userRepository.save ( user );
        return "invalid";
    }
}
