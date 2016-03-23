package com;

import java.awt.Panel;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import com.view.PlayView;
import com.view.SettingsView;

public class Main extends JApplet {

	private Panel currentView;
	
	
	// ������������� �������
	public void init() {
		try {
			// ������������� ����������� ������� � ������
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

	
	// ������������� ������ ������ ������ ����
	public void settings() {
		this.setSize(300, 40);
		manageView(new SettingsView(this));
	}

	
	// ��������� ������������ ������ � ���������� ��������
	private void manageView(Panel view) {
		if (currentView != null) remove(currentView);
		
		currentView = view;
		add(currentView);
		
		rootPane.updateUI();
	} // end method manageView

	
	// ������������� ������ ����
	public void play() {
		this.setSize(430, 450);
		manageView(new PlayView(this));
	}
	
}
