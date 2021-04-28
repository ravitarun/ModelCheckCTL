package View;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JTextArea;

import Model.Project;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class UI {

	public JFrame frame;
	private String filePath;
	private JEditorPane inputState = new JEditorPane();
	private JEditorPane inputExpression = new JEditorPane();
	private JTextArea outputDisplay = new JTextArea();
	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 10));
		frame.setBounds(100, 100, 950, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(51, 204, 255));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{50, 29, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		
		
		frame.getContentPane().setLayout(gridBagLayout);
		JButton loadModel = new JButton("Upload Model");
		loadModel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		GridBagConstraints gbc_loadModel = new GridBagConstraints();
		gbc_loadModel.insets = new Insets(0, 0, 5, 5);
		gbc_loadModel.gridx = 0;
		gbc_loadModel.gridy = 0;
		frame.getContentPane().setBackground(new Color(51, 204, 255));
		loadModel.setForeground(Color.DARK_GRAY);
		frame.getContentPane().add(loadModel, gbc_loadModel);
		
		
		inputState.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		GridBagConstraints gbc_inputState = new GridBagConstraints();
		gbc_inputState.insets = new Insets(0, 0, 5, 5);
		gbc_inputState.gridx = 1;
		gbc_inputState.gridy = 0;
		frame.getContentPane().setBackground(new Color(51, 204, 255));
		frame.getContentPane().add(inputState, gbc_inputState);
		inputState.setText("Enter Start State");
		
		
		inputExpression.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		GridBagConstraints gbc_inputExpression = new GridBagConstraints();
		gbc_inputExpression.insets = new Insets(0, 0, 5, 5);
		gbc_inputExpression.gridx = 2;
		gbc_inputExpression.gridy = 0;
		frame.getContentPane().setBackground(new Color(51, 204, 255));
		frame.getContentPane().add(inputExpression, gbc_inputExpression);
		inputExpression.setText("Enter Expression");	
		
		
		JButton run = new JButton("Run Now");
		run.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		GridBagConstraints gbc_run = new GridBagConstraints();
		gbc_run.insets = new Insets(0, 0, 0, 5);
		gbc_run.gridx = 2;
		gbc_run.gridy = 1;
		frame.getContentPane().setBackground(new Color(51, 204, 255));
		frame.getContentPane().add(run, gbc_run);
		
		
		GridBagConstraints gbc_outputDisplay = new GridBagConstraints();
		gbc_outputDisplay.fill = GridBagConstraints.BOTH;
		gbc_outputDisplay.gridx = 3;
		gbc_outputDisplay.gridy = 1;
		frame.getContentPane().add(outputDisplay, gbc_outputDisplay);
		
		
		
		loadModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(".");

			    fileChooser.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			        System.out.println("Action");

			      }
			    });
			    
			    int status = fileChooser.showOpenDialog(null);

			    if (status == JFileChooser.APPROVE_OPTION) {
			      File selectedFile = fileChooser.getSelectedFile();
			      filePath = selectedFile.getAbsolutePath();
			    } else if (status == JFileChooser.CANCEL_OPTION) {
			      System.out.println("canceled");

			    }
			  }
		});
		
		
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Path p = getFilePath();
				String iS = getInputState();
				String iE = getInputExpression();
				Project pro = new Project(p,iS,iE);
				pro.runProject();
			  }
		});
		
		
	}
	
	public Path getFilePath() {
		return Paths.get(filePath);
	}

	public String getInputState() {
		return inputState.getText();
	}

	public String getInputExpression() {
		return inputExpression.getText();
	}
	
	public void setOutputDisplay(String text) {
		outputDisplay.append("\n" + text);
	}
}
