package ewalletbackend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Role implements GrantedAuthority
{


	@Id
	private String role;
	
	
	@Override
	public String getAuthority()
	{
		return this.role;
	}
	

}
