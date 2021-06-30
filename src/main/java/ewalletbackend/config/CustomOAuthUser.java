package ewalletbackend.config;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuthUser implements OAuth2User
{
	private OAuth2User user;
	
	
	
	public CustomOAuthUser(OAuth2User user)
	{
		this.user = user;
	}

	@Override
	public Map<String, Object> getAttributes()
	{
		return user.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return user.getAuthorities();
	}

	@Override
	public String getName()
	{
		return user.getAttribute("name");
	}
	
	public String getEmail()
	{
		return user.<String>getAttribute("email");
	}

}
