package me.nouma.ngravity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class Main extends JavaPlugin implements Listener {

    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        getServer().getPluginManager().registerEvents(this, this);
        Metrics metrics = new Metrics(this);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Item item = e.getItemDrop();
        Chicken chicken = (Chicken) Bukkit.getWorlds().get(0).spawnEntity(e.getItemDrop().getLocation(), EntityType.CHICKEN);
        chicken.setBaby();
        chicken.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
        chicken.setInvulnerable(true);
        chicken.setSilent(true);
        chicken.setCollidable(false);
        chicken.addPassenger(item);
        chicken.addScoreboardTag("itemgrav");
        chicken.setVelocity(e.getPlayer().getLocation().getDirection());
        new Scheduler(chicken).start();
    }

    @EventHandler
    public void onItemDamage(EntityDamageEvent e) {
        if (e.getEntity().getScoreboardTags().contains("itemgrav")) e.setCancelled(true);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        if (e.getItem().getVehicle() == null) return;
        if (e.getItem().getVehicle().getScoreboardTags().contains("itemgrav")) e.getItem().getVehicle().remove();
    }

    @EventHandler
    public void onDespawn(ItemDespawnEvent e) {
        if (e.getEntity().getVehicle() == null) return;
        if (e.getEntity().getVehicle().getScoreboardTags().contains("itemgrav")) e.getEntity().getVehicle().remove();
    }
}
