package fr.kara.heria.hikabrain.object;

import fr.kara.heria.hikabrain.config.PlayerMode;
import lombok.Getter;
import lombok.Setter;

public class ObjTeamAlive {
    @Getter @Setter
    private static int red, bleue;

    public ObjTeamAlive(int red, int bleue) {
        if (!PlayerMode.isMode(PlayerMode.SOLO)){
            ObjTeamAlive.red = red;
            ObjTeamAlive.bleue = bleue;
        }
    }
}
