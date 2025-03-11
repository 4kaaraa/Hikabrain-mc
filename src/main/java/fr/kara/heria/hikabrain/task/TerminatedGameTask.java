package fr.kara.heria.hikabrain.task;

import fr.kara.heria.hikabrain.Hikabrain;
import fr.kara.heria.hikabrain.config.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TerminatedGameTask extends BukkitRunnable {
    private final Hikabrain plugin;
    private int timeUntilTeleporation;

    public TerminatedGameTask(final Hikabrain plugin) {
        GameState.setCurrentStep(GameState.TERMINATED);
        this.timeUntilTeleporation = 15;
        this.plugin = plugin;
        this.runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void run() {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            if (this.timeUntilTeleporation == 3) {
                for (Player player : Bukkit.getOnlinePlayers())
                    player.chat("/hub");
                plugin.stop();
            }
            if (this.timeUntilTeleporation <= 0) {
                this.cancel();
                if (!Bukkit.getOnlinePlayers().isEmpty()){
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.kickPlayer("\n§6§lHERIA\n\n§cStopping Server");
                    }
                }
            }
            --this.timeUntilTeleporation;
        }
    }
}
