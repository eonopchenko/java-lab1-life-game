package nz.ac.unitec.main;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class SimpleGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GamePanel pnlGame = new GamePanel();
	private JButton btnZeroGen = new JButton("Produce Zero Generation");
	private JRadioButton rbtnGenRandom = new JRadioButton("Random");
	private JRadioButton rbtnGenGlider = new JRadioButton("Glider");
	private JButton btnClear = new JButton("Clear");
	private JButton btnNext = new JButton("Next Generation");
	private JButton btnStartStop = new JButton("Start!");
	private JLabel lblGen = new JLabel("");
	
	private boolean started;
	
	public SimpleGUI () {
		super("Simple Example");
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Container container = this.getContentPane();
		container.setLayout(new FlowLayout());
		
		ButtonGroup btngrGen = new ButtonGroup();
		btngrGen.add(rbtnGenRandom);
		btngrGen.add(rbtnGenGlider);
		rbtnGenRandom.setSelected(true);
		
		container.add(pnlGame);
		container.add(btnZeroGen);
		container.add(rbtnGenRandom);
		container.add(rbtnGenGlider);
		container.add(btnClear);
		container.add(btnNext);
		container.add(btnStartStop);
		container.add(lblGen);

		btnZeroGen.addActionListener(new ZeroGenEventListener());
		btnClear.addActionListener(new ClearEventListener());
		btnNext.addActionListener(new NextEventListener());
		btnStartStop.addActionListener(new StartEventListener());
		
		started = false;
	}
	
	class ClearEventListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			pnlGame.Clear();
		}
	}
	
	class NextEventListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			pnlGame.NextGeneration();
			lblGen.setText(Integer.toString(Integer.parseInt(lblGen.getText()) + 1));
		}
	}
	
	class StartEventListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if(started) {
				pnlGame.Stop();
				btnStartStop.setText("Start!");
				started = false;
			} else {
				pnlGame.Start();
				btnStartStop.setText("Stop");
				started = true;
			}
		}
	}
	
	class ZeroGenEventListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			pnlGame.ZeroGeneration(rbtnGenRandom.isSelected() ? 0 : 1);
			lblGen.setText("0");
		}
	}
}
