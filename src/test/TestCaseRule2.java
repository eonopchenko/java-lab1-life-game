package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import nz.ac.unitec.main.GameFieldPanel;

public class TestCaseRule2 {

	@Test
	public void test() {
		GameFieldPanel pnlGameField = GameFieldPanel.GetInstance();
		
		///--- CELL SURVIVAL TEST (RULE 2) ---///
		pnlGameField.ClearGameField();
		Point point = pnlGameField.PlaceRandomCell();
		int x = (int)point.getX();
		int y = (int)point.getY();
		
		/// Place 2 or 3 random neighbors
		pnlGameField.PlaceRandomNeighbors(x, y, ThreadLocalRandom.current().nextInt(2, 3 + 1));
		
		assertEquals(1, pnlGameField.GetCell(x, y));					// Cell is alive
		pnlGameField.NextGeneration();									// Produce next generation
		assertEquals(1,  pnlGameField.GetCell(x, y));					// Cell is still alive
	}

}
