package ewalletbackend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ewalletbackend.entities.MobileRecharge;


@Repository
public interface MobileRechargeDao extends CrudRepository<MobileRecharge,Long>
{
	List<MobileRecharge> findAllByUsername(String username);

}