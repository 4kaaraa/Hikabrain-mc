package fr.kara.heria.hikabrain.listeners;

import fr.kara.heria.hikabrain.Hikabrain;
import fr.kara.heria.hikabrain.config.*;
import fr.kara.heria.hikabrain.data.PlayerInfo;
import fr.kara.heria.hikabrain.object.ObjConfig;
import fr.kara.heria.hikabrain.task.WaitingTask;
import fr.kara.heria.hikabrain.utils.ItemBuilder;
import fr.kara.heria.hikabrain.utils.Nametag;
import fr.kara.heria.hikabrain.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final Hikabrain plugin;

    public PlayerJoin(Hikabrain plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        //HikabrainData hikabrainData = Hikabrain.getInstance().getHikabrainDataManager().createOrLoad(player.getUniqueId());
        final PlayerInfo data = PlayerInfo.getPlayerData(player);
        e.setJoinMessage(null);

        Hikabrain.getInstance().getScoreboardManager().onLogin(player);
        //data.setPoint(hikabrainData.getPoint());

        if (GameState.isStep(GameState.WAITING)){

            for (int i = 0; i < 100; i++) {
                player.sendMessage("");
            }

            player.teleport(SpawnLocation.SPAWN.getLocation());
            Nametag.setNameTag(player, "§7", "", 1);
            data.setWaiting(true);

            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.setHealth(20);
            player.setExp(0);
            player.setLevel(0);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));


            player.setAllowFlight(false);
            player.setFlying(false);

            player.getInventory().setBoots(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setHelmet(null);

            player.getInventory().setHeldItemSlot(1);
            player.getInventory().setItem(4, new ItemBuilder(Material.BED, 1).setName("§cQuitter §8▸ ").build());

            for (Player online : Bukkit.getOnlinePlayers()) {
                online.showPlayer(player);
                Title.sendActionBar(online, MessageConfigEnum.PREFIX + " §7" + player.getName() + " a rejoint la partie §8(§f" + Bukkit.getOnlinePlayers().size() + "§7/§f" + ObjConfig.getNbJoueur() + "§8)");
            }

            if (PlayerMode.isMode(PlayerMode.SOLO)){
                if (Bukkit.getOnlinePlayers().size() >= 2 && !this.plugin.hasWaitingTaskStarted()){
                    new WaitingTask(this.plugin);
                    this.plugin.getWaitingTask().forceStarting();
                }
            } else {
                if (Bukkit.getOnlinePlayers().size() >= 2 && !this.plugin.hasWaitingTaskStarted()) {
                    new WaitingTask(this.plugin);
                }
            }
        } else {
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            data.setTeam(Team.SPEC);
            Nametag.setNameTag(player, "§7Spectateur ", "", 0);
            data.setWaiting(true);
            player.teleport(SpawnLocation.SPEC.getLocation());
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.setFlying(true);
            for (Player p1 : Bukkit.getOnlinePlayers()) {
                if (p1 == player) continue;
                p1.hidePlayer(player);
            }
        }
    }
}
