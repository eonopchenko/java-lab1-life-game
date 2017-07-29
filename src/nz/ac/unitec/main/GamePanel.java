package nz.ac.unitec.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int columnCount = 20;
    private int rowCount = 20;
	private List<Rectangle> cells;
    private int[][] generation;
    Timer timer;

	GamePanel() {
		cells = new ArrayList<>(columnCount * rowCount);
        generation = new int[columnCount][rowCount];
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
		
        for(int i = 1; i < columnCount - 1; i++) {
        	for(int j = 1; j < rowCount - 1; j++) {
        		int neighbors = 
        				generation[i - 1][j - 1] + 
            			generation[i][j - 1] + 
            			generation[i + 1][j - 1] + 
            			generation[i - 1][j] + 
//            			generation[i][j] + 
            			generation[i + 1][j] + 
            			generation[i - 1][j + 1] + 
            			generation[i][j + 1] + 
            			generation[i + 1][j + 1];

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
	
	public class HelloThread extends TimerTask {
	    public void run() {
	    	NextGeneration();
	    }
	}
	
	public void Start() {
		timer = new Timer();
		timer.schedule(new HelloThread(), 0, 100);
	}
	
	public void Stop() {
		timer.cancel();
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

