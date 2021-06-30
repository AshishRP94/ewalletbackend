package ewalletbackend.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	
	@Autowired
	JwtUtil jwt;
	
	
	@Autowired
	UserDetailServiceImp userimp;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
	{
	      final String tokenHeader=request.getHeader("Authorization");
	      System.out.println(tokenHeader);
 	      String username=null;
	      String jwttoken=null;
	      
	      
	      System.out.println("");
	      if((tokenHeader!=null) &&  (tokenHeader.startsWith("Bearer ")))
	      {
	    	  jwttoken=tokenHeader.substring(7);
	    	  try
	    	  {
	    		  username=this.jwt.extractUsername(jwttoken);
	    	  }
	    	  
	    	  catch(ExpiredJwtException e)
	    	  {
	    		  e.printStackTrace();
	    		  System.out.println("jwt token has expired");
	    	  }
	    	  
	    	  
	    	  catch(Exception  e)
	    	  {
	    		  e.printStackTrace();
	    		  System.out.println("error-->"+e);	    		  
	    	  }
	      }
	      else
	      {
	    	  System.out.println("invalid token does not start with bearer");	    	  
	      }
	      
	   
	      
//	      validate token
	      if(username!=null  && SecurityContextHolder.getContext().getAuthentication()==null)
	      {
	    	 final UserDetails user= this.userimp.loadUserByUsername(username);
	    	 if(this.jwt.validateToken(jwttoken, user))
	    	 {
	    		 UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
	    		 upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	    		 SecurityContextHolder.getContext().setAuthentication(upat);
	    	 }
	      }
	      else
	      {
	    	  System.out.println("token is not valid ");
	      }
	      filterChain.doFilter(request, response);	
	}
	
	

}
