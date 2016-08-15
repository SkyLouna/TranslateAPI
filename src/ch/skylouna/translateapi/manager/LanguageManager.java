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

package ch.skylouna.translateapi.manager;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import ch.skylouna.translateapi.TranslateAPI;
import ch.skylouna.translateapi.object.Language;

public class LanguageManager {
	HashMap<String, Language> languages;
	Language defaultLanguage;

	public LanguageManager() {
		languages = new HashMap<>();
		defaultLanguage = null;
	}

	/**
	 * Load all languages from a folder excepting some files
	 * 
	 * @param folder
	 * @param ignoredFiles
	 */

	public void loadFromFolder(File folder, String... ignoredFiles) {
		List<String> ignored = Arrays.asList(ignoredFiles);
		if (folder.exists()) {
			for (File f : folder.listFiles()) {
				if (ignored.contains(f.getName())) {
					TranslateAPI.warn("Skipping " + f.getName());
				} else {
					TranslateAPI.warn("Adding " + f.getName());
					addLanguage(new Language(f.getName().replace(".yml", ""), f));
				}
			}
		} else {
			TranslateAPI.warn("§4Folder " + folder.getName() + " don't exist (Rt if you cry everyday)");
		}
	}

	public void addLanguage(Language language) {
		languages.put(language.getName(), language);
	}

	public Language getLanguage(String name) {
		Language language = languages.get(name);
		if (language == null)
			if (hasDefaultLanguage())
				return defaultLanguage;
			else
				return new Language("Default", new File("Default.yml"));
		else
			return language;
	}

	public HashMap<String, Language> getLanguages() {
		return languages;
	}

	public boolean hasDefaultLanguage() {
		return getDefaultLanguage() != null;
	}

	public boolean isDefaultLanguage(Language language) {
		if (!hasDefaultLanguage())
			return false;
		if (defaultLanguage.getName().equalsIgnoreCase(language.getName()))
			return true;
		else
			return false;
	}

	public Language getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(Language defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public void reloadLanguages() {
		for (Language lang : getLanguages().values())
			lang.reloadFile();
	}

}
