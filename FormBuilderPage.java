
package com.xxxsoftware.footprints.uitest.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Form Builder (Designer) page.
 * 
 */
public class FormBuilderPage
{

    /**
     * 'Custom Fields' expand button
     */
    @FindBy(xpath = "//span[.='Custom Fields']/../../div[@role='button']")
    public WebElement btnCustomFieldsExpand;

    /**
     * One column layout button
     */
    @FindBy(xpath = "//a[@rel=\"action: 'layout', cols: 1\"]")
    public WebElement btnColumnLayout1;

    /**
     * Single line field
     */
    @FindBy(xpath = "//span[.='Single line field']")
    public WebElement fieldSingleLine;

    private WebDriver _driver;

    public FormBuilderPage(WebDriver driver)
    {
        _driver = driver;
    }

    /**
     * @return list of field cells
     */
    public List<WebElement> getFieldCells()
    {
        return _driver.findElements(By.xpath("//div[@class='fp-panel-body clearfloat']/table/tbody/tr/td/div"));
    }

    /**
     * @param number
     *        position number of form field cell. Count starts from 0
     * @return form field cell according to its position
     */
    public WebElement getFieldCell(int number)
    {
        List<WebElement> cels = getFieldCells();

        Assert.assertTrue(cels.size() > number, "");

        return cels.get(number);
    }

    /**
     * @param element
     *        form element to be drug&dropped
     * @param cellNumber
     *        position number of drug&drop destination cell. Count starts from 0
     */
    public void drugNdropForm(WebElement element, int cellNumber)
    {
        Actions builder = new Actions(_driver);

        WebElement firstCell = getFieldCell(cellNumber);

        builder.clickAndHold(element)
            .moveToElement(firstCell, -30, -40)
            .release(firstCell)
            .build()
            .perform();
    }

}
