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

package ch.skylouna.api.object;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ch.skylouna.api.TranslateAPI;

public class Language {

	String name;
	String fileName;
	File file;
	FileConfiguration configFile;


	public Language(String name, String filename) {
		this.name = name;
		this.fileName = filename;
		file = new File(TranslateAPI.instance.getDataFolder() + "/" + filename);
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
		TranslateAPI.warn("§aNew language ! " + name + " " + fileName);
	}

	public String getMessage(String key) {
		String msg = configFile.getString(key);
		if (msg == null) {
			return "Unknown Key for language: " + this.getName() + " (" + key + ")";
		} else
			return msg;
	}

	public void reloadFile() {
		configFile = YamlConfiguration.loadConfiguration(file);
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
