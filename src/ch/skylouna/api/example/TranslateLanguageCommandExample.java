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

package ch.skylouna.api.example;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ch.skylouna.api.TranslateAPI;
import ch.skylouna.api.object.Language;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class TranslateLanguageCommandExample implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player)) {
			Player p = (Player) sender;
			if (label.equalsIgnoreCase("language")) {
				if (args.length <= 0) {
					// try to get "test_change" key and send to player without args
					TranslateAPI.sendMessage(p, "test_change");
					for (Language language : TranslateAPI.getLanguageManager().getLanguages().values()) {
						TextComponent component = new TextComponent("§a> " + language.getName());
						component.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/language " + language.getName()));
						p.spigot().sendMessage(component);
					}
					return false;
				}
				if (TranslateAPI.getLanguageManager().getLanguage(args[0]) == null) {
					TranslateAPI.sendMessage(p, "test_unknownlanguage");
					return true;
				}
				TranslateAPI.setLanguage(p, TranslateAPI.getLanguageManager().getLanguage(args[0]));
				p.sendMessage("test '");
				return true;
			}
			return true;
		}

		return true;
	}
}
