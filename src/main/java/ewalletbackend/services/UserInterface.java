package ewalletbackend.services;

import ewalletbackend.entities.LoginRequest;
import ewalletbackend.entities.User;
import ewalletbackend.entities.UserResponse;
import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ImplementationDefinition.Optional;

public interface UserInterface
{
	String register(User user); 
	UserResponse get(String user);
	String deleteaccount(String id); 
	String updatemobileno(Long mobileno,String id); 
	String updateemailid(String email,String id); 
	String updatepassword(String password,String id); 
	String login(LoginRequest req,String id); 

}
