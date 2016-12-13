package com.cyumus.thingworx.erp.ui.panels;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import com.cyumus.thingworx.erp.things.BinThing;
import com.cyumus.thingworx.erp.things.ItemThing;
import com.cyumus.thingworx.erp.things.LocationThing;
import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;
import com.cyumus.thingworx.erp.ui.actions.RaspberryPiActionFactory;
import javax.swing.JList;

public class RaspberryPiThingworxMainPanel extends RaspberryPiAbstractPanel  {

	/**
	 * Create the panel.
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JScrollPane scroll;
	private JTree thingList;
	private DefaultMutableTreeNode top;
	/**
	 * Create the panel.
	 */
	public RaspberryPiThingworxMainPanel() {
		this.setBounds(0,0,800,600);
		setLayout(null);
		setBorder(new TitledBorder(new EtchedBorder(), "Connecting"));
		JButton btnCancel = new JButton();
		btnCancel.setBounds(676, 523, 89, 23);
		btnCancel.setAction(RaspberryPiActionFactory.newAction(RaspberryPiFrame.DEFAULT));
		btnCancel.setText("Exit");
		add(btnCancel);
		
		textArea = new JTextArea(16,58);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);		
		textArea.setBounds(10, 11, 414, 205);
		
		scroll = new JScrollPane(textArea);
		scroll.setBounds(10,300,625,245);
		scroll.getViewport().setView(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(scroll);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 22, 625, 267);
		add(scrollPane);
		top = new DefaultMutableTreeNode();
		try{load();}catch(Exception e){e.printStackTrace();}
		thingList = new JTree(top);
		scrollPane.setViewportView(thingList);
	}
	public void write(int i){
		if (textArea!=null)
			textArea.append(""+(char) i);
	}
	public HashMap<String, String> getText(){
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("logs", textArea.getText());
		return args;
	}
	public void load() throws Exception{
		HashMap<String, LocationThing> locs = RaspberryPiFrame.getSingleton().getClient().getLocations();
		for (LocationThing location:locs.values()){
			DefaultMutableTreeNode loc = new DefaultMutableTreeNode("[L] "+location.getName()+" ("+location.getIdentifier()+")");
			HashMap<String, BinThing> bins = location.getBins();
			for (BinThing bin:bins.values()){
				DefaultMutableTreeNode bi = new DefaultMutableTreeNode("[B] "+bin.getName()+" ("+bin.getIdentifier()+")");
				HashMap<String, ItemThing> items = bin.getItems();
				for (ItemThing item:items.values()){
					DefaultMutableTreeNode it = new DefaultMutableTreeNode("[I] "+item.getName()+" ("+item.getIdentifier()+")");
					bi.add(it);
				}
				loc.add(bi);
			}
			top.add(loc);
		}
		
	}
}
