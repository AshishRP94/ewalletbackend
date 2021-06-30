package ewalletbackend.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint
{

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException auth) throws IOException, ServletException
	{
		res.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
	}
	
}
