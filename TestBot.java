
package com.bot;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.bot.ExceptionMessages;
import com.bot.bizarre.BizarreBot;


@Test
public class TestBot extends Assert{
	private final String botName1 = "RomanKarman";
	private final String botName2 = "Andrey_chto_to";
	private final String defaultPassword = "314159";
	private final String toName = "Natasha";
	private final String message = "Test message";
	private final String defaultLoggedInMessage = "В чат вошёл";
	private Bot bot1, bot2;
	
	@Test
	public void loginBot(){
	try{
		new BizarreBot(botName1,defaultPassword);
	}catch(WrongCredentialsException e){
		System.out.println(ExceptionMessages.WRONG_PASSWORD);
		e.printStackTrace();}		
	}
	
	@Test(dependsOnMethods = "loginBot")
	public void createSeveralBots(){
		try{
			bot1 = new BizarreBot(botName1, defaultPassword);
		}catch(WrongCredentialsException e){
			System.out.println(ExceptionMessages.WRONG_PASSWORD);
			e.printStackTrace();
		};
		String generalChatText = bot1.getPublicChatText();		
		assertTrue(generalChatText.contains(botName1),"No message about account being logged in");
			
		try{
			bot2 = new BizarreBot(botName2, defaultPassword);
		}catch(WrongCredentialsException e){
			System.out.println(ExceptionMessages.WRONG_PASSWORD);
			e.printStackTrace();
		};	
		
		generalChatText = bot1.getPublicChatText();
		
		assertTrue(generalChatText.contains(botName2),"No message about account being logged in");
		
		bot1.logout();
		bot1.close();
		bot2.logout();		
		bot2.close();
	}
	
	@Test(dependsOnMethods = "loginBot")
	public void sendMessage(){
		try{
			bot1 = new BizarreBot(botName1, defaultPassword);
		} catch(WrongCredentialsException e){
			System.out.println(ExceptionMessages.WRONG_PASSWORD);
			e.printStackTrace();
		}
		
		try{
			bot1.sendPrivateMessage(toName, message);
		}catch(MessageNotSentException e){
			System.out.println("Cannot send private message");
			e.printStackTrace();
		}
		String privateText = bot1.getPrivateChatText();
		assertTrue(privateText.contains(message), "message was not displayed");
		
		try{
			bot1.sendPublicMessage(toName, message);		
		}catch(MessageNotSentException e){
			System.out.println("Cannot send public message");
			e.printStackTrace();			
		}
		String publicText = bot1.getPrivateChatText();
		assertTrue(publicText.contains(message)&&publicText.contains(toName), "message was not displayed");
				
		try{
			bot1.sendPublicMessage(message);
		}catch(MessageNotSentException e){
			System.out.println("Cannot send public message");
			e.printStackTrace();
		}		
		
		publicText = bot1.getPrivateChatText();
		assertTrue(publicText.contains(message), "message was not displayed");		
		bot1.logout();
		bot1.close();
	}
	
	@Test(dependsOnMethods = "loginBot")
	public void getPrivateChatText(){
		try{
			bot1 = new BizarreBot(botName1, defaultPassword);
		}catch(WrongCredentialsException e){
			System.out.println(ExceptionMessages.WRONG_PASSWORD);
			e.printStackTrace();
		}
		
		try{
			bot1.sendPrivateMessage(botName1, message);			
		}catch(MessageNotSentException e){
			System.out.println("Cannot send private message");
			e.printStackTrace();
		}
		
		String privateText = null;
		privateText = bot1.getPrivateChatText();
		
		if (privateText != null){
			assertTrue((privateText.length()<=0)&&(privateText.contains(message)), "No public text has been retrieved");
		}else
			assertTrue(false, "No public text has been retrieved");					
	}
	
	@Test(dependsOnMethods = "loginBot")
	public void getPublicChatText(){
		try{
			bot1 = new BizarreBot(botName1, defaultPassword);
		}catch(WrongCredentialsException e)
		{
			System.out.println(ExceptionMessages.WRONG_PASSWORD);
			e.printStackTrace();
		}
		
		String publicText = null; 
		publicText = bot1.getPublicChatText();
		
		if (publicText != null)
		{
			assertTrue(publicText.length()<=0, "No public text has been retrieved");
		}else
		{
			assertTrue(false, "No public text has been retrieved");		
		}
		
		bot1.logout();
		bot1.close();
	}
	
	@AfterMethod
	public void tearDown()
	{
		
	}
	
}	

