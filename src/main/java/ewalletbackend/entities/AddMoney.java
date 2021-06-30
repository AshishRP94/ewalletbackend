package ewalletbackend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AddMoney
{
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	Long id;
	private String username;
	private String mode;
//	-------------------credit card
	private Long  money;
	private String  bank;
	private String  accno;
	private String  crn;
	private String  bankingpassword;
//	-------------------netbanking 
	private String  cardnumber;
	private String  month;
	private String  year;
	private String  ccv;
	

}
