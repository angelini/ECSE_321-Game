
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
     private Image bgImage;

     public Image getBackgroundImage() {
        return this.bgImage;
     }

     public void setBackgroundImage(Image image) {
        this.bgImage = image;
     }

     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.drawImage( bgImage, 0, 0, bgImage.getWidth(null), bgImage.getHeight(null), null );
     }
 }