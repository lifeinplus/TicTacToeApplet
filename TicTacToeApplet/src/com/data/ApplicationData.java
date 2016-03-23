package com.data;

public class ApplicationData {

	public static final String PLAYER_VS_PLAYER = "player";
	public static final String PLAYER_VS_COMPUTER = "computer";
	
	private static final String PLAYERX = "Player X";
	private static final String PLAYERO = "Player O";
	private static final String PLAYERX_VALUE = "X";
	private static final String PLAYERO_VALUE = "O";
	
	public static String playMode = PLAYER_VS_PLAYER;
	
	private static String currentPlayer;
	public static boolean gameOver;

	
	// ����������� ����� �������� ������
	public static String getCurrentPlayerName() {
		if (currentPlayer == null) setDefaultSettings();
		return currentPlayer;
	}

	
	// ��������� �������� ���� �� ���������
	public static void setDefaultSettings() {
		currentPlayer = PLAYERX;
		gameOver = false;
	}


	public static String getPlayerValue() {
		switch (currentPlayer) {
		case PLAYERX:
			return PLAYERX_VALUE;
			
		case PLAYERO:
			return PLAYERO_VALUE;

		default:
			setDefaultSettings();
			return getPlayerValue();
		}
	} // end getPlayerValue()


	// ������ ��� ������
	public static void switchCurrentPlayer() {
		switch (currentPlayer) {
		case PLAYERX:
			currentPlayer = PLAYERO;
			break;
			
		case PLAYERO:
			currentPlayer = PLAYERX;
			break;

		default:
			setDefaultSettings();
			break;
		}
	} // end switchCurrentPlayer()
	
}
