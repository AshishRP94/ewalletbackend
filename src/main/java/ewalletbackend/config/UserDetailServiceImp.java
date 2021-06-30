package ewalletbackend.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ewalletbackend.dao.RoleDao;
import ewalletbackend.dao.UserDao;
import ewalletbackend.entities.LoginRequest;
import ewalletbackend.entities.Role;
import ewalletbackend.entities.User;
import lombok.Setter;

@Service
public class UserDetailServiceImp implements UserDetailsService
{
	@Autowired
	UserDao userdao;
	
	@Autowired
	RoleDao roledao;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user=userdao.findById(username)
				.orElseThrow(()->new UsernameNotFoundException("username_does_not_exists"));
		
		UserDetails uduser=new  UserDetails()
		{

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities()
			{

				return user.getRoles();
			}

			@Override
			public String getPassword() {
				
				return user.getPassword();
			}
			
		
		
			
	

			@Override
			public String getUsername()
			{
				return user.getUsername();
			}

			@Override
			public boolean isAccountNonExpired()
			{
//				return user.isAccountNonExpired();
				return true;
			}

			@Override
			public boolean isAccountNonLocked()
			{
//				return user.isAccountNonLocked();
				return true;
			}

			@Override
			public boolean isCredentialsNonExpired()
			{
//				return user.isCredentialsNonExpired();
				return true;
			}

			@Override
			public boolean isEnabled()
			{
//				return user.isEnabled();
				return true;
			
			}
		};
		
		return uduser;
	}
	
	
	
	

}
