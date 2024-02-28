package sryxen.plugins.pluginhider;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginHider extends JavaPlugin implements Listener {

    private static final String PERMISSION_VIEW_PLUGINS = "pluginhider.view";

    @Override
    public void onEnable() {
        getLogger().info("Plugin has been successfully enabled!");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("pl").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("pl")) {
            if (sender instanceof Player) {
                if (!sender.hasPermission(PERMISSION_VIEW_PLUGINS)) {
                    sender.sendMessage("You do not have permission to view plugins.");
                    return true;
                }
            } else {
                sender.sendMessage("List of all plugins:");
                for (org.bukkit.plugin.Plugin plugin : getServer().getPluginManager().getPlugins()) {
                    sender.sendMessage("- " + plugin.getName());
                }
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage().equalsIgnoreCase("/pl") && !player.hasPermission(PERMISSION_VIEW_PLUGINS)) {
            event.setCancelled(true);
            player.sendMessage("You do not have permission to execute this command.");
        }
    }

    @Override
    public void onDisable() {
        System.out.println("Plugin has been disabled!");
    }
}
