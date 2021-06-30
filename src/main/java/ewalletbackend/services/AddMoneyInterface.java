package ewalletbackend.services;

import java.util.List;

import ewalletbackend.entities.AddMoney;

public interface AddMoneyInterface
{
	
	String save(AddMoney money);
//	AddMoney get(String username);
	List<AddMoney> getall(String username);
}
