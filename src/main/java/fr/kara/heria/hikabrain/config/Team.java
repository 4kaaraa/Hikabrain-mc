package fr.kara.heria.hikabrain.config;

import fr.kara.heria.hikabrain.utils.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public enum Team {
    BLEU("bleue", "Bleue ", ChatColor.BLUE),
    ROUGE("rouge", "Rouge ", ChatColor.RED),
    SPEC("spec", "Spectateur ", ChatColor.GRAY);

    private final ChatColor color;
    private final String name;
    private final String prefix;
    private final List<Player> players;

    Team(final String name, final String prefix, ChatColor color) {
        this.name = name;
        this.prefix = prefix;
        this.color = color;
        this.players = new ArrayList<>();
    }

    public static Team getTeam(final String name) {
        for (Team team : values()) {
            if (team.name.equals(name)) {
                return team;
            }
        }
        return null;
    }

    public void addPlayer(final Player player) {
        this.players.add(player);
        player.setPlayerListName(this.color + ((player.getName().length() > 14) ? player.getName().substring(0, 14) : player.getName()));
    }

    public static Team getRandomTeam() {
        Team rdm = RandomUtils.getRandom(Team.ROUGE, Team.BLEU);
        if (Team.BLEU.getOnlinePlayers().size() == Team.ROUGE.getOnlinePlayers().size())
            return rdm;
        if (Team.BLEU.getOnlinePlayers().size() < Team.ROUGE.getOnlinePlayers().size())
            return Team.BLEU;
        else
            return Team.ROUGE;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getPrefix() {
        return prefix;
    }

    public void removePlayer(final Player player) {
        this.players.remove(player);
    }

    public List<Player> getOnlinePlayers() {
        return players;
    }
}
