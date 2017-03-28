package com.bot.chatinet;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.bot.Bot;
import com.bot.WrongCredentialsException;

public class ChatinetBotTest {
  private String username = "Handsome";
  private String password = "rakhmatullov";
  private Bot bot;
  @BeforeClass
  public void beforeClass() {
  }


  @Test
  public void ChatinetBot() {
	try    
	{
		bot = new ChatinetBot(username, password);
	}
	catch(WrongCredentialsException e)
	{
		e.printStackTrace();
	}
	
	bot.logout();
  }
  
  @AfterClass
  public void tearDown()
  {
	  bot.close();
  }
}
