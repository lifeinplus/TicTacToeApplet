package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import com.Main;
import com.data.ApplicationData;
import com.view.PlayView;
import com.view.components.GameCell;

public class PlayViewController extends MouseAdapter implements MouseListener, ActionListener {

	public static final String PLAY_AGAIN_COMMAND = "play again command";
	public static final String GET_SETTINGS_COMMAND = "get settings command";

	private static final String HOR_LINE = "horizontal line";
	private static final String VER_LINE = "vertical line";
	private static final String DIAG_LINE = "diagonal line";
	private static final String OPP_DIAG_LINE = "opposit diagonal line";
	
	private PlayView parent;
	private Main root;
	
	// �������� ������ �� ������������ � �������� ������
	public PlayViewController(PlayView parent, Main root) {
		this.parent = parent;
		this.root = root;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// ���������� �������� ActionCommand � �������
		switch (e.getActionCommand()) {
		case PLAY_AGAIN_COMMAND:
			reset();
			break;
			
		case GET_SETTINGS_COMMAND:
			reset();
			root.settings();
			break;

		default:
			break;
		}
	} // end actionPerformed()


	@Override
	public void mouseClicked(MouseEvent e) {
		// ���������� ��� �������� ������� ������
		GameCell currentCell = (GameCell) e.getComponent();
		
		// return, ���� ���� ��������� �� ���������� ������
		if (!currentCell.isEnabled()) return;
		
		// ���� ������� ���������� �������� � ������
		if (checkCell(currentCell)) {
			// return, ���� ���� ���������
			if (ApplicationData.gameOver) return;
			// ��� ����������, ���� ����� ���� PLAYER_VS_COMPUTER
			if (ApplicationData.playMode == ApplicationData.PLAYER_VS_COMPUTER) computerTurn();
		}
	} // end mouseClicked()


	// ��� ����������
	private void computerTurn() {
		boolean success = true;
		int x, y;
		GameCell currentCell;
		
		do {
			// ��������� ���������
			x = new Random().nextInt(3);
			y = new Random().nextInt(3);
			
			// ��������� ������ �� ��������������� �����������
			currentCell = parent.cells[x][y];
			
			// ���������, ������� �� ���������� �������� � ������
			success = checkCell(currentCell);
		// ���� ���������� �������� �� �������, ���� �����������
		} while (!success );
	} // end computerTurn()


	// ��������� �������� � ������
	private boolean checkCell(GameCell currentCell) {
		// ���� ������ �����
		if (currentCell.getText() == "") {
			// ������������� � �� �������� PLAYERX_VALUE ���� PLAYERO_VALUE
			currentCell.setText(ApplicationData.getPlayerValue());
			
			// true, e��� ��� ��������� ����������
			if (checkForWinner(currentCell)) return true;
			// true, ���� ���� ������������� �����
			if (checkStalemate()) return true;
			
			// ���� ���������� �� ��������� � ��������
			// ������ ������, ������ ��� ������
			ApplicationData.switchCurrentPlayer();
			
			// �������� �������� ���������� �������� ������
			// ������������� ���������� �� ������� ���� � ���� �����������
			parent.setCurrentPlayerNote(ApplicationData.getCurrentPlayerName());
			
			return true;
		}
		
		// false, ��� ��� ���������� �������� � ������ �� �������
		return false;
	} // end checkCell()


	// �������� ������� ���� �� �����
	private boolean checkStalemate() {
		// false, ���� ���� ������ ������
		for (int i = 0; i < parent.cells.length; i++) {
			for (int j = 0; j < parent.cells[i].length; j++) {
				if (parent.cells[i][j].getText().equals("")) return false;
			}
		}
		
		// ������ ������ ����� �����������
		parent.setBlockState();
		// ������������� ����� � ����� � ���� �����������
		parent.setStalemateText();
		
		// �������� �������� ���������� ������� ��������� ����
		ApplicationData.gameOver = true;
		
		return true;
	} // end checkStalemate()


	// �������� ������� ����������
	private boolean checkForWinner(GameCell currentCell) {
		// ���� ������ ��������� ����������� ��������
		if (findFullRow(currentCell)) {
			// ������ ������ ����� �����������
			parent.setBlockState();
			
			// �������� ��� �������� ������
			// ������������� ����� � ������������� � ���� �����������
			parent.setWinnerText(ApplicationData.getCurrentPlayerName());
			
			// �������� �������� ���������� ������� ��������� ����
			ApplicationData.gameOver = true;
			
			return true;
		}
		
		// false, ���� ������ � ����������� �������� �����������
		return false;
	} // end checkForWinner()


	// �������� ����� �� ��������� �����
	private boolean findFullRow(GameCell currentCell) {
		// ������ ��� �������� �������� ����� �� ����� �����
		GameCell[] line;
		
		// �������� ���������� ����� ������ �� �����������
		line = getLine(currentCell, HOR_LINE);
		// true, ���� �������� ����� �������������� ������ �����
		if (checkLine(line)) return true;
		
		// �������� ���������� ����� ������ �� ���������
		line = getLine(currentCell, VER_LINE);
		// true, ���� �������� ����� ������������ ������ �����
		if (checkLine(line)) return true;

		// �������� ���������� ����� 0,0, 1,1 � 2,2
		line = getLine(currentCell, DIAG_LINE);
		// true, ���� �������� ����� 0,0, 1,1 � 2,2 �����
		if (checkLine(line)) return true;
		
		// �������� ���������� ����� 0,2, 1,1 � 2,0
		line = getLine(currentCell, OPP_DIAG_LINE);
		// true, ���� �������� ����� 0,2, 1,1 � 2,0 �����
		if (checkLine(line)) return true;
		
		// false, ���� �������� ����� �� ����� �� �� ����� �� �����
		return false;
	} // end findFullRow()


	// �������� ���������� ������ �� ��������� �����
	private boolean checkLine(GameCell[] line) {
		// false, ���� ������ �����������
		if (line == null) return false;
		
		// �������� �������� ������ ������ � ������
		String symbol = line[0].getText();
		
		// false, ���� ������ ������ � ������ �����
		if (symbol.equals("")) return false;
		
		// false, ���� ������ ������ ����� ������ ��������
		for (int i = 0; i < line.length; i++) {
			if (line[i].getText() != symbol) return false;
		}
		
		// ����� ��� ������ � ����������� ��������
		parent.setLineColor(line);
		
		// true, ���� �������� ����� ����� � �� �����
		return true;
	} // end checkLine()


	// ����������� ����������� ����� ����������� ������
	private GameCell[] getLine(GameCell currentCell, String lineType) {
		// ������ ������ �� 3 ��������
		GameCell[] line = new GameCell[3];
		
		switch (lineType) {
		case HOR_LINE:
			// ��������� � ������ �������� ����� �������������� ������
			for (int i = 0; i < line.length; i++) {
				line[i] = parent.cells[currentCell.cellX][i];
			}
			break;
			
		case VER_LINE:
			// ��������� � ������ �������� ����� ������������ ������
			for (int i = 0; i < line.length; i++) {
				line[i] = parent.cells[i][currentCell.cellY];
			}
			break;
			
		case DIAG_LINE:
			// ���������, ����� �� ���������� ������� ������ �� XY
			if (currentCell.cellX == currentCell.cellY) {
				// ��������� � ������ �������� ����� 0,0, 1,1, 2,2
				for (int i = 0; i < line.length; i++) {
					line[i] = parent.cells[i][i];
				}
			} else {
				return null;
			}
			break;
			
		case OPP_DIAG_LINE:
			// ���� (���������� �) ����� (2 - ���������� Y)
			if (currentCell.cellX == (2 - currentCell.cellY)) {
				// ��������� � ������ �������� ����� 0,2, 1,1 � 2,0
				for (int i = 0; i < line.length; i++) {
					line[i] = parent.cells[i][2 - i];
				}
			} else {
				return null;
			}
			break;

		default:
			break;
		} // end switch
		
		return line;
	} // end getLine()


	private void reset() {
		ApplicationData.setDefaultSettings();
		parent.reset();
	}

}
