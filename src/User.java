import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Observer{
	private String id;
	private Map<String, Observer> followers;
	private Map<String, Observer> following;
	private List<String> tweets;
	private UserView view;
	
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
	
	public void userView(UserGroup group) {
		view = new UserView(this, group);
	}
	
	//---------------------------------------------------------------------------------------------
	// Setters
	//---------------------------------------------------------------------------------------------
	public void updateFollowersList(User user) {
		if(!followers.containsKey(user.getUserId())) {
			followers.put(user.getUserId(), user);
		}
	}
	
	public void updateFollowingList(User followedUser) {
		following.put(followedUser.getUserId(), followedUser);
		followedUser.updateFollowersList(this);
	}
	
	public void postTweet(String tweet) {
		tweets.add(id + ": " + tweet);
		notifyObservers(id);
	}
	
	//---------------------------------------------------------------------------------------------
	// Observer Functions
	//---------------------------------------------------------------------------------------------
	public void notifyObservers(String userId) {
		for(Map.Entry<String, Observer> observer : followers.entrySet()) {
			User user = (User)observer.getValue();
			user.update(userId, tweets.get(tweets.size() - 1));
		}
	}

	@Override
	public void update(String userId, String tweet) {
		if(following.containsKey(userId) || userId.equals(id)) {
			tweets.add(tweet);
			
			if(view != null) {
				view.updateFollowingListView();
				view.updateNewsFeedListView();
			}
		}
	}
}
