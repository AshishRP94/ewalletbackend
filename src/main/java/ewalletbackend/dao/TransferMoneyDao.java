package ewalletbackend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ewalletbackend.entities.TransferMoney;


@Repository
public interface  TransferMoneyDao extends   CrudRepository<TransferMoney,Long>
{
	List<TransferMoney> findAllByUsername(String username);
}
