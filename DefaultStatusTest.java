
package com.xxxsoftware.automatedtesting.tests.chat;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.xxxsoftware.automatedtesting.tests.TestGroups;

@Test(groups = {TestGroups.CHAT, TestGroups.FULL_BVT})
public class DefaultStatusTest extends AbstractChatTest
{
    @BeforeClass(groups = TestGroups.PREPARATION)
    public void beforeTestClass()
    {
        // [PRE] Chat must be enabled for the system
        enableChat();
    }

    @AfterClass(groups = TestGroups.PREPARATION)
    public void afterTestClass()
    {
        // Clean up precondition
        disableChat();
    }

    @AfterMethod
    public void afterTest()
    {
        logOutAllUsers();
    }

    @Test(groups = {TestGroups.MTC_NUMBER + 34552})
    public void testDefaultStatus()
    {
        // Parameter values (User):
        String[] users = {AbstractChatTest.AGENT_ROLE_NAME, AbstractChatTest.CUSTOMER_ROLE_NAME};

        for (int i = 0; i < users.length; i++)
        {
            // Start FootPrints and login as an @User
            logIn(users[i]);

            // Click on the Top expand button to open the chat panel
            getChatEngineUI().expand();

            // Verify that "Available" is the default status
            Assert.assertTrue("Available".equals(getChatEngineUI().getChatStatus()), "Available is the default status");

            if (i < users.length - 1)
            {
                logOut();
            }
        }
    }

    @Test(groups = {TestGroups.MTC_NUMBER + 34535})
    public void testInvisibleToCustomers()
    {
        // Start FootPrints and login as a Customer
        logIn(AbstractChatTest.CUSTOMER_ROLE_NAME);

        // Go to Chat Panel | Chat main window - Status field
        getChatEngineUI().expand();

        // Verify that status "Invisible to Customers" is not available to Customers
        Assert.assertTrue(!getChatEngineUI().isChatStatusInList("Invisible to Customers"),
            "Status 'Invisible to Customers' is not available to Customers");
    }
}
