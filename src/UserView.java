import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserView extends JFrame {
	private User user;
	
	public UserView(User user) {
		this.setTitle(user.getUserId() + " - MiniTwitter");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new FlowLayout());
		
		this.user = user;
		
		init_View();
		
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
		JTextField userId = new JTextField(user.getUserId());
		userId.setEnabled(false);
		userId.setPreferredSize(new Dimension(175, 26));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		panel.add(userId, c);
		
		// ----------------------------------------------------------------------------------------
		// Follow User
		// ----------------------------------------------------------------------------------------
		JButton btn_followUser = new JButton("Follow User");
		btn_followUser.setPreferredSize(new Dimension(175, 26));
		btn_followUser.addActionListener((ActionEvent) -> {
			
		});
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		panel.add(btn_followUser, c);

		add(panel);
	}
}
