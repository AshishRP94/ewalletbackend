package ewalletbackend.entities;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@RedisHash
public class UserResponse  
{

	@Id
	private String id;
	private String password;
	private Long mobileno;
	private String email;
	private Long walletamount;
	private List<Role> roles;
	
}
