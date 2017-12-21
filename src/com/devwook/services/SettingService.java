package com.devwook.services;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
		name = "SettingService",
		storages = {
				@Storage(id = "pathRule", file = StoragePathMacros.PROJECT_FILE)
		}
)

public class SettingService implements PersistentStateComponent<SettingService> {
	public String pathRule = "";

	@Nullable
	@Override
	public SettingService getState() {
		return this;
	}

	public void loadState(SettingService object) {
		XmlSerializerUtil.copyBean(object, this);
	}
}