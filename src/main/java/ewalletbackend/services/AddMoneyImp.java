package ewalletbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ewalletbackend.dao.AddMoneyDao;
import ewalletbackend.dao.RedisUserResponseDao;
import ewalletbackend.dao.UserDao;
import ewalletbackend.entities.AddMoney;
import ewalletbackend.entities.TransactionSucessfull;
import ewalletbackend.entities.User;
import ewalletbackend.entities.UserResponse;


@Service
public class AddMoneyImp implements AddMoneyInterface
{
	
	@Autowired
	AddMoneyDao dao;
	
	@Autowired
	UserDao udao;
	
	@Autowired
    RedisUserResponseDao redisuserdao;

	
	@Autowired
	KafkaTemplate<String,String> kafkatemplate;
	    
	    
	@Autowired
	ObjectMapper obj;

	@Override
	public String save(AddMoney money)
	{
		AddMoney smoney=null;
		String str="";
		Optional<User> user;
		try
		{
			if(money.getMode().equals("card")==true)
			{
				smoney=AddMoney.builder()
						.bank(money.getBank())
						.accno(money.getAccno())
						.crn(money.getCrn())
						.bankingpassword(money.getBankingpassword())
						.username(money.getUsername())
						.money(money.getMoney())
						.cardnumber("null")
						.ccv("null")
						.month("null")
						.year("null")
						.build();
				
				
						
						
			}
			else
			{
				smoney=AddMoney.builder()
						.bank("null")
						.accno("null")
						.crn("null")
						.bankingpassword("null")
						.username(money.getUsername())
						.money(money.getMoney())
						.cardnumber(money.getCardnumber())
						.ccv(money.getCcv())
						.month(money.getMonth())
						.year(money.getYear())
						.build();
				
			}
			
		
			
			user=udao.findById(money.getUsername());
			User suser=user.get();
			
			boolean flag=false;
			String email=null;
			Long newbalance=Math.abs(suser.getWalletamount()+money.getMoney());
			
			suser.setWalletamount(newbalance);
			Optional<UserResponse> redis=redisuserdao.findById(money.getUsername());
			if(redis.isPresent()==true)
			{
				UserResponse redisuser=redis.get();
				redisuser.setWalletamount(newbalance);
				flag=true;
				email=redisuser.getEmail();
				redisuserdao.save(redisuser);
			}
			udao.save(suser);
			dao.save(smoney);
			
			 String topic="sucess";
			 
			 if(flag==false && email==null)
			 {
					email=udao.findById(money.getUsername()).get().getEmail();

			 }
				
			 TransactionSucessfull trans = null;
			 trans=TransactionSucessfull.builder().damount(money.getMoney())
					                              .tamountc(newbalance)
					                              .email(email)
					                              .flag(true)
					                              .username(money.getUsername())
					                              .build();	
			 
			kafkatemplate.send(topic,obj.writeValueAsString(trans));
			System.out.println("sucess");
			str="transaction_sucess";
		}
		catch(Exception e)
		{
			str="exception"+e;
		}
		
		return str;
	}
		
	

	@Override
	public List<AddMoney> getall(String username)
	{
		List<AddMoney> money=null;
		try
		{
			money= dao.findAllByUsername(username);
			
		}
		catch(Exception e)
		{
			System.out.println("e"+e);
			
		}
		
		return money;
	}
	

}
