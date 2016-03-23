package com;

import java.awt.Panel;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import com.view.PlayView;
import com.view.SettingsView;

public class Main extends JApplet {

	private Panel currentView;
	
	
	// Инициализация апплета
	public void init() {
		try {
			// Инициализация компонентов апплета в потоке
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					settings();
				}
			});
			
		} catch (Exception e) {
			System.err.println("Can't init GUI: " + e.toString());
		}
	} // end method init

	
	// Инициализация панели выбора режима игры
	public void settings() {
		this.setSize(300, 40);
		manageView(new SettingsView(this));
	}

	
	// Установка передаваемой панели и обновление корневой
	private void manageView(Panel view) {
		if (currentView != null) remove(currentView);
		
		currentView = view;
		add(currentView);
		
		rootPane.updateUI();
	} // end method manageView

	
	// Инициализация панели игры
	public void play() {
		this.setSize(430, 450);
		manageView(new PlayView(this));
	}
	
}
