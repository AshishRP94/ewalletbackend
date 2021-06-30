package ewalletbackend.services;

import java.util.List;

import ewalletbackend.entities.MobileRecharge;

public interface MobileRechargeInterface
{
	String save(MobileRecharge mobile);
//	MobileRecharge get(String username);
	List<MobileRecharge> getall(String username);

}
