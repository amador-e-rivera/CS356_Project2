import javax.swing.SwingUtilities;

public class Driver {

	public static void main(String[] args) {
		UserGroup root = new UserGroup("root");
		UserGroup group2 = new UserGroup("CS356");
		UserGroup group3 = new UserGroup("CS408");
		
		User user1 = new User("Amador");
		User user2 = new User("Linh");
		User user3 = new User("Giang");
		User user4 = new User("Amber");
		
		root.addUser(user1, root);
		root.addUser(user2, root);
		group2.addUser(user3, root);
		group3.addUser(user4, root);
		
		root.addUser(group2, root);
		group2.addUser(group3, root);

		if (root.userExists("Test", root)) {
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
