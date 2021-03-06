package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import nz.ac.unitec.main.GameFieldPanel;

public class TestCaseRule3 {

	@Test
	public void test() {
		GameFieldPanel pnlGameField = GameFieldPanel.GetInstance();
		
		///--- OVERPOPULATION DEATH TEST (RULE 3) ---///
		pnlGameField.ClearGameField();
		Point point = pnlGameField.PlaceRandomCell();
		int x = (int)point.getX();
		int y = (int)point.getY();
		
		/// Place more than 3 (4 to 8) random neighbors
		pnlGameField.PlaceRandomNeighbors(x, y, ThreadLocalRandom.current().nextInt(4, 8 + 1));
		
		assertEquals(1, pnlGameField.GetCell(x, y));					// Cell is alive
		pnlGameField.NextGeneration();									// Produce next generation
		assertEquals(0,  pnlGameField.GetCell(x, y));					// Cell is dead
	}

}
