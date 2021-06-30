package ewalletbackend.entities;

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
public class TransactionSucessfull
{
	private String username;
	private Long damount;
	private Long tamountc;
	boolean flag=false;
	private String email;

}
