
package com.xxxsoftware.automatedtesting.tests.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.xxxsoftware.automatedtesting.framework.datamodel.container.data.ws.WorkspaceData;
import com.xxxsoftware.automatedtesting.framework.datamodel.container.ui.draft.ws.DraftWorkspacesUIHelper;
import com.xxxsoftware.automatedtesting.framework.datamodel.container.ui.draft.ws.WorkspaceEditingUI;
import com.xxxsoftware.automatedtesting.framework.datamodel.itemdefinition.data.ItemDefinitionData;
import com.xxxsoftware.automatedtesting.framework.datamodel.usermanagement.role.Role;
import com.xxxsoftware.automatedtesting.framework.datamodel.usermanagement.role.data.Permissions;
import com.xxxsoftware.automatedtesting.framework.datamodel.usermanagement.team.Team;
import com.xxxsoftware.automatedtesting.framework.datamodel.usermanagement.team.TeamManagementUI;
import com.xxxsoftware.automatedtesting.framework.datamodel.usermanagement.team.TeamsUIHelper;
import com.xxxsoftware.automatedtesting.framework.datamodel.usermanagement.user.User;
import com.xxxsoftware.automatedtesting.framework.datamodel.usermanagement.user.UserManagementUI;
import com.xxxsoftware.automatedtesting.framework.datamodel.usermanagement.user.UsersUIHelper;
import com.xxxsoftware.automatedtesting.tests.AbstractApplicationTest;
import com.xxxsoftware.automatedtesting.tests.TestGroups;

public class AbstractUserManagementTest extends AbstractApplicationTest
{
    //Test data
    private Map<String, User> _users;
    private Map<String, Team> _teams;
    private Map<String, WorkspaceData> _workspaces;
    private Map<String, Role> _roles;

    //Helpers
    private TeamsUIHelper _teamManagementHelper;
    private UsersUIHelper _userManagementHelper;
    private DraftWorkspacesUIHelper _designTimeWorkspacesUIHelper;

    @BeforeClass(groups = TestGroups.PREPARATION)
    public void prepn()
    {
        //instantiate helpers
        _teamManagementHelper = getMainUI().getTeamsUIHelper();
        _userManagementHelper = getMainUI().getUsersUIHelper();
        _designTimeWorkspacesUIHelper = getMainUI().getDraftWorkspacesUIHelper();

        //instantiate test data objects
        _users = new HashMap<String, User>();
        _teams = new HashMap<String, Team>();
        _workspaces = new HashMap<String, WorkspaceData>();
        _roles = new HashMap<String, Role>();

        createWorkspaces(1);
    }

    /**
     * Gets UsersUIHelper.
     * 
     * @return UsersUIHelper
     */
    public UsersUIHelper getUsersUIHelper()
    {
        return _userManagementHelper;
    }



    /**
     * @return the users
     */
    public Map<String, User> getUsers()
    {
        return _users;
    }



    /**
     * @return the teams
     */
    public Map<String, Team> getTeams()
    {
        return _teams;
    }



    /**
     * @return the workspaces
     */
    public Map<String, WorkspaceData> getWorkspaces()
    {
        return _workspaces;
    }



    /**
     * @return the roles
     */
    public Map<String, Role> getRoles()
    {
        return _roles;
    }

    /**
     * Creates the workspace with index.
     * 
     * @param index
     *        the index
     */
    protected void createWorkspaceWithIndex(int index)
    {
        WorkspaceData suiteWorkspace = getSuiteWorkspace();
        if (suiteWorkspace == null)
        {
            WorkspaceData testWorkspace = getTestEnvironment().getWorkspaceProvider()
                .newContainerTimeStampBasicProperties();
            _designTimeWorkspacesUIHelper.addV(getDefaultTestSettings(), testWorkspace);
            _workspaces.put(testWorkspace.getName(), testWorkspace);
        }
        else
        {
            _workspaces.put(suiteWorkspace.getName(), suiteWorkspace);
        }
    }

    /**
     * Creates the workspaces.
     * 
     * @param count
     *        the count
     */
    protected void createWorkspaces(int count)
    {
        for (int i = 1; i <= count; i++)
        {
            createWorkspaceWithIndex(i);
            /*Workspace testWorkspace = getTestEnvironment().getWorkspaceProvider().getTimeStampedWorkspace();
            _workspaceHelper.createSavePublishWorkspace(testWorkspace);
            _workspaces.put("workspace" + i, testWorkspace);*/
        }
    }

    /**
     * Creates the agent with index.
     * 
     * @param index
     *        the index
     */
    protected void createAgentWithIndex(int index)
    {
        Map<String, WorkspaceData> workspaces = new HashMap<String, WorkspaceData>();
        for (String key : _workspaces.keySet())
        {
            workspaces.put(key, _workspaces.get(key));
        }

        User randomAgent = _userManagementHelper.addRandomAgent(workspaces, getDefaultTestSettings());
        _users.put("agent" + index, randomAgent);
    }

    /**
     * Creates the agent with index, that would be assigned only with the
     * specified workspace.
     * 
     * @param index
     *        the index
     * @param assignedWorkspace
     *        the assigned workspace
     */
    protected void createAgentWithIndex(int index, WorkspaceData assignedWorkspace)
    {
        Map<String, WorkspaceData> workspaces = new HashMap<String, WorkspaceData>();
        workspaces.put("ws1", assignedWorkspace);

        User randomAgent = _userManagementHelper.addRandomAgent(workspaces, getDefaultTestSettings());
        _users.put("agent" + index, randomAgent);
    }

    /**
     * Creates the agents.
     * 
     * @param count
     *        the count
     */
    protected void createAgents(int count)
    {
        for (int i = 1; i <= count; i++)
        {
            createAgentWithIndex(i);
        }
    }

    /**
     * Creates the customers.
     * 
     * @param count
     *        the count
     */
    protected void createCustomers(int count)
    {
        Map<String, WorkspaceData> workspaces = new HashMap<String, WorkspaceData>();
        for (String key : _workspaces.keySet())
        {
            workspaces.put(key, _workspaces.get(key));
        }

        for (int i = 1; i <= count; i++)
        {
            User randomCustomer = _userManagementHelper.addRandomCustomer(workspaces, getDefaultTestSettings());
            _users.put("customer" + i, randomCustomer);
        }
    }

    @AfterClass(groups = TestGroups.PREPARATION)
    public void deleteTestData()
    {
        if (getSuiteWorkspace() == null)
        {
            try
            {
                getClientBrowser().closeAllTabs();

                //delete all teams
                if (_teams != null)
                {
                    if (!_teams.isEmpty())
                    {
                        TeamManagementUI teamManagementUI = _teamManagementHelper.getTeamManagementUI();
                        teamManagementUI.open(getDefaultTestSettings());
                        teamManagementUI.deleteTeams(_teams);
                        teamManagementUI.close(getDefaultTestSettings());
                    }
                }

                //delete all users
                if (_users != null)
                {
                    if (!_users.isEmpty())
                    {
                        UserManagementUI usermangementUI = _userManagementHelper.getUserManagementUI();
                        usermangementUI.open(getDefaultTestSettings());
                        usermangementUI.deleteUsers(_users);
                        usermangementUI.close(getDefaultTestSettings());
                    }
                }

                if (getSuiteWorkspace() == null)
                {
                    //delete all workspaces
                    if (_workspaces != null)
                    {
                        if (!_workspaces.isEmpty())
                        {
                            List<WorkspaceData> workspacesList = new ArrayList<WorkspaceData>(_workspaces.values());
                            _designTimeWorkspacesUIHelper.deleteWorkspaces(workspacesList, getDefaultTestSettings());
                        }
                    }
                }
            }
            catch (Exception ex) //Hides all errors during test data deletion process.
            {
                System.out.println(ex.toString());
                // TODO-SZ: Add logging of errors during deletion of test data. 
            }
        }
    }

    /**
     * Adds the specified number of items to the given workspace.
     * 
     * @param inWorkspace
     *        the in workspace
     * @param inItemsCount
     *        the in items count
     * @return Names list of new created/added items.
     */
    protected List<String> addItemsToWorkspace(WorkspaceData inWorkspace, int inItemsCount)
    {
        getMainUI().getParentClientBrowser().closeAllTabs();

        List<String> addedItemNames = new ArrayList<String>();
        String commonItemName = "Workspace Item %s " + System.currentTimeMillis();

        for (int i = 0; i < inItemsCount; i++)
        {
            WorkspaceEditingUI editingUI = getMainUI().getDraftWorkspacesUIHelper()
                .newEditingUI(inWorkspace);

            ItemDefinitionData itemDefinition = getTestEnvironment().getWorkspaceItemDefinitionProvider()
                .newItemDefinitionTimeStampBasicProperties(null);

            itemDefinition.setName(String.format(commonItemName, i));
            itemDefinition.setItemDescription(String.format(commonItemName, i));
            itemDefinition.setSingularName(String.format(commonItemName, i));
            itemDefinition.setPluralName(String.format(commonItemName, i));

            editingUI.getItemDefinitionsUIHelper().addV(getDefaultTestSettings(), itemDefinition);

            addedItemNames.add(itemDefinition.getName());
        }

        getMainUI().getDraftWorkspacesUIHelper().publish(inWorkspace, getDefaultTestSettings());

        /*WorkspaceEditingUI workspaceEditing = getMainUI().getWorkspacesUIHelper().edit(inWorkspace);
        workspaceEditing.open(getDefaultTestSettings());
        workspaceEditing.viewItemSection();
        workspaceEditing.switchToGridViewItems();
        getClientBrowser().closeInactiveTabs();*/

        return addedItemNames;
    }

    /**
     * Creates the agent role with permissions.
     * 
     * @param permToCreate
     *        the perm to create
     * @return a Role object.
     */
    protected Role createAgentRoleWithPerm(Permissions permToCreate)
    {
        return getMainUI().getRolesUIHelper().createAgentRoleWithPerm(permToCreate, getDefaultTestSettings());
    }

    /**
     * Creates the user object with role.
     * 
     * @param inRole
     *        the in role
     * @param workspaces
     *        the workspaces
     * @return a User object.
     */
    protected User createUserWithRole(Role inRole, Map<String, WorkspaceData> workspaces)
    {
        return getTestEnvironment().getUserManagementDataProvider().getUserWithRole(inRole, workspaces);
    }

}
