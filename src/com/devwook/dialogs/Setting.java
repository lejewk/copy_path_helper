package com.devwook.dialogs;

import com.devwook.consts.PathRule;
import com.devwook.services.SettingService;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Setting extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField PATHTextField;
	private SettingService settingService;

	public Setting(Project project) {
		this.settingService = ServiceManager.getService(project, SettingService.class);

		setTitle("Copy Path Helper Setting");
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - getWidth()) / 2;
		final int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);


		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onOK();
			}
		});

		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		});

		String pathRule = settingService.pathRule;
		if (pathRule == null || pathRule.isEmpty()) {
			pathRule = PathRule.DEFAULT;
		}
		PATHTextField.setText(pathRule);

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onOK() {
		// 저장
		settingService.pathRule = PATHTextField.getText();

		// add your code here
		dispose();
	}

	private void onCancel() {
		// add your code here if necessar
		dispose();
	}

	public static void main(Project project) {
		Setting dialog = new Setting(project);
		dialog.pack();
		dialog.setVisible(true);
	}
}
