package fr.kara.heria.hikabrain.task;

import fr.kara.heria.hikabrain.Hikabrain;
import fr.kara.heria.hikabrain.config.*;
import fr.kara.heria.hikabrain.data.PlayerInfo;
import fr.kara.heria.hikabrain.object.ObjPoint;
import fr.kara.heria.hikabrain.utils.Nametag;
import fr.kara.heria.hikabrain.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class WaitingTask extends BukkitRunnable {
    private final Hikabrain plugin;
    private boolean started = false;
    private boolean forced = false;
    private int timeUntilStart;

    public WaitingTask(final Hikabrain plugin) {
        this.plugin = plugin;
        if (PlayerMode.isMode(PlayerMode.SOLO)) {
            this.timeUntilStart = 15;
        } else {
            this.timeUntilStart = 45;
        }
        plugin.setWaitingTask(this);
        start();
        this.runTaskTimer(plugin, 0L, 20L);
    }

    public void run() {
        if (this.timeUntilStart == 0) {
            this.cancel();
            if (Bukkit.getOnlinePlayers().size() >= (forced ? 1 : 2)) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    final PlayerInfo data = PlayerInfo.getPlayerData(player);
                    if (!data.hasTeam()){
                        data.setTeam(Team.getRandomTeam());
                    }

                    if (Team.ROUGE.getOnlinePlayers().isEmpty() || Team.BLEU.getOnlinePlayers().isEmpty()){
                        for (Player p: Bukkit.getOnlinePlayers()){
                            final PlayerInfo pdata = PlayerInfo.getPlayerData(p);

                            data.setTeam(Team.SPEC);
                            Nametag.setNameTag(player, "§fRandom ", "", 3);
                            data.setTeam(Team.getRandomTeam());
                        }
                    }
                    final Team team = data.getTeam();

                    if (team.name().equalsIgnoreCase("bleu")) {
                        player.teleport(SpawnLocation.BLEU.getLocation());
                        Nametag.setNameTag(player, "§9Bleue ", "", 1);
                    } else if (team.name().equalsIgnoreCase("rouge")) {
                        player.teleport(SpawnLocation.RED.getLocation());
                        Nametag.setNameTag(player, "§cRouge ", "", 2);
                    }

                    //Ajout de la game
                    data.increaseGames(1);

                    //Setup de l'inv
                    player.getInventory().clear();
                    player.setExp(0);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setHealth(player.getMaxHealth());
                    data.setWaiting(false);
                }
                GameState.setCurrentStep(GameState.INGAME);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    final PlayerInfo data = PlayerInfo.getPlayerData(player);
                    final Team team = data.getTeam();

                    //Inventaire
                    player.getActivePotionEffects().clear();
                    player.getInventory().clear();
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

                    if (team.name().equalsIgnoreCase("bleu")) {
                        player.getInventory().setHelmet(ItemStorage.blue_helmet);
                        player.getInventory().setChestplate(ItemStorage.blue_chestplate);
                        player.getInventory().setLeggings(ItemStorage.blue_leggings);
                        player.getInventory().setBoots(ItemStorage.blue_boots);
                    }  else if (team.name().equalsIgnoreCase("rouge")) {
                        player.getInventory().setHelmet(ItemStorage.red_helmet);
                        player.getInventory().setChestplate(ItemStorage.red_chestplate);
                        player.getInventory().setLeggings(ItemStorage.red_leggings);
                        player.getInventory().setBoots(ItemStorage.red_boots);
                    }
                }
                new GameTask(this.plugin);
                new BedTask(this.plugin);
                new ObjPoint(0, 0);
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendActionBar(player, MessageConfigEnum.PREFIX + " §cPas assez de joueurs");
                }
                this.timeUntilStart = 60;
                started = false;
            }
            return;
        }
        int remainingMins = this.timeUntilStart / 60 % 60;
        int remainingSecs = this.timeUntilStart % 60;
        float progress = (float) this.timeUntilStart / (float) 60;
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setExp(progress);
            online.setLevel(remainingSecs);
        }
        if (this.timeUntilStart % 30 == 0 || (remainingMins == 0 && (remainingSecs % 10 == 0 || remainingSecs < 10))) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(MessageConfigEnum.PREFIX + " §fLa partie commence dans §6" + remainingSecs + " secondes");
                Title.sendFullTitle(online, 20, 100, 20, "§6" + remainingSecs + "s", "§fDémarrage de la partie:");
                online.playSound(online.getLocation(), "note.pling", 10, 1);
            }
        }
        --this.timeUntilStart;
    }

    public void shortenCountdown() {
        forceStarting();
        if (this.timeUntilStart > 10)
            this.timeUntilStart = 10;
    }

    public int getRemainingSeconds() {
        return Math.max(0, this.timeUntilStart);
    }

    public void start() {
        started = true;
    }

    public void forceStarting() {
        forced = true;
    }

    public boolean hasStarted() {
        return started;
    }

    public boolean wasForced() {
        return forced;
    }
}
