package ewalletbackend.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignUp
{
	private String username;
	private String password;
	private String email;
	

}
