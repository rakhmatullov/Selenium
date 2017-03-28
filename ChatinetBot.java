package com.bot.chatinet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.bot.Bot;
import com.bot.MessageNotSentException;
import com.bot.WrongCredentialsException;
import com.bot.account.AccountExistsException;

public class ChatinetBot extends Bot{
	private static String url = "http://chatinet.zp.ua/";
	private String username;
	private String password;
	private int roomNumber;
	private int colorNumber;
			
	public ChatinetBot(String username, String password) throws WrongCredentialsException
	{
		super(url);		
		this.username = username;
		this.password = password;
		logIn();
	}
	
	public ChatinetBot(String username, String password, int roomNumber, int colorNumber) throws WrongCredentialsException
	{
		super(url);
		
		this.username = username;
		this.password = password;
		this.roomNumber = roomNumber;
		this.colorNumber = colorNumber;
		
		selectRoomAndColor();
		logIn();
	}
	
	private void logIn()
	{
		findElement(By.name("nik")).sendKeys(username);
		findElement(By.name("pass")).sendKeys(password);
		findElement(By.name("enter")).click();
	}
	
	private void selectRoomAndColor()
	{
		
	}
	

	@Override
	public void sendPrivateMessage(String toName, String message)
			throws MessageNotSentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendPublicMessage(String toName, String message)
			throws MessageNotSentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendPublicMessage(String message)
			throws MessageNotSentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPrivateChatText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPublicChatText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout() 
	{
		switchTo().frame("send");
		//System.out.println(getPageSource());
		findElement(By.xpath("//img[@src='/images/exit.gif']")).click();
	}

	@Override
	protected void registerAccount(String name, String password)
			throws AccountExistsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void goToRegistrationPage() {
		// TODO Auto-generated method stub
		
	}

}
