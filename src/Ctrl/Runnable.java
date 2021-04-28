package Ctrl;

import View.UI;

public class Runnable {
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		UI window = new UI();
		try {
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		OutputHandler handler = new OutputHandler();
		handler.setOutputWindow(window);
	}
	
	
}
