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
public class MobileRecharge
{
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	Long id;
	private String username;
	private String rechargetype;
	private Long mnumber;
	private String operator;
	private String circle;
	private String plans;
	private Long amt_paid;
}
