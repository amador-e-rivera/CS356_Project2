import java.util.Map;

// ********************************************************************************************
// This Visitor class determines the number of users, groups, messages and the percentage
// of positive messages.
// ********************************************************************************************
public class UserVisitor implements IUserVisitor {

	int[] userInfo;
	int numberOfGroups;
	
	public UserVisitor() {
		userInfo = new int[3];
		numberOfGroups = 1; //Always count first group
	}
	
	@Override
	public int getUserInfo(User user) {
		return user.getNewsFeed().size();
	}

	// Uses recursion to iterate through all of the groups and users
	@Override
	public void getGroupInfo(UserGroup group) {
		for(Map.Entry<String, User> user : group.getUserMap().entrySet()) {
			if(user.getValue() instanceof UserGroup) {
				numberOfGroups++;
				this.getGroupInfo((UserGroup)user.getValue());
			} else {
				setUserInfo(user);
			}
		}
	}
	
	private void setUserInfo(Map.Entry<String, User> user) {
		userInfo[0]++; // Keeps track on number of users	
		
		for(String message : user.getValue().getNewsFeed()) {
			String msg = message.toLowerCase();
			
			// Increments the number of messages only if the message is from current user
			if(msg.contains(user.getValue().getUserId().toLowerCase())) {
				userInfo[1]++; // Keeps track on number of messages
				
				//Determines if the message has a positive word
				if((msg.contains("good") || msg.contains("excellent") || msg.contains("great"))) {
					userInfo[2]++; // Keeps number of messages that are positive
				}
			}
		}
	}
	
	public int getNumberOfUsers() {
		return userInfo[0];
	}

	public int getNumberOfGroups() {
		return numberOfGroups;
	}
	
	public int getNumberOfMessages() {
		return userInfo[1];
	}
	
	public double getPositivePercentage() {
		if (userInfo[2] == 0) {
			return 0;
		}
		return Math.round((((userInfo[2] * 1.0) / userInfo[1]) * 100) * 100) / 100.0;
	}
	
}
