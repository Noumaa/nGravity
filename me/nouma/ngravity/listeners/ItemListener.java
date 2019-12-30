package me.nouma.ngravity.listeners;

import me.nouma.ngravity.Main;
import me.nouma.ngravity.schedulers.ItemScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Item item = e.getItemDrop();
        Chicken chicken = (Chicken) e.getPlayer().getLocation().getWorld().spawnEntity(e.getItemDrop().getLocation(), EntityType.CHICKEN);
        chicken.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
        chicken.addScoreboardTag("itemgrav");
        chicken.setBaby();
        chicken.setInvulnerable(true);
        chicken.setSilent(true);
        chicken.setCollidable(false);
        chicken.addPassenger(item);
        setWeight(chicken);
        chicken.setVelocity(e.getPlayer().getLocation().getDirection());
        new ItemScheduler(chicken).start();
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

    public static void setWeight(Chicken chicken) {
        if (!Main.INSTANCE.getConfig().getBoolean("items.weight_affects_gravity")) return;
        if (((Item) chicken.getPassengers().get(0)).getItemStack().getAmount() < 32) chicken.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, false, false));
    }
}
