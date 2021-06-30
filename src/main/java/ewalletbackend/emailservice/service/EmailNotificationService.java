package ewalletbackend.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ewalletbackend.entities.SignUp;
import ewalletbackend.entities.TransactionSucessfull;
import ewalletbackend.services.TransferMoneyTransactionSucessfull;


@Service
public class EmailNotificationService implements Notification
{
	@Autowired
	ObjectMapper obj;
	
	@Autowired
	JavaMailSender javamailsend;
	
	@Autowired
	SimpleMailMessage mail; 
	
	
	@Override
	@KafkaListener(topics="user", groupId="userregistered")
	public void sendonsignup(String event)
	{
		SignUp signup=null;
		try 
		{
			signup=obj.readValue(event, SignUp.class);
			mail.setTo(signup.getEmail());
			mail.setSubject("Wallet Created");
			mail.setText("Welcome To Ewallet");
			javamailsend.send(mail);
		} 
		catch (JsonMappingException e)
		{
			e.printStackTrace();
			System.out.println("JsonMappingException-->"+e);
		} 
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
			System.out.println("JsonProcessingException-->"+e);
		}
		
	}
	
	@Override
	@KafkaListener(topics="sucess", groupId="transactionsucessfull")
	public void sendontransactionsucesfull(String event)
	{
		TransactionSucessfull trans=null;
		try 
		{
			String msg="Amount Deducted";
			
			trans=obj.readValue(event, TransactionSucessfull.class);
			if(trans.isFlag()==true)
			{
				msg="Amount Credited";
			}
			mail.setTo(trans.getEmail());
			mail.setSubject("Payment Sucessfull");
			mail.setText(trans.getDamount()+msg+"\n"+"Balance Remaining="+trans.getTamountc());
			javamailsend.send(mail);
		}
		catch (JsonMappingException e)
		{
			e.printStackTrace();
			System.out.println("JsonMappingException-->"+e);
		} 
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
			System.out.println("JsonProcessingException-->"+e);
		}
		
	}
	
	@Override
	@KafkaListener(topics="transfersucess", groupId="transactionsucessfull")
	public void sendontransfermoneytransactionsucesfull(String event)
	{
		TransferMoneyTransactionSucessfull trans=null;
		try 
		{
			trans=obj.readValue(event, TransferMoneyTransactionSucessfull.class);
			mail.setTo(trans.getTemail());
			mail.setSubject("Money Transferred Sucessfully");
			mail.setText(trans.getTamount()+"Amount Deducted "+"\n"+"Balance Remaining="+trans.getTbalance());
			javamailsend.send(mail);
			
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
			
			mail.setTo(trans.getRemail());
			mail.setSubject("Money Received Sucessfully From "+trans.getTusername());
			mail.setText(trans.getRamount()+"Amount Added "+"\n"+"New Balance="+trans.getRbalance());
			javamailsend.send(mail);
			
			
		}
		catch (JsonMappingException e)
		{
			e.printStackTrace();
			System.out.println("JsonMappingException-->"+e);
		} 
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
			System.out.println("JsonProcessingException-->"+e);
		}
		
	}

}
