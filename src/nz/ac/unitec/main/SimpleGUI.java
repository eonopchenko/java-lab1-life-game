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
	private JButton btnNext = new JButton("Next Generation");
	private JButton btnStart = new JButton("Start!");
	private JButton btnStop = new JButton("Stop");
	private JButton btnZeroGen = new JButton("Produce Zero Generation");
	private JRadioButton rbtnGenRandom = new JRadioButton("Random");
	private JRadioButton rbtnGenGlider = new JRadioButton("Glider");
	private JLabel lblGen = new JLabel("");
	
	public SimpleGUI () {
		super("Simple Example");
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Container container = this.getContentPane();
		container.setLayout(new FlowLayout());
		container.add(pnlGame);
		
		container.add(btnZeroGen);
		
		ButtonGroup btngrGen = new ButtonGroup();
		btngrGen.add(rbtnGenRandom);
		btngrGen.add(rbtnGenGlider);
		rbtnGenRandom.setSelected(true);
		
		container.add(rbtnGenRandom);
		container.add(rbtnGenGlider);
		
		container.add(btnNext);
		container.add(btnStart);
		container.add(btnStop);
		
		container.add(lblGen);
		
		btnNext.addActionListener(new NextEventListener());
		btnStart.addActionListener(new StartEventListener());
		btnStop.addActionListener(new StopEventListener());
		btnZeroGen.addActionListener(new ZeroGenEventListener());
	}
	
	class NextEventListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			pnlGame.NextGeneration();
			lblGen.setText(Integer.toString(Integer.parseInt(lblGen.getText()) + 1));
		}
	}
	
	class StartEventListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			pnlGame.Start();
		}
	}
	
	class StopEventListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			pnlGame.Stop();
		}
	}
	
	class ZeroGenEventListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			pnlGame.ZeroGeneration(rbtnGenRandom.isSelected() ? 0 : 1);
			lblGen.setText("0");
		}
	}
}
