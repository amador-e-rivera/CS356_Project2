import javax.swing.SwingUtilities;

public class Driver {

	public static void main(String[] args) {
		User user1 = new User("Amador");
		User user2 = new User("Linh");
		User user3 = new User("Giang");
		
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
