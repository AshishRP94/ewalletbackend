package ewalletbackend.entities;

import java.util.List;

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
public class Electricity
{
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private Long id;
	private String operators;
	private String eboard;
	private String customer_id;
	private Long amount;
	private String username;
	
}
