package nz.ac.unitec.main;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
	public static void main(String args[]) {
        GameFrame frame = new GameFrame();
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth())/2, 
                (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight())/2);
        frame.setVisible(true);
	}
}
