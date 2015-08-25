import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UserView extends JFrame {
	private User user;
	private UserGroup rootGroup;
	private List<UserView> userViews;
	private JList<String> followingList;
	private JList<String> newsFeedList;

	public UserView(User user, UserGroup rootGroup) {
		this.setTitle(user.getUserId() + " - MiniTwitter");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new FlowLayout());

		this.user = user;
		this.rootGroup = rootGroup;
		userViews = new ArrayList<UserView>();

		init_View();
		updateNewsFeedListView();
		updateFollowingListView();

		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void init_View() {
		JPanel panel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();

		panel.setLayout(new GridBagLayout());

		c.insets = new Insets(5, 5, 5, 5);

		// ----------------------------------------------------------------------------------------
		// User ID
		// ----------------------------------------------------------------------------------------
		JTextField userId = new JTextField("");
		userId.setPreferredSize(new Dimension(175, 26));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(userId, c);

		// ----------------------------------------------------------------------------------------
		// Follow User
		// ----------------------------------------------------------------------------------------
		JButton btn_followUser = new JButton("Follow User");
		btn_followUser.setPreferredSize(new Dimension(175, 26));
		btn_followUser.addActionListener((ActionEvent) -> {
			User followedUser = rootGroup.getUser(userId.getText(), rootGroup);

			if (followedUser != null) {
				user.updateFollowingList(followedUser);
				updateFollowingListView();
			}
		});
		c.gridx = 1;
		c.gridy = 0;
		panel.add(btn_followUser, c);

		// ----------------------------------------------------------------------------------------
		// Following List View
		// ----------------------------------------------------------------------------------------
		followingList = new JList<String>();
		JScrollPane scroll = new JScrollPane(followingList);

		followingList.setPreferredSize(new Dimension(300, 250));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;

		panel.add(scroll, c);

		// ----------------------------------------------------------------------------------------
		// Tweet Message
		// ----------------------------------------------------------------------------------------
		JTextField message = new JTextField("Enter Message");
		message.setPreferredSize(new Dimension(175, 26));
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(message, c);

		// ----------------------------------------------------------------------------------------
		// Post Tweet Button
		// ----------------------------------------------------------------------------------------
		JButton btn_postMessage = new JButton("Post Message");
		btn_postMessage.setPreferredSize(new Dimension(175, 26));
		btn_postMessage.addActionListener((ActionEvent) -> {
			user.postTweet(message.getText());
			updateNewsFeedListView();
			
			for(UserView v : userViews) {
				v.updateNewsFeedListView();
			}
		});
		c.gridx = 1;
		c.gridy = 2;
		panel.add(btn_postMessage, c);

		// ----------------------------------------------------------------------------------------
		// Messages List View
		// ----------------------------------------------------------------------------------------
		String[] news = user.getTweets().toArray(new String[user.getTweets().size()]);
		newsFeedList = new JList<String>(news);
		JScrollPane scroll_msgs = new JScrollPane(newsFeedList);

		newsFeedList.setPreferredSize(new Dimension(300, 250));
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;

		panel.add(scroll_msgs, c);

		add(panel);
	}

	public void add(UserView view) {
		this.userViews.add(view);
	}
	
	public void updateFollowingListView() {
		DefaultListModel<String> model = new DefaultListModel<String>();

		for (Map.Entry<String, Observer> followed : user.getFollowedUsers().entrySet()) {
			model.addElement(followed.getKey());
		}
		followingList.setModel(model);
	}

	public void updateNewsFeedListView() {
		DefaultListModel<String> model = new DefaultListModel<String>();

		for (String tweet : user.getTweets()) {
			model.addElement(tweet);
		}
		newsFeedList.setModel(model);
	}
}
