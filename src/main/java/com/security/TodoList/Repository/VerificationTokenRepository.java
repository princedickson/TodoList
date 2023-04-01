package com.security.TodoList.Repository;

import com.security.TodoList.Entity.VerificationToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    //@Query("select v from VerificationToken v where v.token = :token")
     VerificationToken findByToken(String token);
}
