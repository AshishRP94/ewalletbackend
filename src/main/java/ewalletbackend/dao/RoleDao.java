package ewalletbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ewalletbackend.entities.Role;


@Repository
public interface RoleDao extends JpaRepository<Role,String>
{
	
}
