package fr.kara.heria.hikabrain.task;

import fr.kara.heria.hikabrain.Hikabrain;
import fr.kara.heria.hikabrain.config.Team;
import fr.kara.heria.hikabrain.data.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {
    private static int tempsTotal = 0;
    public final Hikabrain plugin;

    public GameTask(final Hikabrain plugin) {
        this.plugin = plugin;
        tempsTotal = 0;
        this.runTaskTimer(plugin, 0, 20);
    }

    public static int getTempsTotal() {
        return tempsTotal;
    }

    public static String getTempsFormate() {
        return formatTemps(tempsTotal);
    }

    public static String formatTemps(int secondes) {
        int minutes = (secondes % 3600) / 60;
        int secondesRestantes = secondes % 60;

        return String.format("%02d:%02d", minutes, secondesRestantes);
    }

    public void run() {
        tempsTotal++;
        String tempsFormate = formatTemps(tempsTotal);

        for (Player online : Bukkit.getOnlinePlayers()) {
            final PlayerInfo data = PlayerInfo.getPlayerData(online);
            if (data.getTeam() != Team.SPEC) {
                //data.increaseTotalTime(1);
            }
        }
    }
}
