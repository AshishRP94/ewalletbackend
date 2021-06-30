package ewalletbackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ewalletbackend.entities.Dth;
import ewalletbackend.entities.Electricity;
import ewalletbackend.entities.MobileRecharge;


@Repository
public interface DthDao extends CrudRepository<Dth,Long>
{
	List<Dth> findAllByUsername(String username);

	
	
}
