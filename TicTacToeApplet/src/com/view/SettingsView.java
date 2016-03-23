package com.view;

import java.awt.FlowLayout;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;

import com.Main;
import com.controller.SettingsViewController;

public class SettingsView extends Panel {
	
	private SettingsViewController controller;
	
	private JButton playWithPlayer;
	private JButton playWithComputer;

	public SettingsView(Main parent) {
		// Создаём экземпляр класса-слушателя
		controller = new SettingsViewController(this, parent);
		
		// Назначаем менеджер компоновки панеле
		this.setLayout(new FlowLayout());

		// Создаём экземпляры кнопок
		playWithPlayer = new JButton("Player vs Player");
		playWithComputer = new JButton("Play vs Computer");

		// Ассоциируем кнопки с определённым действием
		playWithPlayer.setActionCommand(SettingsViewController.PLAYER_VS_PLAYER);
		playWithComputer.setActionCommand(SettingsViewController.PLAYER_VS_COMPUTER);

		// Добавляем слушатель для кнопок
		playWithPlayer.addActionListener(controller);
		playWithComputer.addActionListener(controller);
		
		// Добавляем кнопки на панель
		this.add(playWithPlayer);
		this.add(playWithComputer);
	} // end constructor SettingsView
	
}
