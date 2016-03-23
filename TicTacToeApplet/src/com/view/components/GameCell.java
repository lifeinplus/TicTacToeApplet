package com.view.components;

import javax.swing.JButton;

public class GameCell extends JButton {

	public int cellX;
	public int cellY;

	public GameCell(int cellX, int cellY) {
		this.cellX = cellX;
		this.cellY = cellY;
	}
	
}
