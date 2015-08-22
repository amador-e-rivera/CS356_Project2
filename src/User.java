import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Observer{
	private String id;
	private Map<String, Observer> followers;
	private Map<String, Observer> following;
	private List<String> tweets;
	
	public User(String id) {
		this.id = id;
		followers = new HashMap<String, Observer>();
		following = new HashMap<String, Observer>();
		tweets = new ArrayList<String>();
	}
	
	//---------------------------------------------------------------------------------------------
	// Getters
	//---------------------------------------------------------------------------------------------
	public String getUserId() {
		return id;
	}
	
	public Map<String, Observer> getFollowers() {
		return followers;
	}
	
	public Map<String, Observer> getFollowedUsers() {
		return following;
	}
	
	public List<String> getTweets() {
		return tweets;
	}
	
	//---------------------------------------------------------------------------------------------
	// Setters
	//---------------------------------------------------------------------------------------------
	public void addFollower(User user) {
		if(!followers.containsKey(user.getUserId())) {
			followers.put(user.getUserId(), user);
		}
	}
	
	public void followUser(User user) {
		following.put(user.getUserId(), user);
	}
	
	public void postTweet(String userId, String tweet) {
		tweets.add(userId + ": " + tweet);
		notifyObservers(userId);
	}
	
	//---------------------------------------------------------------------------------------------
	// Observer Functions
	//---------------------------------------------------------------------------------------------
	public void notifyObservers(String userId) {
		for(Map.Entry<String, Observer> observer : followers.entrySet()) {
			observer.getValue().update(userId, tweets.get(tweets.size() - 1));
		}
	}

	@Override
	public void update(String userId, String tweet) {
		if(followers.containsKey(userId) || userId.equals(id)) {
			tweets.add(tweet);
		}
	}
}
