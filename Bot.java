package com.bot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.bot.account.AccountExistsException;

/**
 * 
 * @author RomanR
 *
 */
public abstract class Bot extends FirefoxDriver{
	private WebDriver driver;
	
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	abstract public void sendPrivateMessage(String toName, String message) throws MessageNotSentException;
	abstract public void sendPublicMessage(String toName, String message) throws MessageNotSentException;
	abstract public void sendPublicMessage(String message)throws MessageNotSentException;	
	
	abstract public String getPrivateChatText();
	abstract public String getPublicChatText();
	
	abstract public void logout();
	
	public Bot(String url){
		super();
		this.get(url);
	}
	abstract protected void registerAccount(String name, String password)throws AccountExistsException;
	abstract protected void goToRegistrationPage();
}