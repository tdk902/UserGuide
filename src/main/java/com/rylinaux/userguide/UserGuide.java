package com.rylinaux.userguide;

import com.rylinaux.userguide.listeners.JoinListener;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class UserGuide extends JavaPlugin implements CommandExecutor {

    private final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GREEN + "UserGuide" + ChatColor.GRAY + "] ";

    private ItemStack guide = null;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("guide").setExecutor(this);
        this.getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        makeBook();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "You must be a player to do that command.");
            return true;
        }

        if (!sender.hasPermission("userguide.use")) {
            sender.sendMessage(PREFIX + ChatColor.RED + "You don't have permission to do that command.");
            return true;
        }

        giveGuide((Player) sender);
        return true;

    }

    private void makeBook() {

        guide = new ItemStack(Material.WRITTEN_BOOK, 1);

        String title = colorMessage(this.getConfig().getString("guide-title"));
        String author = colorMessage(this.getConfig().getString("guide-author"));
        String displayName = colorMessage(this.getConfig().getString("guide-display-name"));

        List<String> pages = this.getConfig().getStringList("pages");

        BookMeta meta = (BookMeta) guide.getItemMeta();

        meta.setTitle(title);
        meta.setAuthor(author);
        meta.setDisplayName(displayName);

        for (String page : pages) {
            meta.addPage(colorMessage(page));
        }

        guide.setItemMeta(meta);

    }

    public void giveGuide(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), guide);
        } else {
            player.getInventory().addItem(guide);
        }
        player.sendMessage(PREFIX + ChatColor.GREEN + "Make sure to read this user guide!");
    }

    private String colorMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
