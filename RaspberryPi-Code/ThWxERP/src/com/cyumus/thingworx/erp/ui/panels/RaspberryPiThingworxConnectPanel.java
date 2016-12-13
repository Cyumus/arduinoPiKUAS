package com.cyumus.thingworx.erp.ui.panels;

import javax.swing.JPanel;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;
import com.cyumus.thingworx.erp.ui.actions.RaspberryPiActionFactory;

import javax.swing.JPasswordField;

public class RaspberryPiThingworxConnectPanel extends RaspberryPiAbstractPanel {
	private JTextField connNameField;
	private JTextField hostField;
	private JTextField uNameField;
	private JTextField portField;
	private JPasswordField passwordField;
	public static final int CONNECT = 0;

	/**
	 * Create the panel.
	 */
	public RaspberryPiThingworxConnectPanel() {
		setLayout(null);
		this.setBounds(0,0,434,261);
		
		JButton btnCancel = new JButton();
		btnCancel.setBounds(335, 230, 89, 23);
		btnCancel.setAction(RaspberryPiActionFactory.newAction(RaspberryPiFrame.DEFAULT));
		btnCancel.setText("Cancel");
		add(btnCancel);
		
		JButton btnConnect = new JButton();
		btnConnect.setBounds(237, 230, 89, 23);
		btnConnect.setAction(RaspberryPiActionFactory.newAction(CONNECT));
		btnConnect.setText("Connect");
		add(btnConnect);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 208);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblConnectionName = new JLabel("Connection Name");
		lblConnectionName.setBounds(10, 40, 121, 14);
		panel.add(lblConnectionName);
		
		connNameField = new JTextField();
		connNameField.setBounds(141, 37, 130, 20);
		panel.add(connNameField);
		connNameField.setColumns(10);
		
		JLabel lblHostDomain = new JLabel("Host Domain");
		lblHostDomain.setBounds(10, 65, 121, 14);
		panel.add(lblHostDomain);
		
		hostField = new JTextField();
		hostField.setBounds(141, 62, 130, 20);
		panel.add(hostField);
		hostField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 90, 121, 14);
		panel.add(lblUsername);
		
		uNameField = new JTextField();
		uNameField.setBounds(141, 87, 130, 20);
		panel.add(uNameField);
		uNameField.setColumns(10);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(281, 65, 46, 14);
		panel.add(lblPort);
		
		portField = new JTextField();
		portField.setBounds(337, 62, 62, 20);
		panel.add(portField);
		portField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 115, 70, 14);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(141, 112, 130, 20);
		panel.add(passwordField);
	}
	public HashMap<String, String> getText(){
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("name", connNameField.getText());
		args.put("host", hostField.getText());
		args.put("port", portField.getText());
		args.put("uname", uNameField.getText());
		args.put("password", new String(passwordField.getPassword()));
		return args;
	}
}
