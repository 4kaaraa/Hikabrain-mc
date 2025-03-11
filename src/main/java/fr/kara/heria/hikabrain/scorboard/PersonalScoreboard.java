package fr.kara.heria.hikabrain.scorboard;

import fr.kara.heria.hikabrain.Hikabrain;
import fr.kara.heria.hikabrain.config.GameState;
import fr.kara.heria.hikabrain.data.PlayerInfo;
import fr.kara.heria.hikabrain.object.ObjPoint;
import fr.kara.heria.hikabrain.task.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PersonalScoreboard {
    private final Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;

    public PersonalScoreboard(Player player) {
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "HeriaMC");
        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData() {
    }

    public void setLines(String ip) {
        String tempsFormate = GameTask.getTempsFormate();
        final PlayerInfo data = PlayerInfo.getPlayerData(player);

        objectiveSign.setDisplayName("§e» §6§lHikabrain §e«");

        if (GameState.isStep(GameState.WAITING)) {
            objectiveSign.setLine(0, "§1");
            objectiveSign.setLine(1, "§8■ §fJeu: §7Hikabrain (Bêta)");
            objectiveSign.setLine(2, "§8■ §fDébut dans: §3" + (Bukkit.getOnlinePlayers().size() < 2 ? "1 joueur" : Hikabrain.getInstance().getWaitingTask().getRemainingSeconds()));
            objectiveSign.setLine(3, "§2");
            objectiveSign.setLine(4, "§8■ §fJoueurs: §a" + Bukkit.getOnlinePlayers().size());
            objectiveSign.setLine(5, "§8");
            objectiveSign.setLine(6, ip);
        }

        if (GameState.isStep(GameState.INGAME)) {
            objectiveSign.setLine(0, "§1");
            objectiveSign.setLine(1, "§8■ §fEquipe Bleu : §9" + ObjPoint.getBleuePoint());
            objectiveSign.setLine(2, "§8■ §fEquipe Rouge : §c" + ObjPoint.getRedPoint());
            objectiveSign.setLine(3, "§3");
            objectiveSign.setLine(4, "§8■ §fTué : §a" + data.getKill());
            objectiveSign.setLine(5, "§8■ §fMort : §c" + data.getDeath());
            objectiveSign.setLine(6, "§8■ §fRatio : §e" + data.getRatio());
            objectiveSign.setLine(7, "§8");
            objectiveSign.setLine(8, ip);
        }

        objectiveSign.updateLines();
    }

    public void onLogout() {
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}

