package me.nouma.ngravity.schedulers;

import me.nouma.ngravity.Main;
import me.nouma.ngravity.listeners.PlayerListener;
import org.bukkit.Bukkit;

public class PlayersScheduler {

    private int id = -1;

    public void start() {
        if (id == -1) stop();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.INSTANCE, () -> Bukkit.getOnlinePlayers().forEach(PlayerListener::setEffects), 5*20, 30*20);
    }

    public void stop() {
        if (id != -1) Bukkit.getScheduler().cancelTask(id);
    }
}
