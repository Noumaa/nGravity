package me.nouma.ngravity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;

public class Scheduler {

    private Chicken chicken;
    private int id = -1;

    public Scheduler(Chicken chicken) {
        this.chicken = chicken;
    }

    public void start() {
        if (id == -1) stop();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> {
            if (chicken.isDead()) stop();
            if (chicken.isOnGround()) chicken.remove();
        }, 5, 5);
    }

    public void stop() {
        if (id != -1) Bukkit.getScheduler().cancelTask(id);
    }
}
