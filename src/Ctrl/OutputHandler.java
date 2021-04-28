package Ctrl;

import View.UI;

public class OutputHandler {
	private static UI outputWindow;
	
	public void setOutputWindow(UI obj) {
		outputWindow = obj;
	}

	public void printOutput(Object text) {
		outputWindow.setOutputDisplay((String) text);
	}

}
