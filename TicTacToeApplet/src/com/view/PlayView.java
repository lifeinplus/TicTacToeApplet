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
		// —оздаЄм экземпл€р класса-слушател€
		controller = new PlayViewController(this, parent);
		
		// Ќазначаем менеджер компоновки основной панеле
		this.setLayout(new BorderLayout());
		
		// —оздаЄм экземпл€ры дополнительных панелей
		topPanel = new Panel();
		cellsPanel = new Panel();
		
		playAgain = new JButton("Play Again");
		settings = new JButton("Settings");
		comment = new JLabel();
		// —оздаЄм экземпл€р многомерного массива дл€ кнопок-€чеек
		cells = new GameCell[3][3];
		
		buttonFont = new Font("Times New Roman", Font.PLAIN, 60);
		
		// Ќазначаем менеджеры компоновок дополнительным панел€м
		topPanel.setLayout(new FlowLayout());
		cellsPanel.setLayout(new GridLayout(3, 3));

		// јссоциируем кнопки с определЄнным действием
		playAgain.setActionCommand(PlayViewController.PLAY_AGAIN_COMMAND);
		settings.setActionCommand(PlayViewController.GET_SETTINGS_COMMAND);
		
		// јссоциируем кнопки со слушателем
		playAgain.addActionListener(controller);
		settings.addActionListener(controller);
		
		// ƒобал€ем кнопки на дополнительную панель
		topPanel.add(playAgain);
		topPanel.add(settings);
		
		// —оздаЄм экземпл€ры кнопок-€чеек, добавл€ем слушател€ и
		// размещаем их на дополнительной панеле
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j] = new GameCell(i, j);
				cells[i][j].addMouseListener(controller);
				cells[i][j].setFont(buttonFont);
				
				cellsPanel.add(cells[i][j]);
			}
		} // end for
		
		// «адаЄм выравнивание дл€ пол€-комментари€
		comment.setHorizontalAlignment(SwingConstants.CENTER);
		
		// –азмещаем все компоненты на основной панеле
		this.add(topPanel, BorderLayout.NORTH);
		this.add(cellsPanel, BorderLayout.CENTER);
		this.add(comment, BorderLayout.SOUTH);
		
		// —брасываем состо€ние кнопок-€чеек на начальное
		reset();
	} // end constructor PlayView


	// —брос состо€ни€ кнопок-€чеек на начальное
	public void reset() {
		// ƒелаем €чейки активными
		setCellsEnabled(true);
		
		// ƒелаем €чейки пустыми
		for (GameCell[] line : cells) {
			for (GameCell item : line) {
				item.setText("");
				item.setBackground(null);
			}
		}
		
		// ѕолучаем им€ текущего игрока и
		// устанавливаем его в поле комментари€
		setCurrentPlayerNote(ApplicationData.getCurrentPlayerName());
	} // end reset()


	// ”становка примечани€ об очереди хода в поле комментари€
	public void setCurrentPlayerNote(String currentPlayerName) {
		comment.setText(currentPlayerName + " your turn.");
	}


	// ”становка активности кнопок-€чеек формы
	private void setCellsEnabled(boolean b) {
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j].setEnabled(b);
			}
		}
	} // end setCellsEnabled()


	// ”становка фона дл€ строки с одинаковыми €чейками
	public void setLineColor(GameCell[] line) {
		for (GameCell cell : line) {
			cell.setBackground(Color.yellow);
			cell.setOpaque(true);
		}
	} // end setLineColor()

	
	// ”становка неактивного состо€ни€ €чеек формы
	public void setBlockState() {
		setCellsEnabled(false);
	}


	// ”становка текста с поздравлением победител€ в поле комметари€
	public void setWinnerText(String currentPlayerName) {
		comment.setText(currentPlayerName.concat(" won!! Congratulations!!!"));
	}


	// ”становка текста о ничье в поле комментари€
	public void setStalemateText() {
		comment.setText("Stalemate! Try again!");
	}
	
}
