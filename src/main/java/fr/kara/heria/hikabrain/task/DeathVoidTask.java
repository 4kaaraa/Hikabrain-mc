package fr.kara.heria.hikabrain.task;

import fr.kara.heria.hikabrain.Hikabrain;
import fr.kara.heria.hikabrain.config.*;
import fr.kara.heria.hikabrain.data.PlayerInfo;
import fr.kara.heria.hikabrain.object.ObjPoint;
import fr.kara.heria.hikabrain.object.ObjTeamAlive;
import fr.kara.heria.hikabrain.utils.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathVoidTask extends BukkitRunnable {
    private final Hikabrain plugin;

    public DeathVoidTask(final Hikabrain plugin) {
        this.plugin = plugin;
        this.runTaskTimer(plugin, 0L, 1L);
    }

    @Override
    public void run() {
        for (Player player: Bukkit.getOnlinePlayers()){

            if (GameState.isStep(GameState.INGAME)){
                if (player.getGameMode().equals(GameMode.SURVIVAL)){
                    if (player.getLocation().getBlockY() < 105){

                        final PlayerInfo data = PlayerInfo.getPlayerData(player);

                        if (data.getTeam().equals(Team.SPEC)){
                            player.teleport(SpawnLocation.SPEC.getLocation());
                        } else {
                            player.playSound(player.getLocation(), "random.splash", 10, 1);
                            player.teleport(SpawnLocation.SPEC.getLocation());
                            player.setGameMode(GameMode.SURVIVAL);
                            player.getInventory().clear();
                            data.increaseDeaths(1);
                            if (!PlayerMode.isMode(PlayerMode.SOLO)) {
                                if (data.getTeam().name().equalsIgnoreCase("bleu")) {
                                    ObjTeamAlive.setBleue(ObjTeamAlive.getBleue() - 1);
                                } else if (data.getTeam().name().equalsIgnoreCase("rouge")) {
                                    ObjTeamAlive.setRed(ObjTeamAlive.getRed() - 1);
                                }
                            }
                            //coté tuer
                            final Player killer = player.getKiller();
                            if (killer != null) {
                                final PlayerInfo data2 = PlayerInfo.getPlayerData(killer);
                                data2.increaseKills(1);
                                killer.playSound(killer.getLocation(), "note.pling", 10, 1);
                                for (Player survival : Bukkit.getOnlinePlayers()){
                                    Title.sendActionBar(survival, MessageConfigEnum.PREFIX + " §c" + player.getName() + "§7 est mort par §a" + data2.getName());
                                    survival.sendMessage(MessageConfigEnum.PREFIX + " §c" + player.getName() + "§7 est mort par §a" + data2.getName());
                                }
                            } else {
                                for (Player survival : Bukkit.getOnlinePlayers()){
                                    Title.sendActionBar(survival, MessageConfigEnum.PREFIX + " §c" + player.getName() + "§7 est mort par le §avide");
                                    survival.sendMessage(MessageConfigEnum.PREFIX + " §c" + player.getName() + "§7 est mort par le §avide");
                                }
                            }

                            player.getInventory().clear();
                            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
                            player.setMaxHealth(20);
                            player.setHealth(player.getMaxHealth());
                            player.setGameMode(GameMode.SURVIVAL);
                            player.getInventory().setItem(0, ItemStorage.sword);
                            player.getInventory().setItem(1, ItemStorage.pickaxe);
                            player.getInventory().setItem(2, ItemStorage.apple);
                            player.getInventory().setItem(3, ItemStorage.blocks);
                            player.getInventory().setItem(4, ItemStorage.blocks);
                            player.getInventory().setItem(5, ItemStorage.blocks);
                            player.getInventory().setItem(6, ItemStorage.blocks);
                            player.getInventory().setItem(7, ItemStorage.blocks);
                            player.getInventory().setItem(8, ItemStorage.blocks);

                            if (Team.BLEU.getOnlinePlayers().contains(player)) {
                                player.getInventory().setHelmet(ItemStorage.blue_helmet);
                                player.getInventory().setChestplate(ItemStorage.blue_chestplate);
                                player.getInventory().setLeggings(ItemStorage.blue_leggings);
                                player.getInventory().setBoots(ItemStorage.blue_boots);
                                player.teleport(SpawnLocation.BLEU.getLocation());
                            }  else if (Team.ROUGE.getOnlinePlayers().contains(player)) {
                                player.getInventory().setHelmet(ItemStorage.red_helmet);
                                player.getInventory().setChestplate(ItemStorage.red_chestplate);
                                player.getInventory().setLeggings(ItemStorage.red_leggings);
                                player.getInventory().setBoots(ItemStorage.red_boots);
                                player.teleport(SpawnLocation.RED.getLocation());
                            }
                        }
                    }
                } else if (player.getGameMode().equals(GameMode.SURVIVAL)){
                    player.teleport(SpawnLocation.SPEC.getLocation());
                    player.playSound(player.getLocation(), "mob.endermen.portal", 10, 1);
                }
            } else if (GameState.isStep(GameState.WAITING)) {
                if (player.getLocation().getBlockY() < 50) {
                    player.teleport(SpawnLocation.SPAWN.getLocation());
                    player.playSound(player.getLocation(), "mob.endermen.portal", 10, 1);
                }
            }
        }
    }
}
