package ewalletbackend.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ewalletbackend.entities.Electricity;

@Repository
public interface ElectricityDao extends CrudRepository<Electricity,Long>
{
	List<Electricity> findAllByUsername(String username);
	
}
