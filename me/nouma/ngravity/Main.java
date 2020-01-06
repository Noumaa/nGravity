package me.nouma.ngravity;

import me.nouma.ngravity.listeners.ItemListener;
import me.nouma.ngravity.listeners.PlayerListener;
import me.nouma.ngravity.schedulers.PlayersScheduler;
import me.nouma.ngravity.updater.UpdateCheck;
import org.bukkit.Bukkit;
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

        UpdateCheck.of(this).resourceId(73808).handleResponse((versionResponse, version) -> {
                    switch (versionResponse) {
                        case FOUND_NEW:
                            Bukkit.getLogger().warning(String.format("[%s] A new version was found: v" + version, getDescription().getName()));
                            Bukkit.getLogger().warning(String.format("[%s] Currently running on v" + getDescription().getVersion(), getDescription().getName()));
                            break;
                        case UNAVAILABLE:
                            Bukkit.getLogger().warning(String.format("[%s] Unable to perform an update check.", getDescription().getName()));
                    }
        }).check();
    }

    public void setupDefaultConfig() {
        getConfig().addDefault("items.enable", true);
        getConfig().addDefault("items.weight_affects_gravity", false);

        getConfig().addDefault("players.enable", true);
        getConfig().addDefault("players.jump_boost", 1);
        getConfig().addDefault("players.slow_falling", 2);

        getConfig().addDefault("worlds_blacklist", new String[] {"world1", "world2"});

        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
