package fr.kara.heria.hikabrain.listeners;

import fr.kara.heria.hikabrain.Hikabrain;
import fr.kara.heria.hikabrain.config.GameState;
import fr.kara.heria.hikabrain.config.PlayerMode;
import fr.kara.heria.hikabrain.config.Team;
import fr.kara.heria.hikabrain.data.PlayerInfo;
import fr.kara.heria.hikabrain.object.ObjTeamAlive;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final Hikabrain plugin;

    public PlayerQuit(Hikabrain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        final PlayerInfo data = PlayerInfo.getPlayerData(player);
        e.setQuitMessage(null);

        Hikabrain.getInstance().getScoreboardManager().onLogout(player);

        if (GameState.isStep(GameState.WAITING)){
            if (data.hasTeam()){
                plugin.removePlayer(player);

                if (GameState.isStep(GameState.INGAME)){
                    ObjTeamAlive.setRed(Team.ROUGE.getOnlinePlayers().size());
                    ObjTeamAlive.setBleue(Team.BLEU.getOnlinePlayers().size());
                }
            }
        } else if (GameState.isStep(GameState.INGAME)){
            if (PlayerMode.isMode(PlayerMode.SOLO)){
                if (data.hasTeam()){
                    plugin.removePlayer(player);

                    if (GameState.isStep(GameState.INGAME)){
                        ObjTeamAlive.setRed(Team.ROUGE.getOnlinePlayers().size());
                        ObjTeamAlive.setBleue(Team.BLEU.getOnlinePlayers().size());
                    }
                }
            } else {
                if (data.hasTeam()){
                    plugin.removePlayer(player);

                    if (GameState.isStep(GameState.INGAME)){
                        ObjTeamAlive.setRed(Team.ROUGE.getOnlinePlayers().size());
                        ObjTeamAlive.setBleue(Team.BLEU.getOnlinePlayers().size());
                    }
                }
            }
        }
    }
}
