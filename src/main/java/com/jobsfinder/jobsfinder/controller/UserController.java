package com.jobsfinder.jobsfinder.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jobsfinder.jobsfinder.dao.UserRepository;
import com.jobsfinder.jobsfinder.model.User;


@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@GetMapping(value ="/api/auth/compte/{username}")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	public Optional<User> consulter_compte(@PathVariable String username){
		return userRepository.findByUsername(username);
	}
	@PostMapping(value ="/api/auth/user")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	public ResponseEntity<Void> ajouterUser(@Valid @RequestBody User user) {
		
	User user1=userRepository.save(user);
	if(user==null) {
		return ResponseEntity.noContent().build();
	}
	URI location= ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(user1.getId())
			.toUri();
	return ResponseEntity.created(location).build();
	}
	@PutMapping (value ="/api/auth/UPUser/{name}/{username}/{password}/{email}/")//update clients
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	public void update_user (@PathVariable String name,@PathVariable String username,@PathVariable String password,@PathVariable String email) {
      
      userRepository.UpdateCompte(name, username, password, email);
        
	}
	@DeleteMapping(value="/api/auth/delUser/{email}")
	 @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('PM')")
	public void supprimerUser(@PathVariable String email) {
		 userRepository.deleteUser(email);
	}

}
