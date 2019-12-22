package com.jobsfinder.jobsfinder.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jobsfinder.jobsfinder.model.User;

 
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);//kima type list
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
    @Transactional
	 @Modifying
	 @Query("UPDATE User t SET t.name = :nom_u , t.username = :username_u , t.password = :password_u WHERE t.email = :email_u")
	 void UpdateCompte (@Param("nom_u") String name,@Param("username_u") String username,@Param("password_u") String password,@Param("email_u") String email);
    @Transactional
   	@Modifying
   	 @Query("delete from User n where n.email = :email_u")
   void  deleteUser(@Param("email_u") String email);
}