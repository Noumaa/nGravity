package me.nouma.ngravity.schedulers;

import com.sun.org.apache.xerces.internal.impl.dv.xs.SchemaDVFactoryImpl;
import me.nouma.ngravity.Main;
import me.nouma.ngravity.listeners.ItemListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Item;
import org.bukkit.entity.Pig;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemScheduler {

    private Chicken chicken;
    private int id = -1;

    public ItemScheduler(Chicken chicken) {
        this.chicken = chicken;
    }

    public void start() {
        if (id == -1) stop();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
            if (chicken.isDead()) stop();
            if (chicken.getPassengers().size() == 0 || chicken.isOnGround()) {
                chicken.remove();
                return;
            }
            if (!Main.INSTANCE.getConfig().getBoolean("items.weight_affects_gravity")) return;
            ItemListener.setWeight(chicken);
        }, 5, 5);
    }

    public void stop() {
        if (id != -1) Bukkit.getScheduler().cancelTask(id);
    }
}
