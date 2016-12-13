package com.cyumus.thingworx.erp.ui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class RaspberryPiThingworxLoggingConnectPanel extends RaspberryPiAbstractPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JTextArea textArea;
	private JScrollPane scroll;
	/**
	 * Create the panel.
	 */
	public RaspberryPiThingworxLoggingConnectPanel() {
		this.setBounds(0,0,434,261);
		setLayout(null);
		setBorder(new TitledBorder(new EtchedBorder(), "Connecting"));
		JButton btnCancel = new JButton();
		btnCancel.setBounds(335, 227, 89, 23);
		btnCancel.setAction(RaspberryPiActionFactory.newAction(RaspberryPiFrame.DEFAULT));
		btnCancel.setText("Cancel");
		add(btnCancel);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 227, 315, 23);
		add(progressBar);
		
		textArea = new JTextArea(16,58);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);		
		textArea.setBounds(10, 11, 414, 205);
		
		scroll = new JScrollPane(textArea);
		scroll.setBounds(10,15,414,205);
		scroll.getViewport().setView(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scroll);
	}
	public void setProgress(int p){
		if (progressBar != null)
			progressBar.setValue(p);
	}
	public void write(int i){
		if (textArea!=null)
			textArea.append(""+(char) i);
	}
}
