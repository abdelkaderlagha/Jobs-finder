package com.jobsfinder.jobsfinder.dao;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
	UserDetails loadUserByUsername(String username);
}
