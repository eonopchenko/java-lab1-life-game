package nz.ac.unitec.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ConwayLifeGame {
	public static void main(String args[]) {
		new ConwayLifeGame();
	}

	public ConwayLifeGame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Conway's Game of Life");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                GamePanel gamePanel = new GamePanel();
                frame.add(gamePanel);
                frame.pack();
                gamePanel.Init();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
	}

	public class GamePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private int columnCount = 20;
        private int rowCount = 20;
        private List<Rectangle> cells;
        private int[][] currentGeneration;
        private int[][] nextGeneration;

        public GamePanel() {
            cells = new ArrayList<>(columnCount * rowCount);
            currentGeneration = new int[columnCount][rowCount];
            nextGeneration = new int[columnCount][rowCount];

            for(int i = 0; i < columnCount; i++) {
            	for(int j = 0; j < rowCount; j++) {
            		currentGeneration[i][j] = 0;
            		nextGeneration[i][j] = 0;
            	}
            }

            MouseAdapter mouseHandler;
            mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                	ProduceNextGeneration();
                    repaint();
                }
            };
            addMouseListener(mouseHandler);
        }

        private void ProduceNextGeneration() {
            for(int i = 1; i < columnCount - 1; i++) {
            	for(int j = 1; j < rowCount - 1; j++) {
            		int neighbors = 
            				currentGeneration[i - 1][j - 1] + 
                			currentGeneration[i][j - 1] + 
                			currentGeneration[i + 1][j - 1] + 
                			currentGeneration[i - 1][j] + 
//                			currentGeneration[i][j] + 
                			currentGeneration[i + 1][j] + 
                			currentGeneration[i - 1][j + 1] + 
                			currentGeneration[i][j + 1] + 
                			currentGeneration[i + 1][j + 1];

            		nextGeneration[i][j] = 0;

            		/// Rule 1
            		if((currentGeneration[i][j] != 0) && (neighbors < 2)) {
            			nextGeneration[i][j] = 0;
            		/// Rule 2
            		} else if((currentGeneration[i][j] != 0) && ((neighbors == 2) || (neighbors == 3))) {
            			nextGeneration[i][j] = 1;
            		/// Rule 3
            		} else if((currentGeneration[i][j] != 0) && (neighbors > 3)) {
            			nextGeneration[i][j] = 0;
            		/// Rule 4
            		} else if((currentGeneration[i][j] == 0) && (neighbors == 3)) {
            			nextGeneration[i][j] = 1;
            		}
            	}
            }

            for(int i = 0; i < columnCount; i++) {
            	for(int j = 0; j < rowCount; j++) {
            		currentGeneration[i][j] = nextGeneration[i][j];
            	}
            }
        }

        public void Init() {
//        	int[][] field20x20 = new int[][] {
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//        	};

//        	/// Glider
//        	int[][] glider = new int[][] {
//        			{0, 1, 0}, 
//        			{0, 0, 1}, 
//        			{1, 1, 1}, 
//        	};
//
//        	for(int i = 0; i < 3; i++) {
//        		int tmp = glider[i][0];
//        		glider[i][0] = glider[0][i];
//        		glider[0][i] = tmp;
//        	}
//
//        	int baseX = columnCount / 2 - 1;
//        	int baseY = rowCount / 2 - 1;
//        	
//        	for(int i = 0; i < 3; i++) {
//        		for(int j = 0; j < 3; j++) {
//        			currentGeneration[baseX + i][baseY + j] = glider[i][j];
//        		}
//        	}
        	
            for(int i = 0; i < columnCount; i++) {
            	for(int j = 0; j < rowCount; j++) {
            		currentGeneration[i][j] = ThreadLocalRandom.current().nextInt(0, 1 + 1);
            	}
            }
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        public void invalidate() {
            cells.clear();
            super.invalidate();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            int width = getWidth();
            int height = getHeight();

            int cellWidth = width / columnCount;
            int cellHeight = height / rowCount;

            int xOffset = (width - (columnCount * cellWidth)) / 2;
            int yOffset = (height - (rowCount * cellHeight)) / 2;

            if (cells.isEmpty()) {
                for (int row = 0; row < rowCount; row++) {
                    for (int col = 0; col < columnCount; col++) {
                        Rectangle cell = new Rectangle(
                                xOffset + (col * cellWidth),
                                yOffset + (row * cellHeight),
                                cellWidth,
                                cellHeight);
                        cells.add(cell);
                    }
                }
            }

            for(int i = 0; i < columnCount; i++) {
            	for(int j = 0; j < rowCount; j++) {
            		if(currentGeneration[i][j] != 0) {
                        int index = i + (j * columnCount);
                        Rectangle cell = cells.get(index);
                        g2d.setColor(Color.BLUE);
                        g2d.fill(cell);
            		}
            	}
            }

            g2d.setColor(Color.GRAY);
            for (Rectangle cell : cells) {
                g2d.draw(cell);
            }

            g2d.dispose();
        }
	}
}
