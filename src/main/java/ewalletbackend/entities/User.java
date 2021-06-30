package ewalletbackend.entities;

import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User 
{

	@Id
	private String username;
	private String password;
	private Long mobileno;
	private String email;
	private Long walletamount;
	
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Role> roles;
//    private boolean isAccountNonExpired;
//    private boolean isAccountNonLocked;
//    private boolean isCredentialsNonExpired;
//    private boolean isEnabled;
    
	
	
}
