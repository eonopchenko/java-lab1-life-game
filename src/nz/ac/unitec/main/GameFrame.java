package nz.ac.unitec.main;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.border.EtchedBorder;

public class GameFrame extends JFrame implements UpdateGenLabelInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel lblGen;
	private JFileChooser fileChooser;
    private JMenu mnuFile, mnuGame;
    private JMenuItem miSpeed, miAbout, miExit;
	private boolean started;
	private int speed = 5;
	
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
		GameFieldPanel pnlGameField = GameFieldPanel.GetInstance();//new GameFieldPanel();
		pnlGameField.SetUpdateGenLabelCallback(this);
		pnlGameField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(pnlGameField, BorderLayout.CENTER);
		
		/// JButton - Load CSV... (btnLoadCSV)
		JButton btnLoadCSV = new JButton("Load CSV...");
		btnLoadCSV.setIcon(new ImageIcon(GameFrame.class.getResource("/resources/Open16.gif")));
		pnlControls.add(btnLoadCSV);
		btnLoadCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            if (fileChooser.showOpenDialog(GameFrame.this) == JFileChooser.APPROVE_OPTION) {
	                File file = fileChooser.getSelectedFile();
	                String path = file.getAbsolutePath();
	                try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
	                	pnlGameField.LoadGameField(buffer);
	                } catch (IOException ex) {
//						// TODO Auto-generated catch block
	                    ex.printStackTrace();
	                }
	            }
			}
		});
		
		/// JButton - Save CSV... (btnSaveCSV)
		JButton btnSaveCSV = new JButton("Save CSV...");
		btnSaveCSV.setIcon(new ImageIcon(GameFrame.class.getResource("/resources/Save16.gif")));
		pnlControls.add(btnSaveCSV);		
		btnSaveCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            if (fileChooser.showSaveDialog(GameFrame.this) == JFileChooser.APPROVE_OPTION) {
	                File file = fileChooser.getSelectedFile();
	                try {
						FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
						String[][] str = pnlGameField.SaveGameField();
						for (int i = 0; i < str.length; i++) {
							StringBuilder builder = new StringBuilder();
							for(String s : str[i]) {
							    builder.append(s);
							}
							fileWriter.append(builder.toString() + '\n');
						}
						fileWriter.flush();
						fileWriter.close();
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
	            }
			}
		});
		
		fileChooser = new JFileChooser();
		fileChooser.setSelectedFile(new File("*.csv"));
		
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
					pnlGameField.Start(speed);
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
//		JRadioButton rdbtnCell = new JRadioButton(new ImageIcon(GameFrame.class.getResource("/resources/CellDes16.gif")));
//		rdbtnCell.setSelectedIcon(new ImageIcon(GameFrame.class.getResource("/resources/CellSel16.gif")));
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
		
		/// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		/// File Menu
		mnuFile = new JMenu("File");
        menuBar.add(mnuFile);
        mnuGame = new JMenu("Game");
        menuBar.add(mnuGame);
        miAbout = new JMenuItem("About");
        mnuFile.add(miAbout);
        mnuFile.add(new JSeparator());
        miExit = new JMenuItem("Exit");
        mnuFile.add(miExit);
        miSpeed = new JMenuItem("Speed");
        mnuGame.add(miSpeed);
        
        miAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Conway's Game Of Life.\n\n Developed by Evgenii Onopchenko, Udit Shah, Alexandr Li.\n\n");
			}
		});
        
        miExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
        
        miSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            final JFrame frmSpeed = new JFrame();
	            frmSpeed.setTitle("Speed");
	            frmSpeed.setSize(300,60);
	            frmSpeed.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frmSpeed.getWidth())/2, 
	                (Toolkit.getDefaultToolkit().getScreenSize().height - frmSpeed.getHeight())/2);
	            frmSpeed.setResizable(false);
	            JPanel pnlSpeed = new JPanel();
	            pnlSpeed.setOpaque(false);
	            frmSpeed.add(pnlSpeed);
	            pnlSpeed.add(new JLabel("Next Generation Speed:"));
	            Integer[] speedType = {1,5,10,20,100};
	            final JComboBox cmbSpeed = new JComboBox(speedType);
	            pnlSpeed.add(cmbSpeed);
	            cmbSpeed.setSelectedItem(speed);
	            cmbSpeed.addActionListener(new ActionListener(){
	                @Override
	                public void actionPerformed(ActionEvent ae) {
	                    speed = (Integer)cmbSpeed.getSelectedItem();
	    					if(started) {
	    						pnlGameField.Stop();
	    						pnlGameField.Start(speed);
	    					}
	    					
	                    frmSpeed.dispose();
	                }
	            });
	            frmSpeed.setVisible(true);
			}
		});
        
        
        
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
