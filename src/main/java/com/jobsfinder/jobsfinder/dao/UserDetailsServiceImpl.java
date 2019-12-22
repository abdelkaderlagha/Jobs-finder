package com.jobsfinder.jobsfinder.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobsfinder.jobsfinder.model.User;


 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)//bel username bech ychargilek les donnes el kol mte3ek
            throws UsernameNotFoundException {
      
        User user = userRepository.findByUsername(username)//itha l9ah el username sinon raise error
                  .orElseThrow(() -> 
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
        );
 
        return UserPrinciple.build(user);//bech yejbed les donnes mta3 el userprincipale
    }
 

}
