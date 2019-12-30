package me.nouma.ngravity;

import me.nouma.ngravity.listeners.ItemListener;
import me.nouma.ngravity.listeners.PlayerListener;
import me.nouma.ngravity.schedulers.PlayersScheduler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Metrics metrics = new Metrics(this);

        setupDefaultConfig();

        if (getConfig().getBoolean("items.enable")) getServer().getPluginManager().registerEvents(new ItemListener(), this);
        if (getConfig().getBoolean("players.enable")) {
            getServer().getPluginManager().registerEvents(new PlayerListener(), this);
            new PlayersScheduler().start();
        }
    }

    public void setupDefaultConfig() {
        getConfig().addDefault("items.enable", true);
        getConfig().addDefault("items.weight_affects_gravity", true);

        getConfig().addDefault("players.enable", true);
        getConfig().addDefault("players.jump_boost", 1);
        getConfig().addDefault("players.slow_falling", 2);

        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
