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

public class GameFrame extends JFrame implements UpdateGenLabelInterface {
	private static final long serialVersionUID = 1L;
	
	private GamePanel pnlGame = new GamePanel(this);
	private JButton btnRandomGen = new JButton("Produce Random Generation");
	private JRadioButton rbtnShapeCell = new JRadioButton("Cell");
	private JRadioButton rbtnShapeGlider = new JRadioButton("Glider");
	private JButton btnClear = new JButton("Clear");
	private JButton btnNext = new JButton("Next Generation");
	private JButton btnStartStop = new JButton("Start!");
	private JLabel lblGen = new JLabel("0");
	private boolean started;
	
	public GameFrame () {
		super("Life Game GUI");
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Container container = this.getContentPane();
		container.setLayout(new FlowLayout());
		
		ButtonGroup btngrGen = new ButtonGroup();
		btngrGen.add(rbtnShapeCell);
		btngrGen.add(rbtnShapeGlider);
		rbtnShapeCell.setSelected(true);
		
		container.add(pnlGame);
		container.add(btnRandomGen);
		container.add(rbtnShapeCell);
		container.add(rbtnShapeGlider);
		container.add(btnClear);
		container.add(btnNext);
		container.add(btnStartStop);
		container.add(lblGen);
		
		rbtnShapeCell.addActionListener(new ShapeCellEventListener());
		rbtnShapeGlider.addActionListener(new ShapeGliderEventListener());
		btnRandomGen.addActionListener(new RandomGenEventListener());
		btnClear.addActionListener(new ClearEventListener());
		btnNext.addActionListener(new NextEventListener());
		btnStartStop.addActionListener(new StartEventListener());
		
		started = false;
	}
	
	public void incGenLabelEvent() {
		lblGen.setText(Integer.toString(Integer.parseInt(lblGen.getText()) + 1));
	}
	
	public void resetGenLabelEvent() {
		lblGen.setText("0");
	}
	
	class ShapeCellEventListener implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e) {
			pnlGame.SetShape(GamePanel.Shape.CELL);
		}
	}
	
	class ShapeGliderEventListener implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e) {
			pnlGame.SetShape(GamePanel.Shape.GLIDER);
		}
	}
	
	class ClearEventListener implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e) {
			pnlGame.Clear();
		}
	}
	
	class NextEventListener implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e) {
			pnlGame.NextGeneration();
		}
	}
	
	class StartEventListener implements ActionListener {
		@Override
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
	
	class RandomGenEventListener implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e) {
			pnlGame.RandomGeneration();
		}
	}
}
