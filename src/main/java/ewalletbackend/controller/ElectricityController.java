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

import ewalletbackend.entities.Electricity;
import ewalletbackend.services.ElectricityImp;

@RestController
@CrossOrigin
public class ElectricityController
{
	@Autowired
	ElectricityImp iele;
	
	
	@PostMapping("/saveelectricitydetails")
	public ResponseEntity saveelectricitydetails(@RequestBody Electricity electricity)
	{
		String str=iele.save(electricity);
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
	
	
	
	@GetMapping("/findallelectricitydetails/{username}")
	public ResponseEntity findallelectricitydetails(@PathVariable("username") String username)
	{
		List<Electricity> list=iele.getall(username);
		if(list==null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("not_found");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(list);
	}
	
	
	
	
	
	

}
