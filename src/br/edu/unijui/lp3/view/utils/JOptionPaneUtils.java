package br.edu.unijui.lp3.view.utils;

import javax.swing.JOptionPane;

public abstract class JOptionPaneUtils {
	
	public static void showError(String message) {
		showError("ERRO", message);
	}
	
	public static void showError(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static String showInput(String message) {
		return showInput("", message);
	}
	
	public static String showInput(String title, String message) {
		return JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void showMessage(String message) {
		showMessage("SUCESSO", message);
	}
	
	public static void showMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean showQuestionMessage(String message) {
		return showQuestionMessage("Tem certeza?", message);
	}
	
	public static boolean showQuestionMessage(String title, String message) {
		return (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE));
	}
	
	public static void showWarning(String message) {
		showWarning("AVISO", message);
	}
	
	public static void showWarning(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}
	
}