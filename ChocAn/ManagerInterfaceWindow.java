package chocAn;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManagerInterfaceWindow extends JFrame {

	private JPanel contentPane;
	private JTextField reportInput;
	private JTextField idInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerInterfaceWindow frame = new ManagerInterfaceWindow();
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
	public ManagerInterfaceWindow(MemberRecords[] member, ProviderRecords[] provider, int memSize, int proSize, ServiceRecord[] service, int servSize) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel reportLabel = DefaultComponentFactory.getInstance().createTitle("Choose Which Report to Generate:");
		reportLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		reportLabel.setBounds(69, 37, 319, 25);
		contentPane.add(reportLabel);
		
		JLabel prReportLabel = DefaultComponentFactory.getInstance().createLabel("1 - Provider Report");
		prReportLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		prReportLabel.setBounds(164, 73, 127, 14);
		contentPane.add(prReportLabel);
		
		JLabel managerTitle = DefaultComponentFactory.getInstance().createTitle("Manager Menu");
		managerTitle.setForeground(new Color(255, 0, 51));
		managerTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		managerTitle.setBounds(147, 11, 175, 25);
		contentPane.add(managerTitle);
		
		JLabel memReportLabel = DefaultComponentFactory.getInstance().createLabel("2 - Member Report");
		memReportLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		memReportLabel.setBounds(164, 98, 139, 14);
		contentPane.add(memReportLabel);
		
		JLabel sumReportLabel = DefaultComponentFactory.getInstance().createLabel("3 - Summary Report");
		sumReportLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sumReportLabel.setBounds(164, 123, 139, 20);
		contentPane.add(sumReportLabel);
		
		JLabel etfReportLabel = DefaultComponentFactory.getInstance().createLabel("4 - ETF Report");
		etfReportLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		etfReportLabel.setBounds(164, 148, 127, 14);
		contentPane.add(etfReportLabel);
		
		reportInput = new JTextField();
		reportInput.setBounds(191, 173, 50, 20);
		contentPane.add(reportInput);
		reportInput.setColumns(10);
		
		idInput = new JTextField();
		idInput.setBounds(191, 204, 50, 20);
		contentPane.add(idInput);
		idInput.setColumns(10);
		
		JLabel generateDisplay = new JLabel("");
		generateDisplay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		generateDisplay.setBounds(147, 237, 127, 14);
		contentPane.add(generateDisplay);
		
		JButton managerBttn = new JButton("Confirm Answer");
		managerBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerInterface man_interface = new ManagerInterface();
				String report_choice = reportInput.getText();
				int reportNum = Integer.parseInt(reportInput.getText());
				int id = 0;
				if (reportNum == 1 || reportNum == 2 ) {
					id = Integer.parseInt(idInput.getText());
				}
				for (int i=0; i<proSize; i++) {
					System.out.println(provider[i].providerRecord.getName());
				}
				man_interface.requestReport(report_choice, member, provider, memSize, proSize, service, servSize, id);
				generateDisplay.setText("Report Generated.");
			}
		});
		managerBttn.setBounds(251, 175, 155, 17);
		contentPane.add(managerBttn);
		
		JButton manExit = new JButton("Exit");
		manExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ExitWindow exit = new ExitWindow();
				exit.setVisible(true);
			}
		});
		manExit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		manExit.setBounds(10, 231, 89, 23);
		contentPane.add(manExit);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Type in Number of Choice:");
		lblNewJgoodiesLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewJgoodiesLabel.setBounds(27, 176, 162, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel idCorrectLbl = new JLabel("Type in ID# :");
		idCorrectLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		idCorrectLbl.setBounds(101, 206, 79, 14);
		contentPane.add(idCorrectLbl);
		
	}
	
	public ManagerInterfaceWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel reportLabel = DefaultComponentFactory.getInstance().createTitle("Choose Which Report to Generate:");
		reportLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		reportLabel.setBounds(69, 37, 319, 25);
		contentPane.add(reportLabel);
		
		JLabel prReportLabel = DefaultComponentFactory.getInstance().createLabel("1 - Provider Report");
		prReportLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		prReportLabel.setBounds(164, 73, 127, 14);
		contentPane.add(prReportLabel);
		
		JLabel managerTitle = DefaultComponentFactory.getInstance().createTitle("Manager Menu");
		managerTitle.setForeground(new Color(255, 0, 51));
		managerTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		managerTitle.setBounds(147, 11, 175, 25);
		contentPane.add(managerTitle);
		
		JLabel memReportLabel = DefaultComponentFactory.getInstance().createLabel("2 - Member Report");
		memReportLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		memReportLabel.setBounds(164, 98, 139, 14);
		contentPane.add(memReportLabel);
		
		JLabel sumReportLabel = DefaultComponentFactory.getInstance().createLabel("3 - Summary Report");
		sumReportLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sumReportLabel.setBounds(164, 123, 139, 20);
		contentPane.add(sumReportLabel);
		
		JLabel etfReportLabel = DefaultComponentFactory.getInstance().createLabel("4 - ETF Report");
		etfReportLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		etfReportLabel.setBounds(164, 148, 127, 14);
		contentPane.add(etfReportLabel);
		
		reportInput = new JTextField();
		reportInput.setBounds(191, 173, 50, 20);
		contentPane.add(reportInput);
		reportInput.setColumns(10);
		
		JButton managerBttn = new JButton("Confirm Answer");
		managerBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*ManagerInterface man_interface = new ManagerInterface();
				String report_choice = reportInput.getText();
				man_interface.requestReport(report_choice);*/
			}
		});
		managerBttn.setBounds(251, 175, 155, 17);
		contentPane.add(managerBttn);
		
		JButton manExit = new JButton("Exit");
		manExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ExitWindow exit = new ExitWindow();
				exit.setVisible(true);
			}
		});
		manExit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		manExit.setBounds(10, 231, 89, 23);
		contentPane.add(manExit);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Type in Number of Choice:");
		lblNewJgoodiesLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewJgoodiesLabel.setBounds(27, 176, 162, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		idInput = new JTextField();
		idInput.setBounds(191, 204, 50, 20);
		contentPane.add(idInput);
		idInput.setColumns(10);
		
		JLabel idCorrectLbl = new JLabel("Type in ID# :");
		idCorrectLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		idCorrectLbl.setBounds(101, 206, 79, 14);
		contentPane.add(idCorrectLbl);
		
		JLabel generateDisplay = new JLabel("");
		generateDisplay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		generateDisplay.setBounds(147, 237, 127, 14);
		contentPane.add(generateDisplay);
	}
}
