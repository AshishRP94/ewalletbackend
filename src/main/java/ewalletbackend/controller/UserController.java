package ewalletbackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ewalletbackend.entities.LoginRequest;
import ewalletbackend.entities.PasswordChangeRequest;
import ewalletbackend.entities.User;
import ewalletbackend.services.Userimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin("*")
public class UserController
{
	
	@Autowired
	Userimp iuser;
	@PostMapping("/register")
    @PreAuthorize(value = "permitAll()")
	public ResponseEntity register(@RequestBody User user)
	{
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(iuser.register(user));
	}
	
	
//	@GetMapping("/loginuser")
//	public ResponseEntity login(@RequestBody LoginRequest req )
//	{
//		String status=iuser.login(req, req.getUsername());
//		if(status.equals("auth_sucessfull")==true)
//		{
//			return ResponseEntity.status(HttpStatus.ACCEPTED).body(status);
//		}
//		else if(status.equals("wrong_password")==true)
//		{
//			
//		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(status);
//		}
//		else
//		{
//			
//		return ResponseEntity.status(HttpStatus.FORBIDDEN)
//				.body(status);
//		}
//	}
	

	
	
	
	
	@GetMapping("/updatemobileno/{mobileno}/id/{uid}")
    @PreAuthorize(value = "hasAnyAuthority('admin','user')")
	public ResponseEntity updmobileno(@PathVariable("mobileno") Long mobileno,@PathVariable("id") String uid,Authentication auth)
	{
		UsernamePasswordAuthenticationToken usernamepasswordauthtoken=new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getAuthorities());
		if(auth.getAuthorities().size()==1)
		{
			SimpleGrantedAuthority authority=new SimpleGrantedAuthority(auth.getAuthorities().iterator().next().getAuthority());
			if((authority.getAuthority().equals("user") && (!usernamepasswordauthtoken.getName().equals(uid))))
			{
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			
		}
		
		
		try
		{
			return ResponseEntity.ok().body(iuser.updatemobileno(mobileno,uid));
		}
		catch(Exception e)
		{
			System.out.println("error-->"+e);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

		}
	}
	
	@GetMapping("/updateemail/{email}/id/{uid}")
    @PreAuthorize(value = "hasAnyAuthority('admin','user')")
	public ResponseEntity updateemail(@PathVariable("email") String email,@PathVariable("id") String uid,Authentication auth)
	{
		UsernamePasswordAuthenticationToken usernamepasswordauthtoken=new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getAuthorities());
		if(auth.getAuthorities().size()==1)
		{
			SimpleGrantedAuthority authority=new SimpleGrantedAuthority(auth.getAuthorities().iterator().next().getAuthority());
			if((authority.getAuthority().equals("user") && (!usernamepasswordauthtoken.getName().equals(uid))))
			{
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			
		}
		
		try
		{
			return ResponseEntity.ok().body(iuser.updateemailid(email,uid));
		}
		catch(Exception e)
		{
			System.out.println("error-->"+e);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

		}
	}
	
	
	@GetMapping("/get/{uid}")
	public ResponseEntity get(@PathVariable("uid") String uid,Authentication auth)
	{
		System.out.println("---------xxxxxxxxx-------------x--get in---------------------------------------------------");
		UsernamePasswordAuthenticationToken usernamepasswordauthtoken=new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getAuthorities());
		if(auth.getAuthorities().size()==1)
		{
			System.out.println("if");
			SimpleGrantedAuthority authority=new SimpleGrantedAuthority(auth.getAuthorities().iterator().next().getAuthority());
			if((authority.getAuthority().equals("user") && (!usernamepasswordauthtoken.getName().equals(uid))))
			{
				System.out.println("if auth");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			
		}
		
		
		try
		{
			return ResponseEntity.ok().body(iuser.get(uid));
		}
		catch(Exception e)
		{
			System.out.println("error-->"+e);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

		}
	}
	
	
	@GetMapping("/updateemail/{password}/id/{uid}")
    @PreAuthorize(value = "hasAnyAuthority('admin','user')")
	public ResponseEntity updatepassword(@RequestBody PasswordChangeRequest req,Authentication auth)
	{
		UsernamePasswordAuthenticationToken usernamepasswordauthtoken=new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getAuthorities());
		if(auth.getAuthorities().size()==1)
		{
			SimpleGrantedAuthority authority=new SimpleGrantedAuthority(auth.getAuthorities().iterator().next().getAuthority());
			if((authority.getAuthority().equals("user") && (!usernamepasswordauthtoken.getName().equals(req.getId()))))
			{
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			
		}
		
		try
		{
			return ResponseEntity.ok().body(iuser.updatepassword(req.getPassword(),req.getId()));
		}
		catch(Exception e)
		{
			System.out.println("error-->"+e);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

		}
	}

	
	
	@GetMapping("/deleteaccount/{uid}")
    @PreAuthorize(value = "hasAnyAuthority('admin','user')")
	public ResponseEntity deleteaccount(@PathVariable("email") String uid,Authentication auth)
	{
		UsernamePasswordAuthenticationToken usernamepasswordauthtoken=new UsernamePasswordAuthenticationToken(auth.getPrincipal(),auth.getAuthorities());
		if(auth.getAuthorities().size()==1)
		{
			SimpleGrantedAuthority authority=new SimpleGrantedAuthority(auth.getAuthorities().iterator().next().getAuthority());
			if((authority.getAuthority().equals("user") && (!usernamepasswordauthtoken.getName().equals(uid))))
			{
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			
		}
		
		try
		{
			return ResponseEntity.ok().body(iuser.deleteaccount(uid));
		}
		catch(Exception e)
		{
			System.out.println("error-->"+e);
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

		}
	}
	



	
	
	
	
	

	

}
