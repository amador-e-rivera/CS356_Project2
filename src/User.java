import java.util.HashMap;
import java.util.Map;

public class User {
	private String id;
	private Map<String, User> followers;
	private Map<String, User> following;
	
	public User(String id) {
		this.id = id;
		followers = new HashMap<String, User>();
		following = new HashMap<String, User>();
	}
	
	public void addFollower(String id) {
		followers.put(id, new User(id));
	}
	
	public void followUser(String id) {
		following.put(id, new User(id));
	}
	
	public String getUserId() {
		return id;
	}
	
	public Map<String, User> getFollowers() {
		return followers;
	}
	
	public Map<String, User> getFollowedUsers() {
		return following;
	}
}
