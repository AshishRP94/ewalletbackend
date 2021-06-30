package ewalletbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ewalletbackend.dao.DthDao;
import ewalletbackend.dao.RedisUserResponseDao;
import ewalletbackend.dao.UserDao;
import ewalletbackend.entities.Dth;
import ewalletbackend.entities.TransactionSucessfull;
import ewalletbackend.entities.User;
import ewalletbackend.entities.UserResponse;

@Service
public class DthImp implements DthInterface
{
	
	
	@Autowired
	DthDao ddao;
	
	@Autowired
	UserDao udao;
	
	@Autowired
    RedisUserResponseDao redisuserdao;
	
	@Autowired
	KafkaTemplate<String,String> kafkatemplate;
	    
	    
	@Autowired
	ObjectMapper obj;
	
	@Override
	public String save(Dth dth)
	{
		String str="";
		try
		{
			
		Optional<User> user=udao.findById(dth.getUsername());
		User suser=user.get();
		if(suser.getWalletamount()>=dth.getAmt())
		{
			Dth sdth=Dth.builder().amt(dth.getAmt())
		             .operator(dth.getOperator())
		             .subscriberid(dth.getSubscriberid())
		             .username(dth.getUsername())
		             .build();
			ddao.save(sdth);
			
			Long newbalance=Math.abs(suser.getWalletamount()-dth.getAmt());

			suser.setWalletamount(newbalance);
			udao.save(suser);
			String id=dth.getUsername();
			Optional<UserResponse> redis=redisuserdao.findById(id);
			
			boolean flag=false;
			String email=null;
			
			if(redis.isPresent()==true)
			{
				UserResponse redisuser=redis.get();
				redisuser.setWalletamount(newbalance);
				email=redisuser.getEmail();
				flag=true;
				redisuserdao.save(redisuser);
			}
			
			if(flag==false && email==null)
			{
				email=udao.findById(dth.getUsername()).get().getEmail();
			}
			
			 String topic="sucess";
			
			 TransactionSucessfull trans = null;
			 trans=TransactionSucessfull.builder().damount(dth.getAmt())
					                              .tamountc(newbalance)
					                              .email(email)
					                              .flag(false)
					                              .username(dth.getUsername())
					                              .build();	
			 
					                              
			kafkatemplate.send(topic,obj.writeValueAsString(trans));
			str="transaction_sucess";
		}
		else
		{
			str= "transaction_failed_money_not_sufficient)";
		}
		}
		catch(Exception e)
		{
			System.out.println("e"+e);
			str= "exception"+e;
		}
		
		return str;
	}

	@Override
	public List<Dth> getall(String username)
	{
		List<Dth> ldth = null;
		try
		{
			ldth=ddao.findAllByUsername(username);
			return ldth;
		}
		catch(Exception e)
		{
			System.out.println("exception"+e);
		}
		
		return ldth;
	}
	
}
