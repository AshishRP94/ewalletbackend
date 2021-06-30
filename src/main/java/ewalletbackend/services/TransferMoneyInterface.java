package ewalletbackend.services;

import java.util.List;

import ewalletbackend.entities.TransferMoney;

public interface TransferMoneyInterface
{
	String save(TransferMoney tmoney);
//	TransferMoney get(String username);
	List<TransferMoney> getall(String username);

}
