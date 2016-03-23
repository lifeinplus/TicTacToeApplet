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
	
	// Получаем ссылки на родительский и основной классы
	public PlayViewController(PlayView parent, Main root) {
		this.parent = parent;
		this.root = root;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Определяем значение ActionCommand в событии
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
		// Переменная для хранения нажатой ячейки
		GameCell currentCell = (GameCell) e.getComponent();
		
		// return, если клик произошёл по неактивной ячейке
		if (!currentCell.isEnabled()) return;
		
		// Если удалось установить значение в ячейку
		if (checkCell(currentCell)) {
			// return, если игра закончена
			if (ApplicationData.gameOver) return;
			// Ход компьютера, если режим игры PLAYER_VS_COMPUTER
			if (ApplicationData.playMode == ApplicationData.PLAYER_VS_COMPUTER) computerTurn();
		}
	} // end mouseClicked()


	// Ход компьютера
	private void computerTurn() {
		boolean success = true;
		int x, y;
		GameCell currentCell;
		
		do {
			// Генерация координат
			x = new Random().nextInt(3);
			y = new Random().nextInt(3);
			
			// Назначаем ячейку по сгенерированным координатам
			currentCell = parent.cells[x][y];
			
			// Проверяем, удалось ли установить значение в ячейку
			success = checkCell(currentCell);
		// Если установить значение не удалось, цикл повторяется
		} while (!success );
	} // end computerTurn()


	// Установка значения в ячейку
	private boolean checkCell(GameCell currentCell) {
		// Если ячейка пуста
		if (currentCell.getText() == "") {
			// Устанавилваем в неё значение PLAYERX_VALUE либо PLAYERO_VALUE
			currentCell.setText(ApplicationData.getPlayerValue());
			
			// true, eсли был обнаружен победитель
			if (checkForWinner(currentCell)) return true;
			// true, если была зафиксирована ничья
			if (checkStalemate()) return true;
			
			// Если победитель не обнаружен и остались
			// пустые ячейки, меняем ход игрока
			ApplicationData.switchCurrentPlayer();
			
			// Получаем значение переменной текущего игрока
			// Устанавливаем примечание об очереди хода в поле комментария
			parent.setCurrentPlayerNote(ApplicationData.getCurrentPlayerName());
			
			return true;
		}
		
		// false, так как установить значение в ячейку не удалось
		return false;
	} // end checkCell()


	// Проверка статуса игры на ничью
	private boolean checkStalemate() {
		// false, если есть пустые ячейки
		for (int i = 0; i < parent.cells.length; i++) {
			for (int j = 0; j < parent.cells[i].length; j++) {
				if (parent.cells[i][j].getText().equals("")) return false;
			}
		}
		
		// Делаем ячейки формы неактивными
		parent.setBlockState();
		// Устанавливаем текст о ничье в поле комментария
		parent.setStalemateText();
		
		// Изменяем значение переменной статуса окончания игры
		ApplicationData.gameOver = true;
		
		return true;
	} // end checkStalemate()


	// Проверка наличия победителя
	private boolean checkForWinner(GameCell currentCell) {
		// Если строка заполнена одинаковыми ячейками
		if (findFullRow(currentCell)) {
			// Делаем ячейки формы неактивными
			parent.setBlockState();
			
			// Получаем имя текущего игрока
			// Устанавливаем текст с поздравлением в поле комментария
			parent.setWinnerText(ApplicationData.getCurrentPlayerName());
			
			// Изменяем значение переменной статуса окончания игры
			ApplicationData.gameOver = true;
			
			return true;
		}
		
		// false, если строка с одинаковыми ячейками отсутствует
		return false;
	} // end checkForWinner()


	// Проверка строк на равенство ячеек
	private boolean findFullRow(GameCell currentCell) {
		// Массив для хранения значений ячеек по одной линии
		GameCell[] line;
		
		// Получаем содержимое ячеек строки по горизонтали
		line = getLine(currentCell, HOR_LINE);
		// true, если значения ячеек горизонтальной строки равны
		if (checkLine(line)) return true;
		
		// Получаем содержимое ячеек строки по вертикали
		line = getLine(currentCell, VER_LINE);
		// true, если значения ячеек вертикальной строки равны
		if (checkLine(line)) return true;

		// Получаем содержимое ячеек 0,0, 1,1 и 2,2
		line = getLine(currentCell, DIAG_LINE);
		// true, если значения ячеек 0,0, 1,1 и 2,2 равны
		if (checkLine(line)) return true;
		
		// Получаем содержимое ячеек 0,2, 1,1 и 2,0
		line = getLine(currentCell, OPP_DIAG_LINE);
		// true, если значения ячеек 0,2, 1,1 и 2,0 равны
		if (checkLine(line)) return true;
		
		// false, если значения ячеек не равны ни по одной из строк
		return false;
	} // end findFullRow()


	// Проверка конкретной строки на равенство ячеек
	private boolean checkLine(GameCell[] line) {
		// false, если строка отсутствует
		if (line == null) return false;
		
		// Получаем значение первой ячейки в строке
		String symbol = line[0].getText();
		
		// false, если первая ячейка в строке пуста
		if (symbol.equals("")) return false;
		
		// false, если ячейки строки имеют разные значения
		for (int i = 0; i < line.length; i++) {
			if (line[i].getText() != symbol) return false;
		}
		
		// Задаём фон строке с одинаковыми ячейками
		parent.setLineColor(line);
		
		// true, если значения ячеек равны и не пусты
		return true;
	} // end checkLine()


	// Возвращение содержимого ячеек определённой строки
	private GameCell[] getLine(GameCell currentCell, String lineType) {
		// Создаём массив на 3 значения
		GameCell[] line = new GameCell[3];
		
		switch (lineType) {
		case HOR_LINE:
			// Добавляем в массив значения ячеек горизонтальной строки
			for (int i = 0; i < line.length; i++) {
				line[i] = parent.cells[currentCell.cellX][i];
			}
			break;
			
		case VER_LINE:
			// Добавляем в массив значения ячеек вертикальной строки
			for (int i = 0; i < line.length; i++) {
				line[i] = parent.cells[i][currentCell.cellY];
			}
			break;
			
		case DIAG_LINE:
			// Проверяем, равны ли координаты нажатой ячейки по XY
			if (currentCell.cellX == currentCell.cellY) {
				// Добавляем в массив значения ячеек 0,0, 1,1, 2,2
				for (int i = 0; i < line.length; i++) {
					line[i] = parent.cells[i][i];
				}
			} else {
				return null;
			}
			break;
			
		case OPP_DIAG_LINE:
			// Если (координата Х) равна (2 - координата Y)
			if (currentCell.cellX == (2 - currentCell.cellY)) {
				// Добавляем в массив значения ячеек 0,2, 1,1 и 2,0
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
