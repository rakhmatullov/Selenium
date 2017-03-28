package com.bot.bizarre;

import com.bot.account.Account;
import com.bot.account.AccountExistsException;
import com.bot.Bot;
import com.bot.MessageNotSentException;
import com.bot.WrongCredentialsException;

/**
 * 
 * @author RomanR
 *
 */
public class BizarreBot extends Bot{
	private static final String loginPageUrl = "http://www.bizarre.kiev.ua/";
	private static final String registrationPageUrl = "http://www.bizarre.kiev.ua/";
	
	private static final String loginEditboxXpath = "";
	private static final String passwordEditboxXpath = "";
	
	public BizarreBot (String name, String password)throws WrongCredentialsException{
		super(loginPageUrl);
	};
	
	//Other parameters like color and sex to be included
	public void registerAccount(String name, String password) throws AccountExistsException{
		goToRegistrationPage();		
	}
	public void goToRegistrationPage(){
		this.get(registrationPageUrl);
		
	}; 
	
	private void inputUserDetails(Account account){
		
	}
	
	
	public void sendPrivateMessage(String toName, String message) throws MessageNotSentException{};
	public void sendPublicMessage(String toName, String message) throws MessageNotSentException{};
	public void sendPublicMessage(String message) throws MessageNotSentException{};
	public String getPrivateChatText(){return new String("TODO");};
	public String getPublicChatText(){return new String("TODO");};	
	public void logout()
	{
		// TODO-RR: to include clicking on exit button
	};
	
	
	
}
