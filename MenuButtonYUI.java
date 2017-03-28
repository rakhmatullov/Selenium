
package com.xxxsoftware.automatedtesting.framework.controls.yui.buttons;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.xxxsoftware.automatedtesting.framework.controls.ElementLocator;
import com.xxxsoftware.automatedtesting.framework.controls.extjs.AbstractExtJsElement;
import com.xxxsoftware.automatedtesting.framework.core.TestLogger;
import com.xxxsoftware.automatedtesting.framework.core.exceptions.TestAutomationException;
import com.xxxsoftware.automatedtesting.framework.pagemodel.AbstractPageModelItem;


/**
 * The Class MenuButtonYUI.
 */
public class MenuButtonYUI extends AbstractExtJsElement
{
//    private final Logger _logger = Logger.getLogger(getClass());

    private static final long MENU_LOADING_TIMEOUT = 5;

    /**
     * Instantiates a new menu button yui.
     * 
     * @param parentBrowserItem
     *        the parent browser item
     * @param elementLocator
     *        the element locator
     * @param fieldControlName
     *        the field control name
     */
    public MenuButtonYUI(AbstractPageModelItem parentBrowserItem, ElementLocator elementLocator, String fieldControlName)
    {
        super(parentBrowserItem, elementLocator, fieldControlName);
    }

    /**
     * Instantiates a new menu button yui.
     * 
     * @param parentBrowserItem
     *        the parent browser item
     * @param elementLocators
     *        the element locators
     * @param fieldControlName
     *        the field control name
     */
    public MenuButtonYUI(AbstractPageModelItem parentBrowserItem, List<ElementLocator> elementLocators, String fieldControlName)
    {
        super(parentBrowserItem, elementLocators, fieldControlName);
    }

    /**
     * Instantiates a new menu button yui.
     * 
     * @param parentBrowserItem
     *        the parent browser item
     * @param element
     *        the element
     * @param controlName
     *        the control name
     */
    public MenuButtonYUI(AbstractPageModelItem parentBrowserItem, WebElement element, String controlName)
    {
        super(parentBrowserItem, element, controlName);
    }

    /**
     * Expands menu using string array with menu item names.
     * 
     * @param options
     *        - array of menu items, starting from root menu list.
     */
    public void clickNestedMenuByPath(String... options)
    {
        int loops = 0;
        do
        {
            openRootMenu();
            sleep(500);

            for (int i = 0; i < options.length - 1; i++)
            {
                openSubmenu(options[i]);
                sleep(200);
            }
            loops++;
        } while (!isLinkDisplayed(options[options.length - 1]) & (loops < 5));
        clickMenuItem(options[options.length - 1]);
    }

    /**
     * Click menu item.
     * 
     * @param link
     *        the link
     */
    private void clickMenuItem(String link)
    {
        TestLogger.writeStep("Click the item with name " + link + " of " + getDescription());
        WebElement parent = getSeleniumWebElement();
        WebElement itemLink = parent.findElement(By.xpath(String.format("./..//a[.='%s']", link)));
        itemLink.click();
    }

    /**
     * Open submenu.
     * 
     * @param item
     *        the item
     */
    private void openSubmenu(String item)
    {
        TestLogger.writeStep("Click the sub menu item with name " + item + " of " + getDescription());
        WebElement parent = getSeleniumWebElement();
        WebElement subMenuLink = parent.findElement(By.xpath(String.format("./..//*[.='%s']", item)));
        subMenuLink.click();
        // TODO-Di This sets the value in the attribute through YUI js (brute forcing the appearance of the submenu), there should be a better way of making the submenu appears (simulating the hovering of the mouse over the list item)
        WebElement subMenuDiv = subMenuLink.findElement(By.xpath("./../div"));
        getSeleniumJavascriptExecutor().executeScript(String.format("window.Y.one('#%s').setAttribute('class','yui3-menu')",
            subMenuDiv.getAttribute("id")));

    }


    /**
     * Checks if is link displayed.
     * 
     * @param item
     *        the item
     * @return true, if is link displayed
     */
    private boolean isLinkDisplayed(String item)
    {
        WebElement parent = getSeleniumWebElement();
        WebElement subMenuLink = parent.findElement(By.xpath(String.format("./..//*[.='%s']", item)));
        return subMenuLink.isDisplayed();
    }

    /**
     * Opens root menu.
     * @return id of sub-menu.
     */
    private String openRootMenu()
    {
        TestLogger.writeStep("Click the menu button " + getDescription());
        // TODO-Di This sets the value in the attribute through YUI js (brute forcing the appearance of the menu), there should be a better way of making the menu appears (simulating the hovering of the mouse over the button)
        WebElement button = getSeleniumWebElement();
        button.click();
        WebElement menu = button.findElement(By.xpath("./../div"));
        getSeleniumJavascriptExecutor().executeScript(String.format("window.Y.one('#%s').setAttribute('class','yui3-menu')",
            menu.getAttribute("id")));

        return menu.getAttribute("id");
    }

    /**
     * Checks if is root menu open.
     * 
     * @return true, if is root menu open
     */
    private boolean isRootMenuOpen()
    {
        WebElement button = getSeleniumWebElement();
        WebElement menu = button.findElement(By.xpath("./../div"));
        return menu.isDisplayed();
    }

    /**
     * Sleep.
     * 
     * @param timeout
     *        the timeout
     */
    private void sleep(long timeout)
    {
        try
        {
            Thread.sleep(timeout);
        }
        catch (InterruptedException ex)
        {
            throw new TestAutomationException();
        }
    }
}
