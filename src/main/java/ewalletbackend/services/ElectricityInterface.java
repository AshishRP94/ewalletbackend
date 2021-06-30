package ewalletbackend.services;

import java.util.List;

import ewalletbackend.entities.Electricity;

public interface ElectricityInterface
{
	String save(Electricity ele);
//	Electricity get(String username);
	List<Electricity> getall(String username);
	
}
