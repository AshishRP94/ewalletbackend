package ewalletbackend.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ewalletbackend.dao.RedisUserResponseDao;
import ewalletbackend.dao.RoleDao;
import ewalletbackend.dao.UserDao;
import ewalletbackend.entities.LoginRequest;
import ewalletbackend.entities.Role;
import ewalletbackend.entities.SignUp;
import ewalletbackend.entities.User;
import ewalletbackend.entities.UserResponse;


@Service
public class Userimp implements UserInterface
{
	
	@Autowired
	RoleDao roledao;
	
	@Autowired
	UserDao userdao;
	

    @Autowired
    PasswordEncoder pencode;
    
    
    @Autowired
    RedisUserResponseDao redisuserdao;
    
    
    @Autowired
    KafkaTemplate<String,String> kafkatemplate;
    
    
    @Autowired
    ObjectMapper obj;

    

	@Override
	public String register(User user)
	{
		try
		{
			
			
		  Role role=roledao.findById("user")
				.orElse(Role.builder().role("user").build());
		  
		  Optional<User> temp=userdao.findById(user.getUsername());
		  if(temp.isPresent()==false)
		  {
			  
		  System.out.println("---------->"+Arrays.asList(role));
			  
		  User suser=User.builder().mobileno(user.getMobileno())
				                   .email(user.getEmail())
				                   .password(pencode.encode(user.getPassword()))
				                   .username(user.getUsername())
				                   .walletamount(user.getWalletamount())
				                   .roles(Arrays.asList(role))
				                   .build();
		  userdao.save(suser);
		  
		  String topic="user";
		  
		  SignUp signup;
		  
		  signup=SignUp.builder().email(suser.getEmail())
				                  .username(suser.getUsername())
				                  .password(suser.getPassword())
				                  .build();
		  
		  kafkatemplate.send(topic,obj.writeValueAsString(signup));
		  
		  return "sucess";
		  }
		  else
		  {
			  return "username_already_exists";  
		  }
		}
		
		 catch (JsonProcessingException e)
		 {
			 e.printStackTrace();
			 return "JsonProcessingException"+e;
	     }
		
		catch(Exception e)
		{
			System.out.println("error");
			return "error"+e;  
		}
		
	}

	@Override
	public UserResponse get(String username)
	{
		UserResponse response = null;
		Optional<UserResponse> tredisuser= redisuserdao.findById(username);
		System.out.println("in ");
		if(tredisuser.isPresent()==true)
		{
			UserResponse redisuser=tredisuser.get();
			System.out.println(" got from redis");
			System.out.println("redisuser="+redisuser);
			return redisuser;
			
		}
		else
		{
			System.out.println("----------------- else ------------------------");
		try
		{
			Optional<User> user=userdao.findById(username);
			if(user.isPresent()==true)
			{
				User tuser=user.get();
				response=UserResponse.builder() .id(tuser.getUsername())     
						                        .email(tuser.getEmail())
						                        .password(tuser.getPassword())
						                        .roles(tuser.getRoles())
						                        .mobileno(tuser.getMobileno())
						                        .walletamount(tuser.getWalletamount())
						                        .build();
				
				redisuserdao.save(response);
		
				
				System.out.println("data saved  in redis");
				System.out.println("got form my sql");
				
				return response;
				
			}
			else
			{
				return response;
			}
			
		}
		
		catch(Exception e)
		{
			System.out.println("error-->"+e);
		 	return response;
		}
		}
	}

	
	@Override
	public String updatemobileno(Long mobileno,String id)
	{
		try
		{
			Optional<User> user=userdao.findById(id);
			if(user.isPresent()==true)
			{
				User tuser=user.get();
				tuser.setMobileno(mobileno);;
				userdao.save(tuser);
				Optional<UserResponse> tredisuser=redisuserdao.findById(id);
				if(tredisuser.isPresent()==true)
				{
					UserResponse redisuser=tredisuser.get();
					redisuser.setMobileno(mobileno);
					redisuserdao.save(redisuser);
				}
				else
				{
					UserResponse redisuser=UserResponse.builder().email(tuser.getEmail())
							                                      .mobileno(mobileno)
							                                      .id(tuser.getUsername())
							                                      .password(tuser.getPassword())
							                                      .roles(tuser.getRoles())
							                                      .walletamount(tuser.getWalletamount())
							                                      .build();
					redisuserdao.save(redisuser);
					
				}
				return "sucess";
			}
			else
			{
				return "username_does_not_exists";
				
			}
			
			
		}
		catch (Exception e)
		{
			System.out.println("error"+e);
			return "error-->"+e;
		}
	}

	@Override
	public String updateemailid(String email,String id)
	{
		try
		{
			Optional<User> user=userdao.findById(id);
			if(user.isPresent()==true)
			{
				User tuser=user.get();
				tuser.setEmail(email);
				userdao.save(tuser);
				Optional<UserResponse> tredisuser=redisuserdao.findById(id);
				if(tredisuser.isPresent()==true)
				{
					UserResponse redisuser=tredisuser.get();
					redisuser.setEmail(tuser.getEmail());
					redisuserdao.save(redisuser);
				}
				else
				{
					UserResponse redisuser=UserResponse.builder() .email(email)
							                                      .mobileno(tuser.getMobileno())
							                                      .id(tuser.getUsername())
							                                      .password(tuser.getPassword())
							                                      .roles(tuser.getRoles())
							                                      .walletamount(tuser.getWalletamount())
							                                      .build();
					redisuserdao.save(redisuser);
				}
					
				return "sucess";
			}
			else
			{
				return "username_does_not_exists";
				
			}
			
			
		}
		catch (Exception e)
		{
			System.out.println("error"+e);
			return "error-->"+e;
		}
		
	}

	@Override
	public String updatepassword(String password,String id)
	{
		try
		{
			Optional<User> user=userdao.findById(id);
			if(user.isPresent()==true)
			{
				User tuser=user.get();
				tuser.setPassword(password);
				userdao.save(tuser);
				Optional<UserResponse> tredisuser=redisuserdao.findById(id);
				if(tredisuser.isPresent()==true)
				{
					UserResponse redisuser=tredisuser.get();
					redisuser.setPassword(tuser.getPassword());
					redisuserdao.save(redisuser);
				}
				else
				{
					UserResponse redisuser=UserResponse.builder()
							                                      .email(tuser.getEmail())
							                                      .mobileno(tuser.getMobileno())
							                                      .id(tuser.getUsername())
							                                      .password(password)
							                                      .roles(tuser.getRoles())
							                                      .walletamount(tuser.getWalletamount())
							                                      .build();
					
					redisuserdao.save(redisuser);
				}
				return "sucess";
			}
			else
			{
				return "username_does_not_exists";
			}
			
			
		}
		catch (Exception e)
		{
			System.out.println("error"+e);
			return "error-->"+e;
		}
	}

	@Override
	public String deleteaccount(String username)
	{
		try
		{
			userdao.deleteById(username);
			Optional<UserResponse> tredisuser=redisuserdao.findById(username);
			if(tredisuser.isPresent()==true)
			{
				redisuserdao.deleteById(username);	
			}
		

		}
		
		catch(Exception e)
		{
			System.out.println("error-->"+e);
			return "error";
		}
	  	
		return "sucess";
	}
	
	

	@Override
	public String login(LoginRequest req, String id)
	{
		Optional<UserResponse> tredisuser=redisuserdao.findById(req.getUsername());
		User tuser = null;
		boolean flag=false;
		
	if(tredisuser.isPresent()==true)
	{
		UserResponse redisuser=tredisuser.get();
	    tuser=User.builder().password(redisuser.getPassword()).build();
	    flag=true;
	}
	String status = null;

	if(flag==false)
	{
	Optional<User> user=userdao.findById(id);
	if(user.isPresent()==true)
	{
	tuser=user.get();
	UserResponse redisuser=UserResponse.builder()
            .email(tuser.getEmail())
            .mobileno(tuser.getMobileno())
            .id(tuser.getUsername())
            .password(tuser.getPassword())
            .roles(tuser.getRoles())
            .walletamount(tuser.getWalletamount())
            .build();
	redisuserdao.save(redisuser);
	}
	else 
	{
		status= "user _does_not_exits";
	}
	}
	System.out.println("tuser"+tuser.getPassword());
	String pass=req.getPassword();
	String epass=pencode.encode(pass);
	if(pass.length()==epass.length())
	{
		if(pass.equals(epass)==true)
		{
			status= "auth_sucessfull";
		}
	}
	else
	{
		status= "wrong_password";
		
	}
	return status;
	}
	
}
