package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import nz.ac.unitec.main.GameFieldPanel;

public class TestCaseRule4 {

	@Test
	public void test() {
		GameFieldPanel pnlGameField = GameFieldPanel.GetInstance();
		
		///--- NEW CELL BORN TEST (RULE 4) ---///
		pnlGameField.ClearGameField();
		Point point = pnlGameField.GetRandomCell();
		int x = (int)point.getX();
		int y = (int)point.getY();
		
		/// Place exactly 3 random neighbors
		pnlGameField.PlaceRandomNeighbors(x, y, 3);
		
		assertEquals(0, pnlGameField.GetCell(x, y));					// Cell is dead
		pnlGameField.NextGeneration();									// Produce next generation
		assertEquals(1, pnlGameField.GetCell(x, y));					// Cell is alive
	}

}
