package com.cyumus.thingworx.erp.ui.panels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.tree.TreePath;

import com.cyumus.thingworx.erp.things.BinThing;
import com.cyumus.thingworx.erp.things.ItemThing;
import com.cyumus.thingworx.erp.things.LocationThing;
import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;
import com.cyumus.thingworx.erp.ui.actions.RaspberryPiActionFactory;
import com.cyumus.thingworx.erp.ui.actions.StartScanAction;
import com.cyumus.thingworx.erp.ui.actions.StopScanAction;
import com.cyumus.thingworx.erp.ui.panels.info.RaspberryPiDefaultInfoPanel;
import com.cyumus.thingworx.erp.ui.panels.info.RaspberryPiInfoPanelFactory;

public class RaspberryPiThingworxMainPanel extends RaspberryPiAbstractPanel  {

	/**
	 * Create the panel.
	 */
	private static final long serialVersionUID = 1L;
	public static final int SCAN = 1;
	public static final int STOP = 2;
	private JTextArea textArea;
	private JScrollPane scroll;
	private JTree thingList;
	private DefaultMutableTreeNode top;
	private RaspberryPiDefaultInfoPanel infoPanel;
	public static JButton btnScan, btnStop;
	/**
	 * Create the panel.
	 */
	public RaspberryPiThingworxMainPanel() {
		try{
			this.setBounds(0,0,800,600);
			setLayout(null);
			setBorder(new TitledBorder(new EtchedBorder(), "Main Menu"));
			
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
			scrollPane.setBounds(10, 22, 200, 267);
			add(scrollPane);
			top = new DefaultMutableTreeNode("Things");
			try{load();}catch(Exception e){e.printStackTrace();}
			thingList = new JTree(top);
			thingList.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent me){
					doMouseClicked(me);
				}
			});
			scrollPane.setViewportView(thingList);
			
			
			JButton btnCancel = new JButton();
			btnCancel.setBounds(676, 520, 89, 23);
			btnCancel.setAction(RaspberryPiActionFactory.newAction(RaspberryPiFrame.DEFAULT));
			btnCancel.setText("Exit");
			add(btnCancel);
			
			JButton btnDelete = new JButton();
			btnDelete.setBounds(676, 490, 89, 23);
			btnDelete.setAction(null);
			btnDelete.setText("Delete");
			btnDelete.setEnabled(false);
			add(btnDelete);
			
			JButton btnNew = new JButton();
			btnNew.setBounds(676, 460, 89, 23);
			btnNew.setAction(null);
			btnNew.setText("New");
			btnNew.setEnabled(false);
			add(btnNew);
			
			btnStop = new JButton();
			btnStop.setBounds(676,  410, 89, 23);
			btnStop.setAction(RaspberryPiActionFactory.newAction(STOP));
			btnStop.setText("Stop");
			btnStop.setEnabled(false);
			add(btnStop);
			
			btnScan = new JButton();
			btnScan.setBounds(676,  380, 89, 23);
			btnScan.setAction(RaspberryPiActionFactory.newAction(SCAN));
			btnScan.setText("Scan");
			btnScan.setEnabled(true);
			add(btnScan);
			
			infoPanel = new RaspberryPiDefaultInfoPanel();
			infoPanel.setBounds(220, 22, 545, 267);
			add(infoPanel);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void doMouseClicked(MouseEvent me){
		TreePath tp = thingList.getPathForLocation(me.getX(), me.getY());
		Object thing;
		if (tp != null){
			thing = tp.getLastPathComponent();
			if (infoPanel!=null){
				this.remove(infoPanel);
				this.revalidate();
				this.repaint();
			}
			this.infoPanel = RaspberryPiInfoPanelFactory.newPanel(thing);
			this.infoPanel.setBounds(220,22,545,267);
			this.add(infoPanel);
		}
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
			LocationThingNode loc = new LocationThingNode("[L] "+location.getName()+" ("+location.getIdentifier()+")", location);
			HashMap<String, BinThing> bins = location.getBins();
			for (BinThing bin:bins.values()){
				BinThingNode bi = new BinThingNode("[B] "+bin.getName()+" ("+bin.getIdentifier()+")", bin);
				HashMap<String, ItemThing> items = bin.getItems();
				for (ItemThing item:items.values()){
					ItemThingNode it = new ItemThingNode("[I] "+item.getName()+" ("+item.getIdentifier()+")", item);
					bi.add(it);
				}
				loc.add(bi);
			}
			top.add(loc);
		}
	}
	
	public HashMap<String, ItemThing> getItems(){
		HashMap<String, ItemThing> items = new HashMap<String, ItemThing>();
		Object node = thingList.getLastSelectedPathComponent();
		if (node instanceof ItemThingNode){
			ItemThing item = ((ItemThingNode) node).getItem();
			items.put(item.getName(), item);
		}
		else if (node instanceof BinThingNode){
			BinThing bin = ((BinThingNode) node).getBin();
			items.putAll(bin.getItems());
		}
		else if (node instanceof LocationThingNode){
			LocationThing loc = ((LocationThingNode) node).getLocation();
			items.putAll(loc.getItems());
		}
		else if (node instanceof DefaultMutableTreeNode){
			int max = ((DefaultMutableTreeNode) node).getChildCount();
			for (int i=0;i<max;i++){
				LocationThingNode n = (LocationThingNode) ((DefaultMutableTreeNode) node).getChildAt(i);
				LocationThing loc = ((LocationThingNode) n).getLocation();
				items.putAll(loc.getItems());
			}
		}
		return items;
	}
	
	public int getTime(){
		return 1000;
	}
	public class ItemThingNode extends DefaultMutableTreeNode implements Comparable<Object>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ItemThing item;
		public ItemThingNode(String name, ItemThing item){
			super(name);
			this.item = item;
		}
		public ItemThing getItem(){
			return this.item;
		}
		public boolean equals(Object o){
			if (!(o instanceof ItemThing)) return false;
			return this.compareTo(o)==0;
		}
		@Override
		public int compareTo(Object o) {
			return this.item.getName().compareTo(((ItemThing) o).getName());
		}
	}
	public class BinThingNode extends DefaultMutableTreeNode implements Comparable<Object>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BinThing bin;
		public BinThingNode(String name, BinThing bin){
			super(name);
			this.bin = bin;
		}
		public BinThing getBin(){
			return this.bin;
		}
		public boolean equals(Object o){
			if (!(o instanceof BinThing)) return false;
			return this.compareTo(o)==0;
		}
		@Override
		public int compareTo(Object o) {
			return this.bin.getName().compareTo(((BinThing) o).getName());
		}
		public boolean hasItem(ItemThing item){
			return this.bin.hasItem(item);
		}
	}
	public class LocationThingNode extends DefaultMutableTreeNode implements Comparable<Object>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private LocationThing loc;
		public LocationThingNode(String name, LocationThing loc){
			super(name);
			this.loc = loc;
		}
		public LocationThing getLocation(){
			return this.loc;
		}
		public boolean equals(Object o){
			if (!(o instanceof BinThing)) return false;
			return this.compareTo(o)==0;
		}
		@Override
		public int compareTo(Object o) {
			return this.loc.getName().compareTo(((LocationThing) o).getName());
		}
		public boolean hasItem(ItemThing item){
			return this.loc.hasItem(item);
		}
		public boolean hasBin(BinThing bin){
			return this.loc.hasBin(bin);
		}
	}
}
