import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

// ------------------------------------------------------------------------------------------------
// AdminControlPanel implemented as a Singleton class so all updates made on the control panel
// are reflected on every AdminControlPanel window open.
// ------------------------------------------------------------------------------------------------
@SuppressWarnings("serial")
public class AdminControlPanel extends JFrame {

	private static AdminControlPanel instance = null;
	private JTree tree;
	private JScrollPane scroll;
	private UserGroup rootGroup;
	private String currentGroup;
	private String currentUser;

	// ********************************************************************************************
	// Constructor
	// ********************************************************************************************
	private AdminControlPanel() {
		this.setTitle("Mini Twitter");
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		rootGroup = new UserGroup("Root");
		currentGroup = "Root";

		// Initializers
		init_View();

		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	// ********************************************************************************************
	// Initializes the Admin Control Panel
	// ********************************************************************************************
	private void init_View() {
		JPanel panel = new JPanel();
		GridBagConstraints c = new GridBagConstraints();

		panel.setLayout(new GridBagLayout());

		c.insets = new Insets(5, 5, 5, 5);

		// ----------------------------------------------------------------------------------------
		// Tree View
		// ----------------------------------------------------------------------------------------
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		tree = new JTree(root); // Add root node to tree
		tree.setPreferredSize(new Dimension(250, 400));
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setSelectionRow(0); // Select root node
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener((TreeSelectionEvent) -> {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			//If node node is selected then return null
			if (node == null) {
				return;
			}
			
			//The remaining code gets the group name.
			if(!node.getAllowsChildren()) {
				currentUser = (String)node.getUserObject();
				node = (DefaultMutableTreeNode) node.getParent();
			} else {
				currentUser = null;
			}
			
			currentGroup = (String) node.getUserObject();
		});

		scroll = new JScrollPane(tree); // Add tree to a scroll pane
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 8;
		c.fill = GridBagConstraints.VERTICAL;
		panel.add(scroll, c);

		// ----------------------------------------------------------------------------------------
		// User Fields
		// ----------------------------------------------------------------------------------------
		JTextField userId = new JTextField("Amador");
		userId.setPreferredSize(new Dimension(175, 26));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		panel.add(userId, c);

		JButton btn_UserId = new JButton("Add User");
		btn_UserId.addActionListener((ActionEvent) -> {
			updateTree(new User(userId.getText()), (UserGroup) rootGroup.getUser(currentGroup, rootGroup));
		});

		c.gridx = 2;
		c.gridy = 0;
		panel.add(btn_UserId, c);

		// ----------------------------------------------------------------------------------------
		// Group Fields
		// ----------------------------------------------------------------------------------------
		JTextField groupId = new JTextField("Rivera");
		groupId.setPreferredSize(new Dimension(175, 26));
		c.gridx = 1;
		c.gridy = 1;
		panel.add(groupId, c);

		JButton btn_GroupId = new JButton("Add Group");
		btn_GroupId.addActionListener((ActionEvent) -> {
			updateTree(new UserGroup(groupId.getText()), (UserGroup) rootGroup.getUser(currentGroup, rootGroup));
		});

		c.gridx = 2;
		c.gridy = 1;
		panel.add(btn_GroupId, c);

		// ----------------------------------------------------------------------------------------
		// Open User Field Button
		// ----------------------------------------------------------------------------------------
		JButton btn_UserView = new JButton("Open User View");
		btn_UserView.addActionListener((ActionEvent) -> {
			if(currentUser != null) {
				new UserView(rootGroup.getUser(currentUser, rootGroup));
			}
		});
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		panel.add(btn_UserView, c);

		// ----------------------------------------------------------------------------------------
		// Show User Total Button
		// ----------------------------------------------------------------------------------------
		JButton btn_ShowUserTotal = new JButton("Show User Total");
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		panel.add(btn_ShowUserTotal, c);

		// ----------------------------------------------------------------------------------------
		// Show Group Total Button
		// ----------------------------------------------------------------------------------------
		JButton btn_ShowGroupTotal = new JButton("Show Group Total");
		c.gridx = 2;
		c.gridy = 3;
		panel.add(btn_ShowGroupTotal, c);

		// ----------------------------------------------------------------------------------------
		// Show Messages Total Button
		// ----------------------------------------------------------------------------------------
		JButton btn_ShowMsgTotal = new JButton("Show Message Total");
		c.gridx = 1;
		c.gridy = 7;
		c.gridheight = 1;
		panel.add(btn_ShowMsgTotal, c);

		// ----------------------------------------------------------------------------------------
		// Show Positive Percentage Button
		// ----------------------------------------------------------------------------------------
		JButton btn_ShowPositivePercentage = new JButton("Show Positive Percentage");
		c.gridx = 2;
		c.gridy = 7;
		panel.add(btn_ShowPositivePercentage, c);

		add(panel);
	}

	// ********************************************************************************************
	// Update Tree
	// ********************************************************************************************
	private void updateTree(User user, UserGroup group) {
		if(group != null) {
			group.addUser(user, rootGroup);
			DefaultTreeModel model = new DefaultTreeModel(updateModel(rootGroup));
			tree.setModel(model);
		}
	}
	
	// ********************************************************************************************
	// This returns a new nested TreeNode to create a new model.
	// ********************************************************************************************
	private DefaultMutableTreeNode updateModel(UserGroup root) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(root.getUserId());
		
		for (Map.Entry<String, User> group : root.getUserMap().entrySet()) {
			if (group.getValue() instanceof UserGroup) {
				DefaultMutableTreeNode newGroup = updateModel((UserGroup) group.getValue());
				node.add(newGroup);
			} else {
				DefaultMutableTreeNode user = new DefaultMutableTreeNode(group.getKey());
				user.setAllowsChildren(false);
				node.add(user);
			}
		}
		
		return node;
	}

	// ********************************************************************************************
	// Returns an instance of the AdminControl panel.
	// ********************************************************************************************
	public static AdminControlPanel getInstance() {
		
		if (instance == null) {
			instance = new AdminControlPanel();
		}
		return instance;
	}
}
