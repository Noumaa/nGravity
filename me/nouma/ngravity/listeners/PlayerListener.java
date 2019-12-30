package me.nouma.ngravity.listeners;

import me.nouma.ngravity.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        setEffects(e.getPlayer());
    }

    public static void setEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, Main.INSTANCE.getConfig().getInt("players.slow_falling"), false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, Main.INSTANCE.getConfig().getInt("players.jump_boost"), false, false));
    }
}
