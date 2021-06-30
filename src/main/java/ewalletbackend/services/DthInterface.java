package ewalletbackend.services;

import java.util.List;

import ewalletbackend.entities.Dth;

public interface DthInterface
{
	String save(Dth dth);
//	Dth get(String username);
	List<Dth> getall(String username);

}
