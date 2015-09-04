package br.edu.unijui.lp3.view;

import javax.swing.UIManager;

public class Start {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new JFLanguage().setVisible(true);
	}
	
}