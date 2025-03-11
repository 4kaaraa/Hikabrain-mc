package fr.kara.heria.hikabrain.config;

import lombok.Getter;
import lombok.Setter;

public enum PlayerMode {
    SOLO,
    DUO,
    QUATOR;

    @Setter
    @Getter
    private static PlayerMode currentMode = PlayerMode.SOLO;

    public static boolean isMode(final PlayerMode step) {
        return PlayerMode.currentMode == step;
    }

}
