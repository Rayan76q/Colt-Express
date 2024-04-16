package Vue;

import javax.swing.*;
import java.awt.*;

public class JbgPanel extends JPanel {
        private Image img;

        public JbgPanel(ImageIcon icon) {
            img = icon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, this.getX(), this.getY(), getWidth(), getHeight(), this);
        }
}
