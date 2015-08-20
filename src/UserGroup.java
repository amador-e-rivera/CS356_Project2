import java.util.HashMap;
import java.util.Map;

public class UserGroup extends User {

	Map<String, User> users;
	
	public UserGroup(String id) {
		super(id);
		users = new HashMap<String, User>();
	}
	
	public void addUser(User user) {
		String id = user.getUserId();
		
		if(!users.containsKey(id)) {
			users.put(id, user);
		} else {
			System.out.println("User id " + id + " already exists!");
		}
	}
	
	public Map<String, User> getUserGroups() {
		return users;
	}
}
