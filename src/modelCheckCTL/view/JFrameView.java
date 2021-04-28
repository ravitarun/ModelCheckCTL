package modelCheckCTL.view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import modelCheckCTL.controller.Controller;
import modelCheckCTL.model.AbstractModel;
import modelCheckCTL.model.Model;
import modelCheckCTL.model.ModelListener;

/**
 * @author Satya Annavaram
 *
 */
abstract public class JFrameView extends JFrame implements View, ModelListener {

	private static final long serialVersionUID = 1L;
	private Model model;
	private Controller controller;

	/**
	 * Constructor set the model and controller.
	 * 
	 * @param model
	 * @param controller
	 */
	public JFrameView(Model model, Controller controller) {
		setModel(model);
		setController(controller);
		setTitle("Model Check CTL");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setBackground(Color.CYAN);
		

	}

	/**
	 * Register the listener with the model.
	 */
	public void registerModel() {
		System.out.println("JFrameView.registerModel ");
		((AbstractModel) model).addModelListener(this);
	}

	/**
	 * 
	 * @returns controller
	 */
	public Controller getController() {
		System.out.println("JFrameView.getController ");
		return controller;
	}

	/**
	 * 
	 * @param controller
	 */
	public void setController(Controller controller) {
		System.out.println("JFrameView.setController ");
		this.controller = controller;
	}

	/**
	 * 
	 * @returns model
	 */
	public Model getModel() {
		System.out.println("JFrameView.getModel ");
		return model;
	}

	/**
	 * 
	 * @param model
	 */
	public void setModel(Model model) {
		System.out.println("JFrameView.setModel ");
		this.model = model;
		registerModel();
	}
	
} // JFrameView
