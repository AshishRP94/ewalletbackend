package ewalletbackend.emailservice.service;

public interface Notification
{
	public void sendonsignup(String event);
	public void sendontransactionsucesfull(String event);
	public void sendontransfermoneytransactionsucesfull(String event);

}
