package me.nouma.ngravity.listeners;

import me.nouma.ngravity.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        setEffects(e.getPlayer());
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.INSTANCE, () -> setEffects(e.getPlayer()), 1);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        setEffects(e.getPlayer());
    }

    public static void setEffects(Player player) {
        if (Main.INSTANCE.getConfig().getStringList("worlds_blacklist").contains(player.getLocation().getWorld().getName())) {
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
            player.removePotionEffect(PotionEffectType.JUMP);
            return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, Main.INSTANCE.getConfig().getInt("players.slow_falling"), false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, Main.INSTANCE.getConfig().getInt("players.jump_boost"), false, false));
    }
}
