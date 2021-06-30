package ewalletbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ewalletbackend.dao.ElectricityDao;
import ewalletbackend.dao.RedisUserResponseDao;
import ewalletbackend.dao.UserDao;
import ewalletbackend.entities.Electricity;
import ewalletbackend.entities.TransactionSucessfull;
import ewalletbackend.entities.User;
import ewalletbackend.entities.UserResponse;

@Service
public class ElectricityImp implements ElectricityInterface
{
	@Autowired
	ElectricityDao edao;
	
	
	@Autowired
	UserDao udao;
	
	@Autowired
    RedisUserResponseDao redisuserdao;
	
    @Autowired
    KafkaTemplate<String,String> kafkatemplate;
    
    
    
    @Autowired
    ObjectMapper obj;
	
	@Override
	public String save(Electricity ele)
	{
		Electricity sele=null;
		String str="";
		try
		{
			User user= udao.getById(ele.getUsername());
		
			if(user.getWalletamount()>=ele.getAmount())
			{
				
			sele=Electricity.builder()
					.amount(ele.getAmount())
					.operators(ele.getOperators())
					.customer_id(ele.getCustomer_id())
					.eboard(ele.getEboard())
					.username(ele.getUsername())
					.build();
			edao.save(sele);
			Long newbalance=Math.abs(user.getWalletamount()-ele.getAmount());
			user.setWalletamount(newbalance);
			udao.save(user);
			Optional<UserResponse> redis=redisuserdao.findById(ele.getUsername());
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
				email=udao.findById(ele.getUsername()).get().getEmail();
			}
			
			 String topic="sucess";
			
			 TransactionSucessfull trans = null;
			 trans=TransactionSucessfull.builder().damount(ele.getAmount())
					                              .tamountc(newbalance)
					                              .email(email)
					                              .flag(false)
					                              .username(ele.getUsername())
					                              .build();
					                              
					                              
			 kafkatemplate.send(topic,obj.writeValueAsString(trans));
			 

			
			str="transaction_sucess";
 			}
			else
			{
				str="transaction_failed (wallet's money not sufficient)";
			}
		}
		catch(Exception e)
		{
			str="exception"+e;
		}
		
		return str;
	}



	@Override
	public List<Electricity> getall(String username)
	{
		List<Electricity> elist = null;
		try
		{
		     elist =edao.findAllByUsername(username);
	
			
		}
		catch(Exception e)
		{
			System.out.println("exception"+e);
		}
		
		return elist;
		
	}
	

}
