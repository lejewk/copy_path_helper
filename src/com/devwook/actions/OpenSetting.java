package com.devwook.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.devwook.dialogs.Setting;

public class OpenSetting extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		// TODO: insert action logic here
		final Project project = e.getData(CommonDataKeys.PROJECT);
		Setting.main(project);
	}
}
