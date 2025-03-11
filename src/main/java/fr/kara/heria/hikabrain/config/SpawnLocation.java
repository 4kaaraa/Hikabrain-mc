package fr.kara.heria.hikabrain.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum SpawnLocation {
    SPAWN(new Location(Bukkit.getWorld("world"), -5.5, 110, 2.5, -90, 1)),
    SPEC(new Location(Bukkit.getWorld("world"), -168.5, 119, 4.5, -1, 89)),

    //Bleu
    BLEU(new Location(Bukkit.getWorld("world"), -156.3, 117, 4.5, 90, 8)),
    BEDBLEUH(new Location(Bukkit.getWorld("world"), -157, 112, 4)),
    BEDBLEUF(new Location(Bukkit.getWorld("world"), -158, 112, 4)),

    //Rouge
    RED(new Location(Bukkit.getWorld("world"), -180.5, 116, 4.5, -90, 4)),
    BEDREDH(new Location(Bukkit.getWorld("world"), -181, 112, 4)),
    BEDREDF(new Location(Bukkit.getWorld("world"), -180, 112, 4)),
    ;


    private final Location location;

    SpawnLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
