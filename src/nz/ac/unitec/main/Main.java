/*
 * @author E.Onopchenko, H.Vardan
 * @company Unitec Institute of Technology
 * @date 29/07/2017
 */

/*
 * @todo 1. Add preset (typical shapes) selection
 * @todo 2. Add game field dimensions selection
 * @todo 3. Add step period selection
 */

package nz.ac.unitec.main;

import java.awt.Dimension;

public class Main {
	public static void main(String args[]) {
        GameFrame frame = new GameFrame();
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setVisible(true);
	}
}
