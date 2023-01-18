package ca.sheridancollege.nhuyle.a4.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import ca.sheridancollege.nhuyle.a4.database.DatabaseAccess;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired @Lazy 

	private DatabaseAccess da;
	
	
//get the user's details by user name
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		ca.sheridancollege.nhuyle.a4.beans.User user = da.findUesrAccount(username);
		if(user==null) {
			throw new UsernameNotFoundException("User " + username 
					+ " not found in the data.");
			}		
		List<String>roles = da.getRolesByUserId(user.getUserId());		
	List<GrantedAuthority> grandList= new ArrayList<GrantedAuthority>();

	if(roles !=null) {
		for(String role : roles) {
			grandList.add(new SimpleGrantedAuthority(role) );			
		}
	}
	UserDetails userDetails = new User(user.getUserName(),
			user.getPassword(), grandList);
		return userDetails;
	}

}
