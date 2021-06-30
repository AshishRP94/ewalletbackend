package ewalletbackend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ewalletbackend.entities.AddMoney;

@Repository
public interface AddMoneyDao extends CrudRepository<AddMoney,Long>
{
	List<AddMoney> findAllByUsername(String username);

}
