package nz.ac.unitec.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;

public class GameFrame extends JFrame implements UpdateGenLabelInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel lblGen;
	private boolean started;
	
	/// Constructor
	public GameFrame () {
		super("Life Game GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		/// Controls panel (pnlControls)
		JPanel pnlControls = new JPanel();
		pnlControls.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(pnlControls, BorderLayout.NORTH);
		
		/// Game field panel (pnlGameField)
		GameFieldPanel pnlGameField = new GameFieldPanel();
		pnlGameField.SetUpdateGenLabelCallback(this);
		pnlGameField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(pnlGameField, BorderLayout.CENTER);
		
		/// Button - Random generation (btnRandomGeneration)
		JButton btnRandomGeneration = new JButton("RandomGeneration");
		btnRandomGeneration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pnlGameField.RandomGeneration();
			}
		});
		pnlControls.add(btnRandomGeneration);
		
		/// Button - Start!/Stop (btnStartStop)
		JButton btnStartStop = new JButton("Start!");
		btnStartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(started) {
					pnlGameField.Stop();
					btnStartStop.setText("Start!");
					started = false;
				} else {
					pnlGameField.Start();
					btnStartStop.setText("Stop");
					started = true;
				}
			}
		});
		pnlControls.add(btnStartStop);
		
		/// Button - Clear (btnClear)
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pnlGameField.ClearGameField();
			}
		});
		pnlControls.add(btnClear);
		
		/// Button - Next Step (btnNextStep)
		JButton btnNextStep = new JButton("Next Step");
		btnNextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlGameField.NextGeneration();
			}
		});
		pnlControls.add(btnNextStep);
		
		/// Label - 0 (lblGeneration)
		lblGen = new JLabel("0");
		pnlControls.add(lblGen);
		
		/// JPanel - Patterns (pnlPatterns)
		JPanel pnlPatterns = new JPanel();
		pnlPatterns.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(pnlPatterns, BorderLayout.SOUTH);
		
		/// JRadioButton - Cell (rdbtnCell)
		JRadioButton rdbtnCell = new JRadioButton("Cell");
		rdbtnCell.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			        pnlGameField.SetPattern(GameFieldPanel.Pattern.CELL);
			    }
			}
		});
		pnlPatterns.add(rdbtnCell);
		
		/// JRadioButton - Glider (rbtnGlider)
		JRadioButton rdbtnGlider = new JRadioButton("Glider");
		rdbtnGlider.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			        pnlGameField.SetPattern(GameFieldPanel.Pattern.GLIDER);
			    }
			}
		});
		pnlPatterns.add(rdbtnGlider);

		/// JRadioButton - R-Pentomino (rdbtnRPentomino)
		JRadioButton rdbtnRPentomino = new JRadioButton("R-Pentomino");
		rdbtnRPentomino.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			        pnlGameField.SetPattern(GameFieldPanel.Pattern.R_PENTONIMO);
			    }
			}
		});
		pnlPatterns.add(rdbtnRPentomino);

		/// JRadioButton - Spaceship 1 (rdbtnSpaceship1)
		JRadioButton rdbtnSpaceship1 = new JRadioButton("Spaceship 1");
		rdbtnSpaceship1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			        pnlGameField.SetPattern(GameFieldPanel.Pattern.SPACESHIP_1);
			    }
			}
		});
		pnlPatterns.add(rdbtnSpaceship1);

		/// JRadioButton - Glider Gun (rdbtnGliderGun)
		JRadioButton rdbtnGliderGun = new JRadioButton("Glider Gun");
		rdbtnGliderGun.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			        pnlGameField.SetPattern(GameFieldPanel.Pattern.GLIDER_GUN);
			    }
			}
		});
		pnlPatterns.add(rdbtnGliderGun);

		/// JRadioButton - Glider Eater (rdbtnGliderEater)
		JRadioButton rdbtnGliderEater = new JRadioButton("Glider Eater");
		rdbtnGliderEater.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			    if (e.getStateChange() == ItemEvent.SELECTED) {
			        pnlGameField.SetPattern(GameFieldPanel.Pattern.GLIDER_EATER);
			    }
			}
		});
		pnlPatterns.add(rdbtnGliderEater);
		
		/// ButtonGroup - (btngrPatterns)
		ButtonGroup btngrPatterns = new ButtonGroup();
		btngrPatterns.add(rdbtnCell);
		btngrPatterns.add(rdbtnGlider);
		btngrPatterns.add(rdbtnRPentomino);
		btngrPatterns.add(rdbtnSpaceship1);
		btngrPatterns.add(rdbtnGliderGun);
		btngrPatterns.add(rdbtnGliderEater);
		rdbtnCell.setSelected(true);
		
		started = false;
	}
	
	/// Increase generation number label
	public void incGenLabelEvent() {
		lblGen.setText(Integer.toString(Integer.parseInt(lblGen.getText()) + 1));
	}
	
	/// Reset generation number label
	public void resetGenLabelEvent() {
		lblGen.setText("0");
	}
}
