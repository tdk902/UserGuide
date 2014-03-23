package com.rylinaux.userguide.listeners;

import com.rylinaux.userguide.UserGuide;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final UserGuide plugin;

    public JoinListener(UserGuide plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {

        final Player player = event.getPlayer();

        if (player.hasPlayedBefore())
            return;

        // Delay 5 second so the book is noticed.
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.giveGuide(player);
            }
        }, 100L);

    }

}
