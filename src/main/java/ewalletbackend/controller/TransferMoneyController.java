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

import ewalletbackend.entities.TransferMoney;
import ewalletbackend.services.TransferMoneyImp;


@RestController
@CrossOrigin
public class TransferMoneyController
{
	
	@Autowired
	TransferMoneyImp itmoney;
	
	
	
	@PostMapping("/savetransfermoneydetails")
	public ResponseEntity saveaddmoneydetails(@RequestBody TransferMoney tmoney)
	{
		String str=itmoney.save(tmoney);
		if(str.equals("transaction_sucess")==true)
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
	
	
	@GetMapping("/findalltransfermoneydetails/{username}")
	public ResponseEntity findalltransfermoneydetails(@PathVariable("username") String username)
	{
		List<TransferMoney> list=itmoney.getall(username);
		if(list==null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("not_found");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(list);
	}

	
}
