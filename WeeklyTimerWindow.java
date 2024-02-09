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
import java.awt.Color;

public class WeeklyTimerWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WeeklyTimerWindow frame = new WeeklyTimerWindow();
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
	public WeeklyTimerWindow(MemberRecords[] member, ProviderRecords[] provider, int memSize, int proSize,ServiceRecord[] service, int servSize) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton weekExitBttn = new JButton("Exit");
		weekExitBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ExitWindow exit = new ExitWindow();
				exit.setVisible(true);
			}
		});
		weekExitBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		weekExitBttn.setBounds(24, 231, 89, 23);
		contentPane.add(weekExitBttn);

		JLabel successLbl = new JLabel("");
		successLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		successLbl.setBounds(182, 147, 108, 14);
		contentPane.add(successLbl);

		JButton runMainBttn = new JButton("Run Main Accounting Procedure");
		runMainBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WeeklyTimer timer = new WeeklyTimer();
				timer.runMainAccountProcedure(member, provider, memSize, proSize, service, servSize);
				successLbl.setText("Success.");
			}
		});
		runMainBttn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		runMainBttn.setBounds(104, 102, 240, 23);
		contentPane.add(runMainBttn);

		JLabel lblNewLabel = new JLabel("Weekly Timer Menu");
		lblNewLabel.setForeground(new Color(204, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(114, 28, 215, 23);
		contentPane.add(lblNewLabel);
	}

	/**
	 * Create the frame.
	 */
	public WeeklyTimerWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton weekExitBttn = new JButton("Exit");
		weekExitBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ExitWindow exit = new ExitWindow();
				exit.setVisible(true);
			}
		});
		weekExitBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		weekExitBttn.setBounds(24, 231, 89, 23);
		contentPane.add(weekExitBttn);

		JButton runMainBttn = new JButton("Run Main Accounting Procedure");
		runMainBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		runMainBttn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		runMainBttn.setBounds(104, 102, 240, 23);
		contentPane.add(runMainBttn);

		JLabel successLbl = new JLabel("");
		successLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		successLbl.setBounds(182, 147, 108, 14);
		contentPane.add(successLbl);

		JLabel timerTitle = new JLabel("Weekly Timer Menu");
		timerTitle.setForeground(new Color(204, 0, 0));
		timerTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		timerTitle.setBounds(114, 28, 215, 23);
		contentPane.add(timerTitle);
	}

}
