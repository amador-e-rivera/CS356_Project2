import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

public class UserGroup extends User implements IUserComponent, IUserVisitable {

	private Map<String, User> users;

	// ********************************************************************************************
	// Constructor
	// ********************************************************************************************
	public UserGroup(String id) {
		super(id);
		users = new HashMap<String, User>();
	}

	// ********************************************************************************************
	// Add user to Map. The rootGroup parameter should be the absolute root of the tree.
	// ********************************************************************************************
	public void addUser(User user, UserGroup rootGroup) {
		String id = user.getUserId();

		if (!userExists(id, rootGroup)) {
			users.put(id, user);
		} else {
			System.out.println("User id " + id + " already exists!");
		}
	}

	// ********************************************************************************************
	// Returns user map
	// ********************************************************************************************
	public Map<String, User> getUserMap() {
		return users;
	}

	// ********************************************************************************************
	// Determines if a user exists by using recursion
	// ********************************************************************************************
	public boolean userExists(String id, UserGroup userGroup) {
		// Returns true if current group matches id or if a user in its Map matches the id
		if (userGroup.getUserId().equals(id) || userGroup.getUserMap().containsKey(id)) {
			return true;
		}

		// For each group in userGroup do a recursive search for the id
		for (Map.Entry<String, User> group : userGroup.getUserMap().entrySet()) {
			if (group.getValue() instanceof UserGroup && userExists(id, (UserGroup) group.getValue())) {
				return true;
			}
		}
		return false; // User not found
	}
	
	// ********************************************************************************************
	// Returns User object for User matching the id by using recursion
	// ********************************************************************************************
	public User getUser(String id, UserGroup userGroup) {
		// Returns true if current group matches id or if a user in its Map matches the id
		if (userGroup.getUserId().equals(id)) {
			return userGroup;
		} else if (userGroup.getUserMap().containsKey(id)) {
			return userGroup.getUserMap().get(id);
		}

		// For each group in userGroup do a recursive search for the id
		for (Map.Entry<String, User> group : userGroup.getUserMap().entrySet()) {
			if (group.getValue() instanceof UserGroup && userExists(id, (UserGroup) group.getValue())) {
				return getUser(id, (UserGroup)group.getValue());
			}
		}

		return null; // id not found
	}
	
	// ----------------------------------------------------------------------------------------
	// Composite Pattern Method - Returns a nested tree node for this user group
	// ----------------------------------------------------------------------------------------
	@Override
	public DefaultMutableTreeNode getUserTreeNode() {
		DefaultMutableTreeNode group = new DefaultMutableTreeNode(super.getUserId());
		
		for(Map.Entry<String, User> user : users.entrySet()) {
			group.add(user.getValue().getUserTreeNode());
		}
		
		return group;
	}
	
	// ----------------------------------------------------------------------------------------
	// Visitor Pattern Method
	// ----------------------------------------------------------------------------------------
	@Override
	public void accept(IUserVisitor visitor) {
		visitor.getGroupInfo(this);
	}
}
