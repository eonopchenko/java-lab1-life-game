package nz.ac.unitec.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameFieldPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/// Supported patterns
	public enum Pattern {
		CELL,
		GLIDER,
		R_PENTONIMO,
		SPACESHIP_1,
		GLIDER_GUN,
		GLIDER_EATER,
	}
	
	/// Glider
    private final int patternGlider[][] = {
    		{0, 1, 0}, 
    		{0, 0, 1}, 
    		{1, 1, 1},
    };
    
    /// R-Pentonimo
    private final int patternRPentonimo[][] = {
    		{0, 1, 1}, 
    		{1, 1, 0}, 
    		{0, 1, 0},
    };
    
    /// Spaceship 1
    private final int patternSpaceship1[][] = {
    		{0, 1, 1, 1, 1, 1, 1},
    		{1, 0, 0, 0, 0, 0, 1},
    		{0, 0, 0, 0, 0, 0, 1},
    		{1, 0, 0, 0, 0, 1, 0},
    		{0, 0, 1, 1, 0, 0, 0},
    };
    
    /// Glider Gun
    private final int patternGliderGun[][] = {
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}, 
        	{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
    		{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
    		{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
    };
    
    /// Glider Eater
    private final int patternGliderEater[][] = {
    		{1, 1, 0, 0}, 
    		{1, 0, 1, 0}, 
    		{0, 0, 1, 0}, 
    		{0, 0, 1, 1}, 
    };

    private UpdateGenLabelInterface updateGenLabel;
	private final int colCount = 100;
    private final int rowCount = 100;
	private final List<Rectangle> cells;
    private int[][] gen;
    private int[][] genTmp;
    private Pattern pattern;
    private Timer timer;
    private boolean temp;
	
    /// Constructor
	public GameFieldPanel() {
		cells = new ArrayList<>(colCount * rowCount);
        gen = new int[colCount][rowCount];
        genTmp = new int[colCount][rowCount];
        pattern = Pattern.CELL;
        temp = false;
        
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for(int col = 0; col < colCount; col++) {
                	for(int row = 0; row < rowCount; row++) {
                		gen[col][row] = genTmp[col][row];
                	}
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                for(int col = 0; col < colCount; col++) {
                	for(int row = 0; row < rowCount; row++) {
                		genTmp[col][row] = gen[col][row];
                	}
                }
                
                int width = getWidth();
                int height = getHeight();
                
                int cellWidth = width / colCount;
                int cellHeight = height / rowCount;
                
                int xOffset = (width - (colCount * cellWidth)) / 2;
                int yOffset = (height - (rowCount * cellHeight)) / 2;
                
                int col = (e.getX() - xOffset) / cellWidth;
                int row = (e.getY() - yOffset) / cellHeight;
                
                if((col >= 0) && (row >= 0) && (col < colCount) && (row < rowCount)) {
                    if(pattern == Pattern.CELL) {
                    	genTmp[col][row] ^= 1;
                    } else if(pattern == Pattern.GLIDER) {
                    	placePattern(col, row, patternGlider);
                    } else if(pattern == Pattern.R_PENTONIMO) {
                    	placePattern(col, row, patternRPentonimo);
                    } else if(pattern == Pattern.SPACESHIP_1) {
                    	placePattern(col, row, patternSpaceship1);
                    } else if(pattern == Pattern.GLIDER_GUN) {
                    	placePattern(col, row, patternGliderGun);
                	} else if(pattern == Pattern.GLIDER_EATER) {
                		placePattern(col, row, patternGliderEater);
                    }
                }
                
                temp = true;
                repaint();
            }
        };
        
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
	}
	
	/// Set generation label update callback
	public void SetUpdateGenLabelCallback(UpdateGenLabelInterface updateGenLabel) {
		this.updateGenLabel = updateGenLabel;
	}
    
    @Override
    public void invalidate() {
        super.invalidate();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        int width = getWidth();
        int height = getHeight();
        
        int cellWidth = width / colCount;
        int cellHeight = height / rowCount;
        
        int xOffset = (width - (colCount * cellWidth)) / 2;
        int yOffset = (height - (rowCount * cellHeight)) / 2;
        
        cells.clear();
        for (int col = 0; col < colCount; col++) {
        	for (int row = 0; row < rowCount; row++) {
        		Rectangle cell = new Rectangle(
        				xOffset + (col * cellWidth),
        				yOffset + (row * cellHeight),
        				cellWidth,
        				cellHeight);
        		cells.add(cell);
        	}
        }
        
        for(int col = 0; col < colCount; col++) {
        	for(int row = 0; row < rowCount; row++) {
        		if((temp ? genTmp[col][row] : gen[col][row]) != 0) {
	        		int index = row + (col * colCount);
	        		Rectangle cell = cells.get(index);
	        		g2d.setColor(Color.MAGENTA);
	        		g2d.fill(cell);
        		}
        	}
        }
        
        temp = false;
        
        g2d.setColor(Color.GRAY);
        for (Rectangle cell : cells) {
            g2d.draw(cell);
        }
        
        g2d.dispose();
    }
	
    
    ///--- GAME LOGIC IMPLEMENTATION ---///
    
    /// Placing a pattern on the game field
    private void placePattern(int x, int y, int[][] pattern) {
		int rows = pattern.length;
		int cols = pattern[0].length;
		
		if((x <= (colCount - cols)) && (y <= (rowCount - rows))) {
			for (int col = 0; col < cols; col++) {
				for (int row = 0; row < rows; row++) {
					genTmp[x + col][y + row] = pattern[row][col];
				}
			}
		}
    }
    
    /// Setting a pattern to place
	public void SetPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	/// Clearing the game field
	public void ClearGameField() {
        for(int col = 0; col < colCount; col++) {
        	for(int row = 0; row < rowCount; row++) {
        		gen[col][row] = genTmp[col][row] = 0;
        	}
        }
        repaint();
        updateGenLabel.resetGenLabelEvent();
	}
	
	/// Placing of a random generation
	public void RandomGeneration() {
		for(int col = 0; col < colCount; col++) {
			for(int row = 0; row < rowCount; row++) {
				int rand = ThreadLocalRandom.current().nextInt(0, 1 + 1);
	        		gen[col][row] = genTmp[col][row] = rand;
	        }
		}
        repaint();
        updateGenLabel.resetGenLabelEvent();
	}
	
	/// Calculation of a new generation
	public void NextGeneration() {
		int[][] next = new int[colCount][rowCount];
		
        for(int col = 0; col < colCount; col++) {
        	for(int row = 0; row < rowCount; row++) {
        		int neighbors = 
        				gen[col == 0 ? colCount - 1 : col - 1][row == 0 ? rowCount - 1 : row - 1] + 
            			gen[col][row == 0 ? rowCount - 1 : row - 1] + 
            			gen[col == colCount - 1 ? 0 : col + 1][row == 0 ? rowCount - 1 : row - 1] + 
            			gen[col == 0 ? colCount - 1 : col - 1][row] + 
            			gen[col == colCount - 1 ? 0 : col + 1][row] + 
            			gen[col == 0 ? colCount - 1 : col - 1][row == rowCount - 1 ? 0 : row + 1] + 
            			gen[col][row == rowCount - 1 ? 0 : row + 1] + 
            			gen[col == colCount - 1 ? 0 : col + 1][row == rowCount - 1 ? 0 : row + 1];
        		
        		next[col][row] = 0;
        		
        		/// Rule 1
        		if((gen[col][row] != 0) && (neighbors < 2)) {
        			next[col][row] = 0;
        		/// Rule 2
        		} else if((gen[col][row] != 0) && ((neighbors == 2) || (neighbors == 3))) {
        			next[col][row] = 1;
        		/// Rule 3
        		} else if((gen[col][row] != 0) && (neighbors > 3)) {
        			next[col][row] = 0;
        		/// Rule 4
        		} else if((gen[col][row] == 0) && (neighbors == 3)) {
        			next[col][row] = 1;
        		}
        	}
        }
        
        for(int i = 0; i < colCount; i++) {
        	for(int j = 0; j < rowCount; j++) {
        		gen[i][j] = next[i][j];
        	}
        }
        
        repaint();
        updateGenLabel.incGenLabelEvent();
	}
	
	/// Game process thread
	public class GameThread extends TimerTask {
	    public void run() {
	    	NextGeneration();
	    }
	}
	
	/// Start the game
	public void Start() {
		timer = new Timer();
		timer.schedule(new GameThread(), 0, 10);
	}
	
	/// Stop the game
	public void Stop() {
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	/// Load game field
	public void LoadGameField(BufferedReader br) throws IOException {
		ClearGameField();
        String line = "";
        int row = 0;
        while ((line = br.readLine()) != null) {
            String[] lines = line.split(",");
            for (int col = 0; col < lines.length; col++) {
            	gen[col][row] = lines[col].equals("\"0\"") ? 0 : 1;
            }
            row++;
        }
	}
	
	/// Save game field
	public String[][] SaveGameField() {
		String str[][] = new String[rowCount][colCount];
		for(int col = 0; col < colCount; col++) {
			for(int row = 0; row < rowCount; row++) {
				str[row][col] = (gen[col][row] == 0 ? "\"0\"" : "\"1\"") + ',';
        	}
        }
		return str;
	}
	
}
