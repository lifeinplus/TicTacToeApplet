package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.Main;
import com.data.ApplicationData;
import com.view.SettingsView;

public class SettingsViewController implements ActionListener {

	public static final String PLAYER_VS_PLAYER = "PLAYER_VS_PLAYER";
	public static final String PLAYER_VS_COMPUTER = "PLAYER_VS_COMPUTER";
	
	private SettingsView parent;
	private Main root;
	
	
	// Передаём ссылки на родительский и основной классы
	public SettingsViewController(SettingsView parent, Main root) {
		this.parent = parent;
		this.root = root;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Определяем значение ActionCommand в событии
		switch (e.getActionCommand()) {
			case PLAYER_VS_PLAYER:
				// Устанавливаем режим игры "Player vs Player"
				ApplicationData.playMode = ApplicationData.PLAYER_VS_PLAYER;
				break;
	
			case PLAYER_VS_COMPUTER:
				// Устанавливаем режим игры "Player vs Computer"
				ApplicationData.playMode = ApplicationData.PLAYER_VS_COMPUTER;
				break;
		} // end switch
		
		// Инициализируем панель игры
		root.play();
	} // end method actionPerformed

}
