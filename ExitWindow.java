package chocAn;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExitWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExitWindow frame = new ExitWindow();
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
	public ExitWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel exitLabel = DefaultComponentFactory.getInstance().createLabel("You have been exited out of ChocAn");
		exitLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		exitLabel.setBounds(58, 23, 339, 25);
		contentPane.add(exitLabel);
		
		JButton loginReturnBttn = new JButton("Return to Log In");
		loginReturnBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				Interface.main(null);
			}
		});
		loginReturnBttn.setBounds(157, 124, 132, 23);
		contentPane.add(loginReturnBttn);
	}
}
