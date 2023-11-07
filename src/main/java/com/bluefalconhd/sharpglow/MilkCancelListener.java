package com.bluefalconhd.sharpglow;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.Material;
import org.bukkit.event.EventPriority;

public class MilkCancelListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        // Check if the item is a milk bucket
        if(event.getItem().getType() == Material.MILK_BUCKET) {
            // Cancel the event, which prevents the milk from being consumed
            event.setCancelled(true);
        }
    }
}
