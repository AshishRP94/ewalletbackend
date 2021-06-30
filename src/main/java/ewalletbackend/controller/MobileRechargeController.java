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

import ewalletbackend.entities.MobileRecharge;
import ewalletbackend.services.MobileRechargeImp;


@RestController
@CrossOrigin
public class MobileRechargeController
{
	@Autowired
	MobileRechargeImp imobile;
	
	@PostMapping("/savemobilerechargedetails")
	public ResponseEntity savemobilerechargedetails(@RequestBody MobileRecharge mobile)
	{
		String str=imobile.save(mobile);
		System.out.println("str="+str);
		if(str.equals("transaction_sucessfull")==true)
		{
			System.out.println("in if");
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(str);
		}
		else
		{
			System.out.println("in else");
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(str);
		}
		
	}
	
	@GetMapping("/findallmobilerechargedetails/{username}")
	public ResponseEntity findallmobilerechargedetails(@PathVariable("username") String username)
	{
		List<MobileRecharge> list=imobile.getall(username);
		if(list==null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("not_found");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(list);
	}
	

}
