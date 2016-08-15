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
package ch.skylouna.translateapi;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ch.skylouna.translateapi.manager.LanguageManager;
import ch.skylouna.translateapi.object.Language;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;

public class TranslateAPI extends JavaPlugin {

	public static TranslateAPI instance;
	public static LanguageManager languageManager;

	public void onEnable() {
		instance = this;

		getConfig().options().copyDefaults(true);
		saveConfig();

		languageManager = new LanguageManager();

	}

	/**
	 * Send a message to a player:
	 * 
	 * replace @0, @1, @2, @3... with args :o
	 * 
	 * @param p
	 * @param key
	 * @param args
	 */

	public static void sendMessage(Player p, String key, String... args) {
		p.sendMessage(translate(p, key, args));
	}

	public static void sendActionBar(Player p, String key, String... args) {
		PacketPlayOutChat ppoc = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + translate(p, key, args) + "\"}"), (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}

	/**
	 * Translate a message il player language
	 * 
	 * @param p
	 * @param key
	 * @param args
	 * @return translated message
	 */
	public static String translate(Player p, String key, String... args) {
		if (key == null)
			return null;
		String defaultMessage = getLanguage(p).getMessage(key, false);
		for (int i = 0; i < args.length; i++)
			defaultMessage = defaultMessage.replace("@" + i, args[i]);
		defaultMessage = defaultMessage.replace("&", "§");
		return defaultMessage;
	}

	public static void setLanguage(Player p, Language language) {
		instance.getConfig().set("Player." + p.getUniqueId().toString(), language.getName());
		instance.saveConfig();
	}

	public static Language getLanguage(Player p) {
		return languageManager.getLanguage(instance.getConfig().getString("Player." + p.getUniqueId().toString()));
	}

	public static void warn(String msg) {
		Bukkit.getConsoleSender().sendMessage(msg);
	}

	public static TranslateAPI getInstance() {
		return instance;
	}

	public static LanguageManager getLanguageManager() {
		return languageManager;
	}


}
