package com.bluefalconhd.sharpglow;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class TemporaryEnchantmentManager implements Listener {
    private final JavaPlugin plugin;
    private final Map<ItemStack, BukkitRunnable> enchantedItems = new HashMap<>();

    public TemporaryEnchantmentManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void applyTemporaryEnchantment(ItemStack item, Enchantment enchantment, int level, long ticks, Player player) {
        item.addUnsafeEnchantment(enchantment, level);

        BukkitRunnable removeEnchantmentTask = new BukkitRunnable() {
            @Override
            public void run() {
                item.removeEnchantment(enchantment);
                enchantedItems.remove(item);
            }
        };

        removeEnchantmentTask.runTaskLater(plugin, ticks);
        enchantedItems.put(item, removeEnchantmentTask);

        player.getInventory().addItem(item);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        removeEnchantmentIfNeeded(event.getItemDrop().getItemStack());
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        removeEnchantmentIfNeeded(event.getItem().getItemStack());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        removeEnchantmentIfNeeded(item);
    }

    private void removeEnchantmentIfNeeded(ItemStack item) {
        if (item != null && enchantedItems.containsKey(item)) {
            enchantedItems.get(item).cancel();
            item.getEnchantments().forEach((enchantment, level) -> item.removeEnchantment(enchantment));
            enchantedItems.remove(item);
        }
    }
}