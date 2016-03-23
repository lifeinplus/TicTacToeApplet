package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.Main;
import com.controller.PlayViewController;
import com.data.ApplicationData;
import com.view.components.GameCell;

public class PlayView extends Panel {

	private PlayViewController controller;
	
	private Panel topPanel;
	private Panel cellsPanel;
	
	private JButton playAgain;
	private JButton settings;
	private JLabel comment;

	public GameCell[][] cells;

	private Font buttonFont;

	
	public PlayView(Main parent) {
		// ������ ��������� ������-���������
		controller = new PlayViewController(this, parent);
		
		// ��������� �������� ���������� �������� ������
		this.setLayout(new BorderLayout());
		
		// ������ ���������� �������������� �������
		topPanel = new Panel();
		cellsPanel = new Panel();
		
		playAgain = new JButton("Play Again");
		settings = new JButton("Settings");
		comment = new JLabel();
		// ������ ��������� ������������ ������� ��� ������-�����
		cells = new GameCell[3][3];
		
		buttonFont = new Font("Times New Roman", Font.PLAIN, 60);
		
		// ��������� ��������� ���������� �������������� �������
		topPanel.setLayout(new FlowLayout());
		cellsPanel.setLayout(new GridLayout(3, 3));

		// ����������� ������ � ����������� ���������
		playAgain.setActionCommand(PlayViewController.PLAY_AGAIN_COMMAND);
		settings.setActionCommand(PlayViewController.GET_SETTINGS_COMMAND);
		
		// ����������� ������ �� ����������
		playAgain.addActionListener(controller);
		settings.addActionListener(controller);
		
		// �������� ������ �� �������������� ������
		topPanel.add(playAgain);
		topPanel.add(settings);
		
		// ������ ���������� ������-�����, ��������� ��������� �
		// ��������� �� �� �������������� ������
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j] = new GameCell(i, j);
				cells[i][j].addMouseListener(controller);
				cells[i][j].setFont(buttonFont);
				
				cellsPanel.add(cells[i][j]);
			}
		} // end for
		
		// ����� ������������ ��� ����-�����������
		comment.setHorizontalAlignment(SwingConstants.CENTER);
		
		// ��������� ��� ���������� �� �������� ������
		this.add(topPanel, BorderLayout.NORTH);
		this.add(cellsPanel, BorderLayout.CENTER);
		this.add(comment, BorderLayout.SOUTH);
		
		// ���������� ��������� ������-����� �� ���������
		reset();
	} // end constructor PlayView


	// ����� ��������� ������-����� �� ���������
	public void reset() {
		// ������ ������ ���������
		setCellsEnabled(true);
		
		// ������ ������ �������
		for (GameCell[] line : cells) {
			for (GameCell item : line) {
				item.setText("");
				item.setBackground(null);
			}
		}
		
		// �������� ��� �������� ������ �
		// ������������� ��� � ���� �����������
		setCurrentPlayerNote(ApplicationData.getCurrentPlayerName());
	} // end reset()


	// ��������� ���������� �� ������� ���� � ���� �����������
	public void setCurrentPlayerNote(String currentPlayerName) {
		comment.setText(currentPlayerName + " your turn.");
	}


	// ��������� ���������� ������-����� �����
	private void setCellsEnabled(boolean b) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j].setEnabled(b);
			}
		}
	} // end setCellsEnabled()


	// ��������� ���� ��� ������ � ����������� ��������
	public void setLineColor(GameCell[] line) {
		for (GameCell cell : line) {
			cell.setBackground(Color.yellow);
			cell.setOpaque(true);
		}
	} // end setLineColor()

	
	// ��������� ����������� ��������� ����� �����
	public void setBlockState() {
		setCellsEnabled(false);
	}


	// ��������� ������ � ������������� ���������� � ���� ����������
	public void setWinnerText(String currentPlayerName) {
		comment.setText(currentPlayerName.concat(" won!! Congratulations!!!"));
	}


	// ��������� ������ � ����� � ���� �����������
	public void setStalemateText() {
		comment.setText("Stalemate! Try again!");
	}
	
}
