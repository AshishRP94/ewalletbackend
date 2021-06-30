package ewalletbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ewalletbackend.dao.RedisUserResponseDao;
import ewalletbackend.dao.TransferMoneyDao;
import ewalletbackend.dao.UserDao;
import ewalletbackend.entities.TransferMoney;
import ewalletbackend.entities.User;
import ewalletbackend.entities.UserResponse;


@Service
public class TransferMoneyImp implements TransferMoneyInterface
{
	@Autowired
	TransferMoneyDao dao;
	@Autowired
	UserDao udao;
	
	@Autowired
    RedisUserResponseDao redisuserdao;
	
    @Autowired
    KafkaTemplate<String,String> kafkatemplate;
    
    
    @Autowired
    ObjectMapper obj;

	@Override
	public String save(TransferMoney tmoney)
	{
		String str="";
		try
		{
			Optional<User> ttuser=udao.findById(tmoney.getUsername());
			Optional<User> truser=udao.findById(tmoney.getTusername());
			if(truser.isPresent()==true)
			{
			
			TransferMoney stmoney=TransferMoney.builder()
					.money(tmoney.getMoney())
					.tusername(tmoney.getTusername())
					.username(tmoney.getUsername())
					.build();
			
			
		    User tuser=ttuser.get();
		    User ruser=	truser.get();
		    
		    if(tuser.getWalletamount()>=tmoney.getMoney())
		    {
		    	dao.save(stmoney);
		    	long dbalance=Math.abs(tuser.getWalletamount()-tmoney.getMoney());
		    	long abalance=Math.abs(Math.abs(ruser.getWalletamount()+tmoney.getMoney()));
		    	tuser.setWalletamount(dbalance);
		    	ruser.setWalletamount(abalance);
		    	udao.save(tuser);
		    	udao.save(ruser);
		    	Optional<UserResponse> tredis=redisuserdao.findById(tuser.getUsername());
		    	
//		    	boolean flag1=false;
		    	String email1="";
//		    	
//		    	boolean flag2=false;
		    	String email2="";
		    	
				if(tredis.isPresent()==true)
				{
					UserResponse tredisuser=tredis.get();
					tredisuser.setWalletamount(dbalance);
//					flag1=true;
//					email1=tredisuser.getEmail();
					redisuserdao.save(tredisuser);
				}
				
		    	Optional<UserResponse> rredis=redisuserdao.findById(ruser.getUsername());
			    if(rredis.isPresent()==true)
				{
					UserResponse rredisuser=rredis.get();
					rredisuser.setWalletamount(abalance);
//					flag2=true;
//					email2=rredisuser.getEmail();
					redisuserdao.save(rredisuser);
				}
			    
			    
			    email1=tuser.getEmail();
			    email2=ruser.getEmail();
			    
			    String topic="transfersucess";
			    
			     TransferMoneyTransactionSucessfull trans=TransferMoneyTransactionSucessfull.builder().tamount(tmoney.getMoney())
			    		                                                                               .tbalance(dbalance)
			    		                                                                               .tusername(tmoney.getUsername())
			    		                                                                               .ramount(tmoney.getMoney())
			    		                                                                               .ramount(abalance)
			    		                                                                               .rusername(tmoney.getTusername())
			    		                                                                               .build();
			    		                                                                               
			    		                                                                              
			    
				 kafkatemplate.send(topic,obj.writeValueAsString(trans));

			    
		    	str="transaction_successfull";
		    	
		    }
		    else
		    {
		    	str="transaction_failed_not_sufficient_money";
		    }
		
			}
			
			
			
		else
		{
			str="user_not_found";
		}
		}
		catch(Exception e)
		{
			str="exception"+e;
		}
		return str;
	}

	@Override
	public List<TransferMoney> getall(String username)
	{
		List<TransferMoney> list = null;
		try
		{
			list=dao.findAllByUsername(username);
			
		}
			
		catch(Exception e)
		{
			System.out.println("e"+e);
		}
		return list;
	}
	

}
