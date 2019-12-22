package com.jobsfinder.jobsfinder.controller;

import java.util.HashSet;
import java.util.Set;
 
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobsfinder.jobsfinder.configuration.JwtProvider;
import com.jobsfinder.jobsfinder.dao.RoleRepository;
import com.jobsfinder.jobsfinder.dao.UserRepository;
import com.jobsfinder.jobsfinder.message.JwtResponse;
import com.jobsfinder.jobsfinder.message.LoginForm;
import com.jobsfinder.jobsfinder.message.SignUpForm;
import com.jobsfinder.jobsfinder.model.Role;
import com.jobsfinder.jobsfinder.model.RoleName;
import com.jobsfinder.jobsfinder.model.User;


 
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")//ki bech na3mel el authentification fel postman n7ot /api/auth
public class AuthRestAPIs {
 
    @Autowired
    AuthenticationManager authenticationManager;//classe predefine fi spring security
 
    @Autowired
    UserRepository userRepository;
 
    @Autowired
    RoleRepository roleRepository;
 
    @Autowired
    PasswordEncoder encoder;//predifinie aussi cryptage password et decryptage aussi
 
    @Autowired
    JwtProvider jwtProvider;
 
    @PostMapping("/signin")//bech nda5el mes info bech ygeneri el web token
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {//reponse entite ? //matsir el auth  ela ma username wpassword valid  si login form valid
 //el login matet3ada el ma username w pass s7a7 requestbody bech yefhem eli hiya formulaire
        Authentication authentication = authenticationManager.authenticate(//hne el authetification
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
 
        SecurityContextHolder.getContext().setAuthentication(authentication);//par defaut ygeneri el reponse w el web token w y9olek ok bech ygiblez fel postman type bearer
 
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
 
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",//si username exist deja
                    HttpStatus.BAD_REQUEST);
        }
 
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",//si mail exist deja
                    HttpStatus.BAD_REQUEST);
        }
 
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
 
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
 
        strRoles.forEach(role -> {
          switch(role) {
          case "admin":
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                  .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roles.add(adminRole);
            
            break;
          case "pm":
                Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
                  .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                roles.add(pmRole);
                
            break;
          default:
              Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                  .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
              roles.add(userRole);              
          }
        });
        
        user.setRoles(roles);
        userRepository.save(user);//ajout mta3 el utilisateur
 
        return ResponseEntity.ok().body("User registered successfully!");//enregistrement effectue
    }
}