package br.edu.unijui.lp3.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class JFLanguage extends JFrame {
	
	private JLabel jlId;
	private JLabel jlLastUpdate;
	private JTabbedPane jtpRegisterSearch;
	private JTextField jtfId;
	private JTextField jtfName;
	private JTextField jtfLastUpdate;
	
	public JFLanguage() {
		configureComponents();
		addComponents();
	}
	
	private void configureComponents() {
		jlId = new JLabel("Id");
		jlId.setVisible(false);
		jtfId = new JTextField();
		jtfId.setVisible(false);
		jtfName = new JTextField();
		jlLastUpdate = new JLabel("Last Update");
		jlLastUpdate.setVisible(false);
		jtfLastUpdate = new JTextField();
		jtfLastUpdate.setEditable(false);
		jtfLastUpdate.setVisible(false);
		jtpRegisterSearch = new JTabbedPane();
		jtpRegisterSearch.addTab("Register", createPanelRegister());
		jtpRegisterSearch.addTab("Search", createPanelSearch());
	}
	
	private JPanel createPanelRegister() {
		JPanel jpRegister = new JPanel(new MigLayout(new LC().hideMode(3)));
		jpRegister.add(jlId);
		jpRegister.add(jtfId, new CC().width("50!"));
		jpRegister.add(new JLabel("Name"));
		jpRegister.add(jtfName, new CC().width("100%").wrap());
		jpRegister.add(jlLastUpdate);
		jpRegister.add(jtfLastUpdate, new CC().width("100%").spanX());
		return jpRegister;
	}
	
	private JPanel createPanelSearch() {
		JPanel jpSearch = new JPanel(new MigLayout());
		
		return jpSearch;
	}
	
	private void addComponents() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new MigLayout(new LC().hideMode(3)));
		setTitle("Languages");
		
		add(jtpRegisterSearch, new CC().width("400:100%:").height("300:100%:"));
		
		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
	}
	
	
	
}