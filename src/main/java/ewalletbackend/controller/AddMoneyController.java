package ewalletbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ewalletbackend.entities.AddMoney;
import ewalletbackend.services.AddMoneyImp;


@RestController
@CrossOrigin
public class AddMoneyController
{
	@Autowired
	AddMoneyImp imoney;
	
	@PostMapping("/saveaddmoneydetails")
	public ResponseEntity saveaddmoneydetails(@RequestBody AddMoney mobile)
	{
		String str=imoney.save(mobile);
		if(str.equals("transaction_sucessfull")==true)
		{
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(str);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(str);
		}
	
	}
	
	@GetMapping("/findalladdmoneydetails/{username}")
	public ResponseEntity findallmobilerechargedetails(@PathVariable("username") String username)
	{
		List<AddMoney> list=imoney.getall(username);
		if(list==null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("not_found");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(list);
	}

	
}
