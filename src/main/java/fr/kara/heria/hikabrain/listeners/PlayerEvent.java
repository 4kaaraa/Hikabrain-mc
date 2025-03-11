package fr.kara.heria.hikabrain.listeners;

import fr.kara.heria.hikabrain.config.GameState;
import fr.kara.heria.hikabrain.config.Team;
import fr.kara.heria.hikabrain.data.PlayerInfo;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerEvent implements Listener {

    @Getter
    private static final List<Block> blocks = new ArrayList<>();
    @Getter
    private static final List<Block> pblocks = new ArrayList<>();

    @EventHandler
    public void DamageEvent(EntityDamageByEntityEvent e) {
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        final PlayerInfo data = PlayerInfo.getPlayerData(attacker);
        final PlayerInfo data1 = PlayerInfo.getPlayerData(victim);

        if (data.isSpectateur() || data.isWaiting()) {
            e.setCancelled(true);
        }

        if (GameState.isStep(GameState.INGAME)) {
            if (data.getTeam().name().equals(data1.getTeam().name())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void voidCancel(EntityDamageEvent e){
        Player victim = (Player) e.getEntity();
        final PlayerInfo data = PlayerInfo.getPlayerData(victim);

        if (data.isSpectateur() || data.isWaiting()){
            e.setCancelled(true);
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void CancelEvent(BlockBreakEvent e) {
        if (GameState.isStep(GameState.WAITING)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent e){
        if (Team.SPEC.getOnlinePlayers().contains(e.getPlayer())){
            e.setCancelled(true);
        }

        if (GameState.isStep(GameState.WAITING)){
            e.setCancelled(true);
        } else if (GameState.isStep(GameState.INGAME)) {
            blocks.add(e.getBlockPlaced());
            if (e.getBlock().getLocation().getBlockY() >= 116){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Material blockType = e.getBlock().getType();

        if (blockType == Material.BED_BLOCK || blockType == Material.GLASS || blockType == Material.OBSIDIAN) {
            e.setCancelled(true);
        } else if (GameState.isStep(GameState.INGAME)) {
            if (e.getBlock().getLocation().getBlockY() >= 116) {
                e.getBlock().setType(Material.AIR);
            } else {
                pblocks.add(e.getBlock());
            }
        }
    }

    @EventHandler
    public void WeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void inventoryItemMove(InventoryDragEvent e){
        //Player player = e.getWhoClicked().getKiller();

        if (GameState.isStep(GameState.WAITING)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void interactionItem(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (GameState.isStep(GameState.WAITING)) {
            if (e.hasItem()) {
                e.setCancelled(true);
                if (e.getAction() != Action.LEFT_CLICK_AIR) {
                    if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cQuitter §8▸ ")) {
//                        HeriaServer heriaServer = HeriaAPI.get().getServerManager().getWithLessPlayers(HeriaServerType.HUB);
//                        HeriaAPI.get().getMessaging().send(new SendPlayerPacket(player.getUniqueId(), heriaServer.getName()));
                    }
                }
            }
        }
    }

    @EventHandler
    public void hungerMeterChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }
}
