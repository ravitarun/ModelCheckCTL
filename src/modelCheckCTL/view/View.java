package modelCheckCTL.view;

import modelCheckCTL.controller.Controller;
import modelCheckCTL.model.Model;

/**
 * @author Satya Annavaram
 *
 */
public interface View {
	Controller getController();

	void setController(Controller controller);

	Model getModel();

	void setModel(Model model);
} // View
