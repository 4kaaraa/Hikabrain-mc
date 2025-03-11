package fr.kara.heria.hikabrain.task;

import fr.kara.heria.hikabrain.Hikabrain;
import fr.kara.heria.hikabrain.config.*;
import fr.kara.heria.hikabrain.object.ObjPoint;
import fr.kara.heria.hikabrain.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BedTask extends BukkitRunnable {
    private final Hikabrain plugin;

    public BedTask(final Hikabrain plugin) {
        this.plugin = plugin;
        this.runTaskTimer(plugin, 0L, 1L);
    }

    @Override
    public void run() {
        for (Player player: Bukkit.getOnlinePlayers()) {

            if (GameState.isStep(GameState.INGAME)) {
                if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                    Location playerLocation = player.getLocation();
                    Block blockUnderPlayer = playerLocation.getBlock();

                    if (Team.ROUGE.getOnlinePlayers().contains(player)) {
                        if (blockUnderPlayer.getLocation().equals(SpawnLocation.BEDBLEUH.getLocation()) || blockUnderPlayer.getLocation().equals(SpawnLocation.BEDBLEUF.getLocation())) {

                            //Ajout du point
                            ObjPoint.setRedPoint(ObjPoint.getRedPoint() + 1);
                            Title.sendFullTitle(player, 20, 100, 20, "", "§6+1 point");
                            this.plugin.removeBlock();
                            this.plugin.placeBlock();

                            //Condition
                            if (ObjPoint.getRedPoint() < 5) {
                                player.teleport(SpawnLocation.RED.getLocation());
                                player.setHealth(20);
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
                                player.getInventory().setHelmet(ItemStorage.red_helmet);
                                player.getInventory().setChestplate(ItemStorage.red_chestplate);
                                player.getInventory().setLeggings(ItemStorage.red_leggings);
                                player.getInventory().setBoots(ItemStorage.red_boots);

                                for (Player blue : Team.BLEU.getOnlinePlayers()) {
                                    blue.teleport(SpawnLocation.BLEU.getLocation());
                                    blue.setHealth(20);
                                    blue.getInventory().clear();
                                    blue.getActivePotionEffects().forEach(potionEffect -> blue.removePotionEffect(potionEffect.getType()));
                                    blue.setMaxHealth(20);
                                    blue.setHealth(blue.getMaxHealth());
                                    blue.setGameMode(GameMode.SURVIVAL);
                                    blue.getInventory().setItem(0, ItemStorage.sword);
                                    blue.getInventory().setItem(1, ItemStorage.pickaxe);
                                    blue.getInventory().setItem(2, ItemStorage.apple);
                                    blue.getInventory().setItem(3, ItemStorage.blocks);
                                    blue.getInventory().setItem(4, ItemStorage.blocks);
                                    blue.getInventory().setItem(5, ItemStorage.blocks);
                                    blue.getInventory().setItem(6, ItemStorage.blocks);
                                    blue.getInventory().setItem(7, ItemStorage.blocks);
                                    blue.getInventory().setItem(8, ItemStorage.blocks);
                                    blue.getInventory().setHelmet(ItemStorage.blue_helmet);
                                    blue.getInventory().setChestplate(ItemStorage.blue_chestplate);
                                    blue.getInventory().setLeggings(ItemStorage.blue_leggings);
                                    blue.getInventory().setBoots(ItemStorage.blue_boots);
                                }

                                if (!PlayerMode.isMode(PlayerMode.SOLO)) {
                                    for (Player red : Team.ROUGE.getOnlinePlayers()) {
                                        Title.sendFullTitle(red, 20, 100, 20, "", "§6+1 point");
                                        red.teleport(SpawnLocation.RED.getLocation());
                                        red.setHealth(20);
                                        red.getInventory().clear();
                                        red.getActivePotionEffects().forEach(potionEffect -> red.removePotionEffect(potionEffect.getType()));
                                        red.setMaxHealth(20);
                                        red.setHealth(red.getMaxHealth());
                                        red.setGameMode(GameMode.SURVIVAL);
                                        red.getInventory().setItem(0, ItemStorage.sword);
                                        red.getInventory().setItem(1, ItemStorage.pickaxe);
                                        red.getInventory().setItem(2, ItemStorage.apple);
                                        red.getInventory().setItem(3, ItemStorage.blocks);
                                        red.getInventory().setItem(4, ItemStorage.blocks);
                                        red.getInventory().setItem(5, ItemStorage.blocks);
                                        red.getInventory().setItem(6, ItemStorage.blocks);
                                        red.getInventory().setItem(7, ItemStorage.blocks);
                                        red.getInventory().setItem(8, ItemStorage.blocks);
                                        red.getInventory().setHelmet(ItemStorage.red_helmet);
                                        red.getInventory().setChestplate(ItemStorage.red_chestplate);
                                        red.getInventory().setLeggings(ItemStorage.red_leggings);
                                        red.getInventory().setBoots(ItemStorage.red_boots);
                                    }
                                }
                            } else {
                                for (Player igPlayer : Bukkit.getOnlinePlayers()){
                                    igPlayer.teleport(SpawnLocation.SPAWN.getLocation());
                                    igPlayer.getInventory().clear();
                                    igPlayer.getEquipment().clear();
                                    igPlayer.setGameMode(GameMode.ADVENTURE);
                                    igPlayer.setAllowFlight(true);
                                    igPlayer.setFlying(true);
                                }

                                this.plugin.stopGame(Team.ROUGE);
                            }
                        }
                    }

                    if (Team.BLEU.getOnlinePlayers().contains(player)) {
                        if (blockUnderPlayer.getLocation().equals(SpawnLocation.BEDREDH.getLocation()) || blockUnderPlayer.getLocation().equals(SpawnLocation.BEDREDF.getLocation())) {
                            //Ajout du point
                            ObjPoint.setBleuePoint(ObjPoint.getBleuePoint() + 1);
                            Title.sendFullTitle(player, 20, 100, 20, "", "§6+1 point");
                            this.plugin.removeBlock();
                            this.plugin.placeBlock();

                            //Condition
                            if (ObjPoint.getBleuePoint() < 5) {
                                player.teleport(SpawnLocation.BLEU.getLocation());
                                player.setHealth(20);
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
                                player.getInventory().setHelmet(ItemStorage.blue_helmet);
                                player.getInventory().setChestplate(ItemStorage.blue_chestplate);
                                player.getInventory().setLeggings(ItemStorage.blue_leggings);
                                player.getInventory().setBoots(ItemStorage.blue_boots);

                                for (Player red : Team.ROUGE.getOnlinePlayers()) {
                                    red.teleport(SpawnLocation.RED.getLocation());
                                    red.setHealth(20);
                                    red.getInventory().clear();
                                    red.getActivePotionEffects().forEach(potionEffect -> red.removePotionEffect(potionEffect.getType()));
                                    red.setMaxHealth(20);
                                    red.setHealth(red.getMaxHealth());
                                    red.setGameMode(GameMode.SURVIVAL);
                                    red.getInventory().setItem(0, ItemStorage.sword);
                                    red.getInventory().setItem(1, ItemStorage.pickaxe);
                                    red.getInventory().setItem(2, ItemStorage.apple);
                                    red.getInventory().setItem(3, ItemStorage.blocks);
                                    red.getInventory().setItem(4, ItemStorage.blocks);
                                    red.getInventory().setItem(5, ItemStorage.blocks);
                                    red.getInventory().setItem(6, ItemStorage.blocks);
                                    red.getInventory().setItem(7, ItemStorage.blocks);
                                    red.getInventory().setItem(8, ItemStorage.blocks);
                                    red.getInventory().setHelmet(ItemStorage.red_helmet);
                                    red.getInventory().setChestplate(ItemStorage.red_chestplate);
                                    red.getInventory().setLeggings(ItemStorage.red_leggings);
                                    red.getInventory().setBoots(ItemStorage.red_boots);
                                }

                                if (!PlayerMode.isMode(PlayerMode.SOLO)) {
                                    for (Player blue : Team.BLEU.getOnlinePlayers()) {
                                        Title.sendFullTitle(blue, 20, 100, 20, "", "§6+1 point");
                                        blue.teleport(SpawnLocation.BLEU.getLocation());
                                        blue.setHealth(20);
                                        blue.getInventory().clear();
                                        blue.getActivePotionEffects().forEach(potionEffect -> blue.removePotionEffect(potionEffect.getType()));
                                        blue.setMaxHealth(20);
                                        blue.setHealth(blue.getMaxHealth());
                                        blue.setGameMode(GameMode.SURVIVAL);
                                        blue.getInventory().setItem(0, ItemStorage.sword);
                                        blue.getInventory().setItem(1, ItemStorage.pickaxe);
                                        blue.getInventory().setItem(2, ItemStorage.apple);
                                        blue.getInventory().setItem(3, ItemStorage.blocks);
                                        blue.getInventory().setItem(4, ItemStorage.blocks);
                                        blue.getInventory().setItem(5, ItemStorage.blocks);
                                        blue.getInventory().setItem(6, ItemStorage.blocks);
                                        blue.getInventory().setItem(7, ItemStorage.blocks);
                                        blue.getInventory().setItem(8, ItemStorage.blocks);
                                        blue.getInventory().setHelmet(ItemStorage.blue_helmet);
                                        blue.getInventory().setChestplate(ItemStorage.blue_chestplate);
                                        blue.getInventory().setLeggings(ItemStorage.blue_leggings);
                                        blue.getInventory().setBoots(ItemStorage.blue_boots);
                                    }
                                }
                            } else {
                                for (Player igPlayer : Bukkit.getOnlinePlayers()){
                                    igPlayer.teleport(SpawnLocation.SPAWN.getLocation());
                                    igPlayer.getInventory().clear();
                                    igPlayer.getEquipment().clear();
                                    igPlayer.setGameMode(GameMode.ADVENTURE);
                                    igPlayer.setAllowFlight(true);
                                    igPlayer.setFlying(true);
                                }

                                this.plugin.stopGame(Team.BLEU);
                            }
                        }
                    }
                }
            }
        }
    }
}
