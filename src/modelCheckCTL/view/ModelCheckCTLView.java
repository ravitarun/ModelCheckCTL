package modelCheckCTL.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import modelCheckCTL.controller.ModelCheckCTLController;
import modelCheckCTL.model.ModelCheckCTLModel;
import modelCheckCTL.model.ModelEvent;

/**
 * This object is the main object and creates the GUI for the user. It allows a
 * file representing a Kripke Structure to be loaded. It takes a CTL formula and
 * a starting state from the user. Buttons exist to submit all of these items.
 * The GUI also contains a log output area for the results of the actions taken.
 * 
 * @author Satya Annavaram
 *
 */

public class ModelCheckCTLView extends JFrameView {

	private static final long serialVersionUID = 1L;
	private JTextArea log;
	private JTextField ctlFormula = new JTextField();
	private JTextField startState = new JTextField();
	private JFileChooser fc;
	public static final String CTL_FORMULA = "Load CTL Formula";
	public static final String KRIPKE_STRUCT = "Upload Kripke Structure";
	public static final String CHECK_STATE = "Verify starting state";
	@SuppressWarnings("exports")
	public JButton loadKSButton;
	@SuppressWarnings("exports")
	public JButton loadCTLButton;
	@SuppressWarnings("exports")
	public JButton stateButton;
	private int returnVal;

	
	
	
	private JLabel ctlDefinition = new JLabel("Define CTL Formula Here ");
    private JLabel startStateDef = new JLabel("Select Starting State ");
    private JTextField ctlText = new JTextField(20);
    private JTextField startStateText = new JTextField(20);
	
	
	
	
	
	/**
	 * Constructor - Creates the GUI.
	 * 
	 * @param model
	 * @param controller
	 */
	public ModelCheckCTLView(ModelCheckCTLModel model,
			ModelCheckCTLController controller) {
		super(model, controller);

		//JPanel textPanel = new JPanel();
		
		JPanel buttonPanel = new JPanel();
		Handler handler = new Handler();
		
		
		JPanel textPanel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;     
        textPanel.add(ctlDefinition, constraints);
 
        constraints.gridx = 1;
        textPanel.add(ctlText, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        textPanel.add(startStateDef, constraints);
         
        constraints.gridx = 1;
        textPanel.add(startStateText, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        textPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Input Panel"));
        //add(newPanel);
        
        pack();
        setLocationRelativeTo(null);
		
		// Create the open button.
		loadKSButton = new JButton(KRIPKE_STRUCT);
		loadKSButton.addActionListener(handler);

		// Create the save button.
		loadCTLButton = new JButton(CTL_FORMULA);
		loadCTLButton.addActionListener(handler);

		// Create the save button.
		stateButton = new JButton(CHECK_STATE);
		stateButton.addActionListener(handler);
		// Add the elements
		this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
		buttonPanel.add(loadKSButton, null);
		buttonPanel.add(loadCTLButton, null);
		buttonPanel.add(stateButton, null);
		buttonPanel.setBackground(Color.CYAN);

		// For layout purposes, put the buttons in a separate panel
		buttonPanel.setLayout(new GridLayout(3, 1, 10, 4));
		
		// Field to set ctl formula
		//ctlFormula.setText("Define CTL Formula Here");
		//ctlFormula.setEditable(true);
		// this.getContentPane().add(ctlFormula,BorderLayout.NORTH);

		//startState.setText("Select Starting State");
		//startState.setEditable(true);
		// this.getContentPane().add(startState,BorderLayout.AFTER_LAST_LINE);

		//textPanel.setLayout(new GridLayout(2, 1, 10, 12));
		this.getContentPane().add(textPanel, BorderLayout.NORTH);
		//textPanel.add(ctlFormula, null);
		//textPanel.add(startState, null);
		textPanel.setBackground(Color.CYAN);
		//add(textPanel);
	

		
		// Create the log
		setLog(new JTextArea(50, 50));
		getLog().setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(getLog());
		this.getContentPane().add(logScrollPane, BorderLayout.SOUTH);
		this.getContentPane().setBackground(Color.CYAN);
		

		// Create a file chooser
		fc = new JFileChooser();

		pack();

	} // constructor

	// Now implement the necessary event handling code
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modelCheckCTL.model.ModelListener#modelChanged(modelCheckCTL.model.ModelEvent
	 * )
	 */
	public void modelChanged(ModelEvent event) {
		String msg = event.getOutput() + "";
		System.out.println("ModelCheckCTLView.modelChanged "+msg);
		getLog().append(msg);
	}

	/**
	 * Display results to the log text area.
	 * 
	 * @param s
	 */
	public void displayResults(String s) {
		System.out.println("ModelCheckCTLView.displayResults ");
		getLog().append(s + "\n");
	} // displayResults

	// Inner classes for Event Handling
	/**
	 * Inner class only used here.
	 * 
	 * @author ssiroky
	 *
	 */
	class Handler implements ActionListener {
		// Event handling is handled locally
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		public void actionPerformed(ActionEvent e) {
			System.out.println("ModelCheckCTL$Hander.actionPerformed.operation "+e.getSource());
			if (e.getSource() == loadKSButton) {
				returnVal = fc.showOpenDialog(ModelCheckCTLView.this);
			}
			((ModelCheckCTLController) getController()).operation(e
					.getActionCommand());
		}
	}// class Handler

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ModelCheckCTLController();
	}

	// getters and setters
	@SuppressWarnings("exports")
	public JTextArea getLog() {
		System.out.println("ModelCheckCTLView.getLog ");
		return log;
	}

	@SuppressWarnings("exports")
	public void setLog(JTextArea log) {
		System.out.println("ModelCheckCTLView.setLog ");
		this.log = log;
	}

	@SuppressWarnings("exports")
	public JTextField getCtlFormula() {
		System.out.println("ModelCheckCTLView.getCTLFormula ");
		return ctlFormula;
	}

	@SuppressWarnings("exports")
	public JTextField getStartState() {
		System.out.println("ModelCheckCTLView.getStartState ");
		return startState;
	}

	@SuppressWarnings("exports")
	public void setStartState(JTextField startState) {
		System.out.println("ModelCheckCTLView.setStartState ");
		this.startState = startState;
	}

	@SuppressWarnings("exports")
	public void setCtlFormula(JTextField ctlFormula) {
		System.out.println("ModelCheckCTLView.setCtlFormula ");
		this.ctlFormula = ctlFormula;
	}

	@SuppressWarnings("exports")
	public JFileChooser getFc() {
		System.out.println("ModelCheckCTLView.getFc ");
		return fc;
	}

	@SuppressWarnings("exports")
	public void setFc(JFileChooser fc) {
		System.out.println("ModelCheckCTLView.setFc ");
		this.fc = fc;
	}

	public int getReturnVal() {
		System.out.println("ModelCheckCTLView.getReturnVal ");
		return returnVal;
	}

	public void setReturnVal(int returnVal) {
		System.out.println("ModelCheckCTLView.setReturnVal ");
		this.returnVal = returnVal;
	}

} // ModelCheckCTLView

