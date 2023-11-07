package com.bluefalconhd.sharpglow;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerKillDeathListener implements Listener {

    private boolean canEnchantWithSharpness(Material material) {
        // Define a list of materials that can be enchanted with Sharpness.
        Material[] enchantable = {
                Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD,
                Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD,
                Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE,
                Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE
        };

        for (Material mat : enchantable) {
            if (material == mat) {
                return true; // The material can be enchanted with Sharpness.
            }
        }

        return false; // The material cannot be enchanted with Sharpness.
    }

    private void deathEffects(Player player) {
        // give player slowness for 30 mins
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 60 * 30, 1));
    }

    private void killEffects(Player player) {
        // apply temporary enchantment to weapon in players main hand if it is an item that sharpness can be applied to
        ItemStack item = player.getInventory().getItemInMainHand(); // Get the item in the player's main hand.
        if (canEnchantWithSharpness(item.getType())) { // Check if the item can be enchanted with Sharpness.
            // Apply the temporary enchantment to the item for 30 mins
            Sharpglow.getTemporaryEnchantmentManager().applyTemporaryEnchantment(item, Enchantment.DAMAGE_ALL, 2, 20 * 60 * 30, player);
        }

        // give player glowing effect for 30 mins
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 60 * 30, 1));
    }

    // Listener for when a player kills another player
    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            Player killer = event.getEntity().getKiller();
            killEffects(killer);
        }
    }

    // Listener for when a player dies
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deceased = event.getEntity();
        deathEffects(deceased);
    }
}