package ewalletbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ewalletbackend.dao.MobileRechargeDao;
import ewalletbackend.dao.RedisUserResponseDao;
import ewalletbackend.dao.UserDao;
import ewalletbackend.entities.MobileRecharge;
import ewalletbackend.entities.TransactionSucessfull;
import ewalletbackend.entities.User;
import ewalletbackend.entities.UserResponse;


@Service
public class MobileRechargeImp implements MobileRechargeInterface
{
	@Autowired
	MobileRechargeDao dao;
	
	@Autowired
	UserDao udao;
	
	@Autowired
    RedisUserResponseDao redisuserdao;
	
    @Autowired
    KafkaTemplate<String,String> kafkatemplate;
    
    
    @Autowired
    ObjectMapper obj;
	
	@Override
	public String save(MobileRecharge mobile)
	{
		String str="";
		try
		{
			Optional<User> user=udao.findById(mobile.getUsername());
			User suser=user.get();
			if(suser.getWalletamount()>=mobile.getAmt_paid())
			{
				Long newbalance=Math.abs(suser.getWalletamount()-mobile.getAmt_paid());
				suser.setWalletamount(newbalance);
				MobileRecharge smobile;
				if(mobile.getRechargetype().equals("prepaid")==true)
				{
					
			       smobile=MobileRecharge.builder()
						.rechargetype("prepaid")
						.amt_paid(mobile.getAmt_paid())
						.circle(mobile.getCircle())
						.operator(mobile.getOperator())
						.plans(mobile.getPlans())
						.username(mobile.getUsername())
						.mnumber(mobile.getMnumber())
						.build();
				
						
				}
				else
				{
				       smobile=MobileRecharge.builder()
								.rechargetype("postpaid")
								.amt_paid(mobile.getAmt_paid())
								.circle(mobile.getCircle())
								.operator(mobile.getOperator())
								.plans("null")
								.username(mobile.getUsername())
								.mnumber(mobile.getMnumber())
								.build();
					
				}
				dao.save(smobile);
				suser.setWalletamount(newbalance);
				udao.save(suser);
				Optional<UserResponse> redis=redisuserdao.findById(mobile.getUsername());
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
					email=udao.findById(mobile.getUsername()).get().getEmail();
				}
				
				 String topic="sucess";
				
				 TransactionSucessfull trans = null;
				 trans=TransactionSucessfull.builder().damount(mobile.getAmt_paid())
						                              .tamountc(newbalance)
						                              .email(email)
						                              .flag(false)
						                              .username(mobile.getUsername())
						                              .build();		                              
						                              
				 kafkatemplate.send(topic,obj.writeValueAsString(trans));
				
				str="transaction_sucessfull";
				
			}
			else
			{
				str="transaction_failed_not_sufficient_funds";				
			}
		}
		catch(Exception e)
		{
			str="exception"+e;
		}
		
		
		return str;
	}

	@Override
	public List<MobileRecharge> getall(String username)
	{
		List<MobileRecharge>mlist=null;
		try
		{
			mlist=dao.findAllByUsername(username);			
		}
		
		catch(Exception e)
		{
			System.out.println("e"+e);
		}
		
		return mlist;
	}

}
