import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

public class UserGroup extends User {

	Map<String, User> users;

	public UserGroup(String id) {
		super(id);
		users = new HashMap<String, User>();
	}

	public void addUser(User user) {
		String id = user.getUserId();

		if (!users.containsKey(id)) {
			users.put(id, user);
		} else {
			System.out.println("User id " + id + " already exists!");
		}
	}

	public Map<String, User> getUserGroups() {
		return users;
	}

	public boolean findUser(String id, UserGroup userGroup) {
		if (userGroup.getUserId().equals(id)) {
			return true;
		}

		if (!userGroup.getUserGroups().isEmpty()) {
			for (Map.Entry<String, User> group : userGroup.getUserGroups().entrySet()) {
				if (group.getValue() instanceof UserGroup && findUser(id, (UserGroup) group.getValue())) {
					return true;
				} else if (group.getKey().equals(id)) {
					return true;
				}
			}
		}

		return false;
	}
}
