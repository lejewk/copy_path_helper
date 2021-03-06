package com.devwook.actions;

import com.devwook.consts.PathRule;
import com.devwook.services.SettingService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import static com.intellij.openapi.actionSystem.CommonDataKeys.PROJECT;
import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

/**
 * Created by Lee-jaewook on 2017-11-21.
 */
public class CopyPath extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent event) {
		// TODO: insert action logic here
		final Project project = event.getData(CommonDataKeys.PROJECT);

		final String projetName = project.getName();
		final VirtualFile file = event.getData(VIRTUAL_FILE);
		// 파일이 선택되어있지 않는경우
		if (file == null) {
			showPopup(event, "<p><b>Please select one file.</b></p>");
			return;
		}

		final String canonicalPath = file.getCanonicalPath();
		// 패스가 없는경우
		if (canonicalPath == null) {
			showPopup(event, "<p><b>This file does not have a path.</b></p>");
			return;
		}

		final int index = canonicalPath.indexOf(projetName);
		final String filePath = canonicalPath.substring(
			index + projetName.length() + 1,
			canonicalPath.length()
		);

		SettingService settingService = ServiceManager.getService(project, SettingService.class);
		String pathRule = settingService.pathRule;

		if (pathRule == null || pathRule.isEmpty()) {
			pathRule = PathRule.DEFAULT;
		}

		pathRule = pathRule.replaceAll("\\{FILE\\}", file.getName());
		pathRule = pathRule.replaceAll("\\{PATH\\}", filePath);

		this.writeTextToClipboard(pathRule);
	}

	private void writeTextToClipboard(String s) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable transferable = new StringSelection(s);
		clipboard.setContents(transferable, null);
	}

	private void showPopup(final AnActionEvent event, final String html) {

		final StatusBar statusBar = WindowManager.getInstance().getStatusBar(PROJECT.getData(event.getDataContext()));
		if (statusBar == null) return;

		JBPopupFactory.getInstance()
				.createHtmlTextBalloonBuilder(html, MessageType.INFO, null)
				.setFadeoutTime(2500)
				.createBalloon()
				.show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);
	}
}
