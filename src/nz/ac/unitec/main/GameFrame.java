package nz.ac.unitec.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameFrame extends JFrame implements UpdateGenLabelInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameFrame () {
		super("Life Game GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		/// Controls panel (pnlControls)
		JPanel pnlControls = new JPanel();
		getContentPane().add(pnlControls, BorderLayout.NORTH);
		
		/// Game field panel (pnlGameField)
		GameFieldPanel pnlGameField = new GameFieldPanel();
		getContentPane().add(pnlGameField, BorderLayout.CENTER);
		
		/// Button - Random generation (btnRandomGeneration)
		JButton btnRandomGeneration = new JButton("RandomGeneration");
		btnRandomGeneration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pnlGameField.RandomGeneration();
			}
		});
		pnlControls.add(btnRandomGeneration);
		
		/// Button - Start! (btnStart)
		JButton btnStart = new JButton("Start!");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlGameField.Start();
			}
		});
		pnlControls.add(btnStart);
		
		/// Button - Stop (btnStop)
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlGameField.Stop();
			}
		});
		pnlControls.add(btnStop);
	}

	public void incGenLabelEvent() {
	}

	public void resetGenLabelEvent() {
	}
}
