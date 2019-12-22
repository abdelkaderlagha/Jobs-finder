package com.jobsfinder.jobsfinder.dao;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
	UserDetails loadUserByUsername(String username);//bech tgiblek el donnes el kol mta3 el username

}
