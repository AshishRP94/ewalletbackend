package ewalletbackend.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ewalletbackend.entities.UserResponse;


@Repository
public interface RedisUserResponseDao extends CrudRepository<UserResponse,String>
{

}
