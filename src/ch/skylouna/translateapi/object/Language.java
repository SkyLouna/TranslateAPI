/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 SkyLouna
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.skylouna.translateapi.object;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ch.skylouna.translateapi.TranslateAPI;

public class Language {

	String name;
	String fileName;
	File file;
	FileConfiguration configFile;
	HashMap<String, String> languageTranslations;


	public Language(String name, File file) {
		this.name = name;
		this.fileName = file.getName();
		this.file = file;
		try {
			if (file.createNewFile()) {
				TranslateAPI.warn("§cCreate file for language " + name + " §a(" + fileName + ")");
				configFile = YamlConfiguration.loadConfiguration(file);
			} else {
				configFile = YamlConfiguration.loadConfiguration(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		languageTranslations = new HashMap<>();
		loadTranslations();
		TranslateAPI.warn("§aNew language ! " + name + " " + fileName + " (" + languageTranslations.size() + " translations)");
	}

	public String getMessage(String key, boolean dynamic) {
		String msg = null;
		if (dynamic)
			getConfigFile().getString(key);
		else
			msg = languageTranslations.get(key);
		if (msg == null) {
			return "Unknown Key for language: " + this.getName() + " (" + key + ")";
		} else
			return msg;
	}

	public void loadTranslations() {
		int lastSize = languageTranslations.size();
		long lastTime = System.currentTimeMillis();
		languageTranslations.clear();
		configFile = YamlConfiguration.loadConfiguration(file);
		configFile.options().copyDefaults(true);
		for (String str : configFile.getKeys(true)) {
			if (languageTranslations.containsKey(str)) {
				// normaly key can't be 2 times the same :o Bukkit block that
				TranslateAPI.warn("§cThere are two times the same keys in " + this.name + " : " + str + " §c( " + languageTranslations.get(str) + " §c) is now ( " + configFile.getString(str) + " §c)");
			}
			languageTranslations.put(str, configFile.getString(str));
		}
		TranslateAPI.warn("Loading " + this.name + " in: " + (System.currentTimeMillis() - lastTime) + " millis. Actual: " + languageTranslations.size() + " Keys. Before: " + lastSize + " Keys.");

	}

	public void reloadFile() {
		TranslateAPI.warn("Reloading " + this.name);
		loadTranslations();
	}

	/**
	 * Defaults getters and setters
	 */

	public FileConfiguration getConfigFile() {
		return configFile;
	}

	public File getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}

	public String getName() {
		return name;
	}


}
