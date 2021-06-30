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

import ewalletbackend.entities.Dth;
import ewalletbackend.services.DthImp;

@RestController
@CrossOrigin
public class DthController
{
	@Autowired
	DthImp idth;
	
	@PostMapping("/savedthdetails")
	public ResponseEntity savedthdetails(@RequestBody Dth dth)
	{
		String str=idth.save(dth);
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
	
	
	@GetMapping("/findalldthdetails/{username}")
	public ResponseEntity findalldthdetails(@PathVariable("username") String username)
	{
		List<Dth> list=idth.getall(username);
		if(list==null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("not_found");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(list);
	}
	
	
	
	

}
