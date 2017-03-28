
package com.xxxsoftware.automatedtesting.tests.rule;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.xxxsoftware.automatedtesting.framework.datamodel.rule.Rule;
import com.xxxsoftware.automatedtesting.framework.datamodel.runtimeitem.data.ws.WsTicketData;
import com.xxxsoftware.automatedtesting.tests.TestGroups;

@Test
public class EnabledDisabledRuleTest extends AbstractRuleTest
{
    //Test data
    private Rule _ruleActive;
    private Rule _ruleInactive;
    private WsTicketData _issue1;
    private WsTicketData _issue2;
    private WsTicketData _issue3;

    @BeforeClass(groups = TestGroups.PREPARATION)
    public void init()
    {

        _ruleActive = getTestEnvironment().getRuleProvider().getRule();
        _ruleInactive = getTestEnvironment().getRuleProvider().getRule();
        _ruleInactive.setActive(false);

        _issue1 = getTestEnvironment().getWorkspaceRunTimeItemProvider().newTimeStampedUsingBuiltInDefinition();
        _issue1.setTitle(_issue1.getTitle() + " qwerty");

        _issue2 = getTestEnvironment().getWorkspaceRunTimeItemProvider().newTimeStampedUsingBuiltInDefinition();
        _issue2.setTitle(_issue2.getTitle() + " qwerty");

        _issue3 = getTestEnvironment().getWorkspaceRunTimeItemProvider().newTimeStampedUsingBuiltInDefinition();
        _issue3.setTitle(_issue3.getTitle() + " qwerty");

    }

    @Test(groups = {TestGroups.RULES})
    public void testActiveRule()
    {
        createRule(_ruleActive);

        getWorkspacesUIHelper().publish(getWorkspace(), getDefaultTestSettings());

        getRuntimeIssuesUIHelper().createIssue(_issue1, getDefaultTestSettings());

        getRulesUIHelper().view(_ruleActive, getDefaultTestSettings());
        Assert.assertTrue(getRuleCreationUI().isRuleActive(), "Rule isn't active");

        getClientBrowser().closeAllTabs();
    }

    @Test(groups = {TestGroups.RULES})
    public void testNotActiveRule()
    {
        createRule(_ruleInactive);

        getWorkspacesUIHelper().publish(getWorkspace(), getDefaultTestSettings());

        getRuntimeIssuesUIHelper().createIssue(_issue2, getDefaultTestSettings());

        getRulesUIHelper().view(_ruleInactive, getDefaultTestSettings());

        Assert.assertFalse(getRuleCreationUI().isRuleActive(), "Rule is active");
        getClientBrowser().closeAllTabs();
    }

    @Test(groups = {TestGroups.RULES, TestGroups.MTC_NUMBER + 25294}, dependsOnMethods = {"testActiveRule"})
    public void testActiveToNotActiveRule()
    {
        _ruleActive.setOnUpdate(false);
        _ruleActive.setActive(false);

        getRulesUIHelper().edit(_ruleActive, getDefaultTestSettings());

        getWorkspacesUIHelper().publish(getWorkspace(), getDefaultTestSettings());

        getRuntimeIssuesUIHelper().createIssue(_issue3, getDefaultTestSettings());

        int impact = getRulesUIHelper().getRuleImpact(_ruleActive);
        Assert.assertEquals(impact, 2, "A rule isn't triggered as expected");

        getRulesUIHelper().view(_ruleActive, getDefaultTestSettings());
        Assert.assertFalse(getRuleCreationUI().isRuleActive(), "Rule is active");

        getClientBrowser().closeAllTabs();
    }

}
