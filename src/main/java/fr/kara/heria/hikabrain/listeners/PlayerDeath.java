package fr.kara.heria.hikabrain.listeners;

import fr.kara.heria.hikabrain.Hikabrain;
import fr.kara.heria.hikabrain.config.*;
import fr.kara.heria.hikabrain.data.PlayerInfo;
import fr.kara.heria.hikabrain.object.ObjTeamAlive;
import fr.kara.heria.hikabrain.utils.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {


    private final Hikabrain plugin;

    public PlayerDeath(Hikabrain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        Player attacker = e.getEntity().getKiller();
        Player victim = e.getEntity().getPlayer();
        e.getDrops().clear();

        Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
            @Override
            public void run() {
                if (GameState.isStep(GameState.INGAME)) {
                    final PlayerInfo data = PlayerInfo.getPlayerData(victim);

                    if (!PlayerMode.isMode(PlayerMode.SOLO)) {
                        if (data.getTeam().name().equalsIgnoreCase("bleu")) {
                            ObjTeamAlive.setBleue(ObjTeamAlive.getBleue() - 1);
                        } else if (data.getTeam().name().equalsIgnoreCase("rouge")) {
                            ObjTeamAlive.setRed(ObjTeamAlive.getRed() - 1);
                        }
                    }

                    // coté attanquant
                    if (attacker != null) {
                        final PlayerInfo data1 = PlayerInfo.getPlayerData(attacker);
                        data1.increaseKills(1);
                        attacker.playSound(attacker.getLocation(), "note.pling", 10, 1);
                        for (Player player : Bukkit.getOnlinePlayers()){
                            Title.sendActionBar(player, MessageConfigEnum.PREFIX + " §c" + data.getName() + "§7 est mort par §a" + data1.getName());
                            player.sendMessage(MessageConfigEnum.PREFIX + " §c" + data.getName() + "§7 est mort par §a" + data1.getName());
                        }
                    } else {
                        for (Player survival : Bukkit.getOnlinePlayers()){
                            Title.sendActionBar(survival, MessageConfigEnum.PREFIX + " §c" + victim.getName() + "§7 est mort par le §avide");
                            survival.sendMessage(MessageConfigEnum.PREFIX + " §c" + victim.getName() + "§7 est mort par le §avide");
                        }
                    }

                    // coté victim
                    victim.setHealth(20);
                    victim.getInventory().clear();
                    victim.getActivePotionEffects().forEach(potionEffect -> victim.removePotionEffect(potionEffect.getType()));
                    victim.setMaxHealth(20);
                    victim.setHealth(victim.getMaxHealth());
                    victim.setGameMode(GameMode.SURVIVAL);
                    victim.getInventory().setItem(0, ItemStorage.sword);
                    victim.getInventory().setItem(1, ItemStorage.pickaxe);
                    victim.getInventory().setItem(2, ItemStorage.apple);
                    victim.getInventory().setItem(3, ItemStorage.blocks);
                    victim.getInventory().setItem(4, ItemStorage.blocks);
                    victim.getInventory().setItem(5, ItemStorage.blocks);
                    victim.getInventory().setItem(6, ItemStorage.blocks);
                    victim.getInventory().setItem(7, ItemStorage.blocks);
                    victim.getInventory().setItem(8, ItemStorage.blocks);

                    if (Team.BLEU.getOnlinePlayers().contains(victim)) {
                        victim.getInventory().setHelmet(ItemStorage.blue_helmet);
                        victim.getInventory().setChestplate(ItemStorage.blue_chestplate);
                        victim.getInventory().setLeggings(ItemStorage.blue_leggings);
                        victim.getInventory().setBoots(ItemStorage.blue_boots);
                        victim.teleport(SpawnLocation.BLEU.getLocation());
                    }  else if (Team.ROUGE.getOnlinePlayers().contains(victim)) {
                        victim.getInventory().setHelmet(ItemStorage.red_helmet);
                        victim.getInventory().setChestplate(ItemStorage.red_chestplate);
                        victim.getInventory().setLeggings(ItemStorage.red_leggings);
                        victim.getInventory().setBoots(ItemStorage.red_boots);
                        victim.teleport(SpawnLocation.RED.getLocation());
                    }
                }
            }
        }, 1L);
    }
}
