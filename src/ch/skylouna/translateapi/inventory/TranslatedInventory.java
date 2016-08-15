package ch.skylouna.translateapi.inventory;

import java.util.HashMap;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ch.skylouna.translateapi.TranslateAPI;
import ch.skylouna.translateapi.item.TranslatedItem;

public class TranslatedInventory {
	String translatedName;
	int lines;
	HashMap<ItemStack, Integer> items;
	HashMap<TranslatedItem, Integer> translatedItems;
	Inventory inv;

	public TranslatedInventory(String translatedName, int lines) {
		this.translatedName = translatedName;
		this.lines = lines;
		items = new HashMap<>();
		translatedItems = new HashMap<>();
	}

	public void addItem(TranslatedItem item, int slot) {
		translatedItems.put(item, slot);
	}

	public void addItem(ItemStack item, int slot) {
		items.put(item, slot);
	}

	public void open(Player p, String... strings) {
		String name = TranslateAPI.translate(p, translatedName, strings);
		if (name.length() > 32)
			name = name.substring(0, 31);
		inv = Bukkit.createInventory(p, lines * 9, name);
		for (Entry<ItemStack, Integer> nI : items.entrySet())
			inv.setItem(nI.getValue(), nI.getKey());
		for (Entry<TranslatedItem, Integer> tI : translatedItems.entrySet())
			inv.setItem(tI.getValue(), tI.getKey().getItemStack(p));
		p.openInventory(inv);
	}

	public int getPos(int line, int slot) {
		return (line * 9 - 9) + slot - 1;
	}


}
