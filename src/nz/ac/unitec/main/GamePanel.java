/*
 * @note game field design borrowed on 
 * @note https://stackoverflow.com/questions/15421708/how-to-draw-grid-using-swing-class-java-and-detect-mouse-position-when-click-and
 */


package nz.ac.unitec.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int columnCount = 100;
    private int rowCount = 100;
	private List<Rectangle> cells;
    private int[][] generation;
    Timer timer;

	GamePanel() {
		cells = new ArrayList<>(columnCount * rowCount);
        generation = new int[columnCount][rowCount];
        
        MouseAdapter mouseHandler;
        mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int width = getWidth();
                int height = getHeight();

                int cellWidth = width / columnCount;
                int cellHeight = height / rowCount;

                if (e.getX() >= 0 && e.getY() >= 0) {

                    int column = (e.getX() - 0) / cellWidth - 1;
                    int row = (e.getY() - 0) / cellHeight - 1;

                    if (column >= 0 && row >= 0 && column < columnCount && row < rowCount) {
                    	if(generation[column][row] == 0) {
                    		generation[column][row] = 1;
                    	} else {
                    		generation[column][row] = 0;
                    	}
                    }

                }
                repaint();

            }
        };
        addMouseListener(mouseHandler);
	}
	
	public void Clear() {
        for(int i = 0; i < columnCount; i++) {
        	for(int j = 0; j < rowCount; j++) {
        		generation[i][j] = 0;
        	}
        }
        repaint();
	}
	
	public void ZeroGeneration(int type) {
		if(type == 0) {
	        for(int i = 0; i < columnCount; i++) {
	        	for(int j = 0; j < rowCount; j++) {
	        		generation[i][j] = ThreadLocalRandom.current().nextInt(0, 1 + 1);
	        	}
	        }
		} else if (type == 1) {
			JOptionPane.showMessageDialog(null, "Here should be Glider!", "Generation", JOptionPane.PLAIN_MESSAGE);
		}
		
        repaint();
	}
	
	public void NextGeneration() {
		int[][] next = new int[columnCount][rowCount];
		
        for(int i = 0; i < columnCount; i++) {
        	for(int j = 0; j < rowCount; j++) {
//        		if((i == 0) || (j == 0) || (i == columnCount - 1) || (j == rowCount - 1)) {
//        			continue;
//        		}
        		
        		int neighbors = 
        				generation[i == 0 ? columnCount - 1 : i - 1][j == 0 ? rowCount - 1 : j - 1] + 
            			generation[i][j == 0 ? rowCount - 1 : j - 1] + 
            			generation[i == columnCount - 1 ? 0 : i + 1][j == 0 ? rowCount - 1 : j - 1] + 
            			generation[i == 0 ? columnCount - 1 : i - 1][j] + 
            			generation[i == columnCount - 1 ? 0 : i + 1][j] + 
            			generation[i == 0 ? columnCount - 1 : i - 1][j == rowCount - 1 ? 0 : j + 1] + 
            			generation[i][j == rowCount - 1 ? 0 : j + 1] + 
            			generation[i == columnCount - 1 ? 0 : i + 1][j == rowCount - 1 ? 0 : j + 1];
        		
        		next[i][j] = 0;
        		
        		/// Rule 1
        		if((generation[i][j] != 0) && (neighbors < 2)) {
        			next[i][j] = 0;
        		/// Rule 2
        		} else if((generation[i][j] != 0) && ((neighbors == 2) || (neighbors == 3))) {
        			next[i][j] = 1;
        		/// Rule 3
        		} else if((generation[i][j] != 0) && (neighbors > 3)) {
        			next[i][j] = 0;
        		/// Rule 4
        		} else if((generation[i][j] == 0) && (neighbors == 3)) {
        			next[i][j] = 1;
        		}
        	}
        }

        for(int i = 0; i < columnCount; i++) {
        	for(int j = 0; j < rowCount; j++) {
        		generation[i][j] = next[i][j];
        	}
        }
        
        repaint();
	}
	
	public class GameThread extends TimerTask {
	    public void run() {
	    	NextGeneration();
	    }
	}
	
	public void Start() {
		timer = new Timer();
		timer.schedule(new GameThread(), 0, 10);
	}
	
	public void Stop() {
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(700, 500);
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
        		if(generation[i][j] != 0) {
                    int index = i + (j * columnCount);
                    Rectangle cell = cells.get(index);
                    g2d.setColor(Color.MAGENTA);
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

