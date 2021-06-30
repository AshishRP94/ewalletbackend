package ewalletbackend.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransferMoneyTransactionSucessfull
{
	private String tusername;
	private String temail;
	private Long tamount;
	private Long tbalance;
//	---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private String rusername;
	private String remail;
	private Long ramount;
	private Long rbalance;
	

}
