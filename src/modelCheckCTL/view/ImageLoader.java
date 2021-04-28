package modelCheckCTL.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author Satya Annavaram
 */

public class ImageLoader extends JPanel{
	
	 private Image img;
	 
	 public ImageLoader(String img) {
	    this(new ImageIcon(img).getImage());
	 }
	 
	 public ImageLoader(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    //setLayout(null);
	 }
	 @Override
	 public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	 }
}
