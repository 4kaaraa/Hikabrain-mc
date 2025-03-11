package fr.kara.heria.hikabrain;

import fr.kara.heria.hikabrain.config.PlayerMode;
import fr.kara.heria.hikabrain.data.PlayerInfo;
import fr.kara.heria.hikabrain.config.GameState;
import fr.kara.heria.hikabrain.config.MessageConfigEnum;
import fr.kara.heria.hikabrain.config.Team;
import fr.kara.heria.hikabrain.listeners.*;
import fr.kara.heria.hikabrain.object.ObjConfig;
import fr.kara.heria.hikabrain.scorboard.ScoreboardManager;
import fr.kara.heria.hikabrain.task.DeathVoidTask;
import fr.kara.heria.hikabrain.task.TerminatedGameTask;
import fr.kara.heria.hikabrain.task.WaitingTask;
import fr.kara.heria.hikabrain.utils.Title;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Hikabrain extends JavaPlugin {

    @Getter
    private static Hikabrain instance;

    @Setter
    @Getter
    private WaitingTask waitingTask;

    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onEnable() {
        instance = this;

        GameState.setCurrentStep(GameState.WAITING);
        new ObjConfig(PlayerMode.SOLO, 5, 5, 2);
        System.out.println("Hikabrain Loaded - made by kara");

        this.registerListeners();

        Bukkit.getWorld("world").setTime(6000);
        Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();
        new DeathVoidTask(this);
    }

    @Override
    public void onDisable() {
        getScoreboardManager().onDisable();
    }

    public void stop() {

    }

    public void registerListeners() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoin(this), this);
        pluginManager.registerEvents(new PlayerQuit(this), this);
        pluginManager.registerEvents(new PlayerDeath(this), this);
        pluginManager.registerEvents(new PlayerEvent(), this);
        pluginManager.registerEvents(new PlayerChat(), this);
    }

    public void removePlayer(final Player player) {
        final PlayerInfo data = PlayerInfo.getPlayerData(player);
        final Team team = data.getTeam();
        if (!data.isSpectateur()) {
            data.setTeam(Team.SPEC);
            if (GameState.isStep(GameState.INGAME) && team.getOnlinePlayers().isEmpty()) {
                final Team winnerTeam = team == Team.BLEU ? Team.ROUGE : Team.BLEU;
                final Team winnerSureTeam = stopGame(winnerTeam);
            }
        }
    }

    public Team stopGame(Team winnerTeam) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setGameMode(GameMode.ADVENTURE);
            online.setAllowFlight(true);
            online.setFlying(true);
            Title.sendFullTitle(online, 20, 100, 20, "§b§lGAGNANT", "§fL'équipe " + winnerTeam.getColor() + winnerTeam.getPrefix() + "§fa gagné");
        }
        new TerminatedGameTask(this);

        for (Map.Entry<OfflinePlayer, PlayerInfo> entry : PlayerInfo.getEntries()) {
            final OfflinePlayer offPlayer = entry.getKey();
            final PlayerInfo data = entry.getValue();
            if (winnerTeam != null && winnerTeam != Team.SPEC && offPlayer.isOnline()) {
                Player player = (Player) offPlayer;
                if (data.getTeam() == winnerTeam) {
                    //HikabrainData hikabrainData = Hikabrain.getInstance().hikabrainDataManager.get(player.getUniqueId());
                    data.increaseWins(1);

                    int randomNumber2 = ThreadLocalRandom.current().nextInt(10, 21);
                    player.sendMessage(MessageConfigEnum.PREFIX + " §7Vous avez gagné §a" + randomNumber2 + " §7points");

                    //hikabrainData.setPoint(hikabrainData.getPoint() + data.getKill() - data.getDeath() + randomNumber2);

                    //Hikabrain.getInstance().hikabrainDataManager.save(hikabrainData);

                } else if(data.getTeam() != winnerTeam) {
                    //HikabrainData hikabrainData = Hikabrain.getInstance().hikabrainDataManager.get(player.getUniqueId());

                    int randomNumber1 = ThreadLocalRandom.current().nextInt(1, 11);
                    //hikabrainData.setPoint(hikabrainData.getPoint() + data.getKill() - data.getDeath() +randomNumber1);

                    player.sendMessage(MessageConfigEnum.PREFIX + " §7Vous avez gagné §a" + randomNumber1 + " §7points");
                    //Hikabrain.getInstance().hikabrainDataManager.save(hikabrainData);
                } else {
                    player.sendMessage(MessageConfigEnum.PREFIX + " §7Developper §aKARA");
                }
            }
        }
        stop();
        return winnerTeam;
    }

    public boolean hasWaitingTaskStarted() {
        return this.waitingTask != null;
    }

    public void removeBlock(){
        for (Block block: PlayerEvent.getBlocks()){
            Bukkit.getWorld("world").getBlockAt(block.getLocation()).setType(Material.AIR);
        }
    }

    public void placeBlock(){
        for (Block block: PlayerEvent.getPblocks()){
            Bukkit.getWorld("world").getBlockAt(block.getLocation()).setType(Material.SANDSTONE);
        }
    }
}
