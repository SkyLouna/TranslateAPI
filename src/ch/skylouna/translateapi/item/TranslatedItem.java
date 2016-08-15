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

package ch.skylouna.translateapi.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ch.skylouna.translateapi.TranslateAPI;

public class TranslatedItem {

	String translatedName;
	String translatedLore;
	ItemStack item;

	public TranslatedItem(String translatedName, String translatedLore, ItemStack item) {
		this.translatedName = translatedName;
		this.translatedLore = translatedLore;
		this.item = item;
	}

	public ItemStack getItemStack(Player player) {
		ItemStack item = getItem().clone();
		ItemMeta meta = item.getItemMeta();
		String itemStackName = TranslateAPI.translate(player, getTranslatedName());
		String itemStackLore = TranslateAPI.translate(player, getTranslatedLore());
		meta.setDisplayName(itemStackName == null ? "" : itemStackName);
		List<String> itemLore = new ArrayList<String>();
		if (itemStackLore == null)
			meta.setLore(Arrays.asList(""));
		else {
			String[] loreLines = itemStackLore.split("@n");
			for (String loreLine : loreLines)
				itemLore.add(loreLine);
			meta.setLore(itemLore);
		}

		item.setItemMeta(meta);
		return item;
	}

	public void give(Player p) {
		p.getInventory().addItem(getItemStack(p));
	}

	public void give(Player p, int slot) {
		p.getInventory().setItem(slot, getItemStack(p));
	}


	/**
	 * Getters
	 */

	public ItemStack getItem() {
		return item;
	}

	public String getTranslatedLore() {
		return translatedLore;
	}

	public String getTranslatedName() {
		return translatedName;
	}

	/**
	 * Setters
	 */
	public void setItem(ItemStack item) {
		this.item = item;
	}

	public void setTranslatedLore(String translatedLore) {
		this.translatedLore = translatedLore;
	}

	public void setTranslatedName(String translatedName) {
		this.translatedName = translatedName;
	}
}
