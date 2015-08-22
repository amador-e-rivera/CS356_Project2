import javax.swing.SwingUtilities;

public class Driver {

	public static void main(String[] args) {
		UserGroup group1 = new UserGroup("root");
		UserGroup group2 = new UserGroup("CS356");
		UserGroup group3 = new UserGroup("CS408");
		
		User user1 = new User("Amador");
		User user2 = new User("Linh");
		User user3 = new User("Giang");
		User user4 = new User("Amber");
		
		group1.addUser(user1);
		group1.addUser(user2);
		group2.addUser(user3);
		group3.addUser(user4);
		
		group1.addUser(group2);
		group2.addUser(group3);

		if (group1.findUser("Amber", group1)) {
			System.out.println("User Exists");
		} else {
			System.out.println("User Not Found");
		}
		
		
		user1.addFollower(user2);
		user1.addFollower(user3);
		user2.addFollower(user1);
		user2.addFollower(user3);
		//user3.addFollower(user2);
		user3.addFollower(user1);
		
		user3.postTweet("Amador", "Hello");
		user2.postTweet("Giang", "Linh says hi");
		System.out.println();
		
		SwingUtilities.invokeLater(() -> {
			AdminControlPanel.getInstance();
		});
	}

}
