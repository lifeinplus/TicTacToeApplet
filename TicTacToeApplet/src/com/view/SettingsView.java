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
		// ������ ��������� ������-���������
		controller = new SettingsViewController(this, parent);
		
		// ��������� �������� ���������� ������
		this.setLayout(new FlowLayout());

		// ������ ���������� ������
		playWithPlayer = new JButton("Player vs Player");
		playWithComputer = new JButton("Play vs Computer");

		// ����������� ������ � ����������� ���������
		playWithPlayer.setActionCommand(SettingsViewController.PLAYER_VS_PLAYER);
		playWithComputer.setActionCommand(SettingsViewController.PLAYER_VS_COMPUTER);

		// ��������� ��������� ��� ������
		playWithPlayer.addActionListener(controller);
		playWithComputer.addActionListener(controller);
		
		// ��������� ������ �� ������
		this.add(playWithPlayer);
		this.add(playWithComputer);
	} // end constructor SettingsView
	
}
