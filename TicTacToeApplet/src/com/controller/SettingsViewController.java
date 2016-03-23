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
	
	
	// ������� ������ �� ������������ � �������� ������
	public SettingsViewController(SettingsView parent, Main root) {
		this.parent = parent;
		this.root = root;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// ���������� �������� ActionCommand � �������
		switch (e.getActionCommand()) {
			case PLAYER_VS_PLAYER:
				// ������������� ����� ���� "Player vs Player"
				ApplicationData.playMode = ApplicationData.PLAYER_VS_PLAYER;
				break;
	
			case PLAYER_VS_COMPUTER:
				// ������������� ����� ���� "Player vs Computer"
				ApplicationData.playMode = ApplicationData.PLAYER_VS_COMPUTER;
				break;
		} // end switch
		
		// �������������� ������ ����
		root.play();
	} // end method actionPerformed

}
