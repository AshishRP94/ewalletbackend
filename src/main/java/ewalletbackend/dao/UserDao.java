package ewalletbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ewalletbackend.entities.User;

@Repository
public interface UserDao extends JpaRepository<User,String> {

}
