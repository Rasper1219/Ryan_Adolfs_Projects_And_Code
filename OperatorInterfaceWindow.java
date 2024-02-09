package chocAn;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Color;
import javax.swing.JTextField;

public class OperatorInterfaceWindow extends JFrame {

	private JPanel contentPane;
	private JTextField opInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperatorInterfaceWindow frame = new OperatorInterfaceWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OperatorInterfaceWindow(MemberRecords[] member, ProviderRecords[] provider, int memSize, int proSize, int mesFlag) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton opExitBttn = new JButton("Exit");
		opExitBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ExitWindow exit = new ExitWindow();
				exit.setVisible(true);
			}
		});
		opExitBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		opExitBttn.setBounds(10, 231, 89, 23);
		contentPane.add(opExitBttn);
		
		JLabel opTitle = DefaultComponentFactory.getInstance().createTitle("Operator Menu");
		opTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		opTitle.setForeground(new Color(204, 0, 51));
		opTitle.setBounds(143, 11, 166, 23);
		contentPane.add(opTitle);
		
		JLabel addLabel = DefaultComponentFactory.getInstance().createLabel("1 - Add");
		addLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		addLabel.setBounds(182, 76, 90, 14);
		contentPane.add(addLabel);
		
		JLabel deleteLabel = DefaultComponentFactory.getInstance().createLabel("2 - Delete");
		deleteLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		deleteLabel.setBounds(182, 101, 90, 14);
		contentPane.add(deleteLabel);
		
		JLabel editLabel = DefaultComponentFactory.getInstance().createLabel("3 - Edit");
		editLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		editLabel.setBounds(182, 126, 90, 14);
		contentPane.add(editLabel);
		
		JLabel optionTitle = DefaultComponentFactory.getInstance().createTitle("Options:");
		optionTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		optionTitle.setForeground(new Color(0, 0, 0));
		optionTitle.setBounds(168, 44, 104, 31);
		contentPane.add(optionTitle);
		
		opInput = new JTextField();
		opInput.setFont(new Font("Tahoma", Font.PLAIN, 11));
		opInput.setBounds(264, 153, 45, 20);
		contentPane.add(opInput);
		opInput.setColumns(10);
		
		JButton updateMemBttn = new JButton("Update Member");
		updateMemBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = Integer.parseInt(opInput.getText());
				contentPane.setVisible(false);
				dispose();
				OperatorUpdateWindow update = new OperatorUpdateWindow(member, provider, memSize, proSize, action, 1);
				update.setVisible(true);
			}
		});
		updateMemBttn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		updateMemBttn.setBounds(76, 183, 133, 23);
		contentPane.add(updateMemBttn);
		
		JButton updateProvBttn = new JButton("Update Provider");
		updateProvBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = Integer.parseInt(opInput.getText());
				contentPane.setVisible(false);
				dispose();
				OperatorUpdateWindow update = new OperatorUpdateWindow(member, provider, memSize, proSize, action, 2);
				update.setVisible(true);
			}
		});
		updateProvBttn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		updateProvBttn.setBounds(230, 184, 133, 23);
		contentPane.add(updateProvBttn);
		
		JLabel numLbl = DefaultComponentFactory.getInstance().createLabel("Type in Number of Choice:");
		numLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		numLbl.setBounds(88, 155, 166, 14);
		contentPane.add(numLbl);
		
		JLabel successLbl = new JLabel("");
		successLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		successLbl.setBounds(143, 217, 166, 28);
		contentPane.add(successLbl);
		if (mesFlag == 1) {
			successLbl.setText("Update was successful.");
		} else if (mesFlag ==2) {
			successLbl.setText("ID not found.");
		} else {
			successLbl.setText("");
		}
	}
	
	public OperatorInterfaceWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton opExitBttn = new JButton("Exit");
		opExitBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ExitWindow exit = new ExitWindow();
				exit.setVisible(true);
			}
		});
		opExitBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		opExitBttn.setBounds(10, 231, 89, 23);
		contentPane.add(opExitBttn);
		
		JLabel opTitle = DefaultComponentFactory.getInstance().createTitle("Operator Menu");
		opTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		opTitle.setForeground(new Color(204, 0, 51));
		opTitle.setBounds(143, 11, 166, 23);
		contentPane.add(opTitle);
		
		JLabel addLabel = DefaultComponentFactory.getInstance().createLabel("1 - Add");
		addLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		addLabel.setBounds(182, 76, 90, 14);
		contentPane.add(addLabel);
		
		JLabel deleteLabel = DefaultComponentFactory.getInstance().createLabel("2 - Delete");
		deleteLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		deleteLabel.setBounds(182, 101, 90, 14);
		contentPane.add(deleteLabel);
		
		JLabel editLabel = DefaultComponentFactory.getInstance().createLabel("3 - Edit");
		editLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		editLabel.setBounds(182, 126, 90, 14);
		contentPane.add(editLabel);
		
		JLabel optionTitle = DefaultComponentFactory.getInstance().createTitle("Options:");
		optionTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		optionTitle.setForeground(new Color(0, 0, 0));
		optionTitle.setBounds(168, 44, 104, 31);
		contentPane.add(optionTitle);
		
		opInput = new JTextField();
		opInput.setFont(new Font("Tahoma", Font.PLAIN, 11));
		opInput.setBounds(264, 153, 45, 20);
		contentPane.add(opInput);
		opInput.setColumns(10);
		
		JButton updateMemBttn = new JButton("Update Member");
		updateMemBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		updateMemBttn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		updateMemBttn.setBounds(76, 183, 133, 23);
		contentPane.add(updateMemBttn);
		
		JButton updateProvBttn = new JButton("Update Provider");
		updateProvBttn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		updateProvBttn.setBounds(230, 184, 133, 23);
		contentPane.add(updateProvBttn);
		
		JLabel numLbl = DefaultComponentFactory.getInstance().createLabel("Type in Number of Choice:");
		numLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		numLbl.setBounds(88, 155, 166, 14);
		contentPane.add(numLbl);
		
		JLabel successLbl = new JLabel("");
		successLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		successLbl.setBounds(143, 217, 166, 28);
		contentPane.add(successLbl);
	}
}
