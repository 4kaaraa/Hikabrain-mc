package fr.kara.heria.hikabrain.object;

import fr.kara.heria.hikabrain.config.PlayerMode;
import lombok.Getter;

public class ObjConfig {
    @Getter
    private static PlayerMode playerMode;
    private static int round, maxRound, nbRedPoint, nbBleuPoint, nbJoueur;

    public ObjConfig(PlayerMode playerMode, int nbRedPoint, int nbBleuPoint, int nbJoueur) {
        ObjConfig.playerMode = playerMode;
        ObjConfig.nbRedPoint = nbRedPoint;
        ObjConfig.nbBleuPoint = nbBleuPoint;
        ObjConfig.nbJoueur = nbJoueur;
    }

    //set

    public static void setMaxRound(int maxRound) {
        ObjConfig.maxRound = maxRound;
    }

    public static void setNbRedPoint(int nbRedPoint) {
        ObjConfig.nbRedPoint = nbRedPoint;
    }

    public static void setNbBleuPoint(int nbBleuPoint) {
        ObjConfig.nbBleuPoint = nbBleuPoint;
    }

    public static void setNbJoueur(int nbJoueur) {
        ObjConfig.nbJoueur = nbJoueur;
    }

    public static int getNbJoueur() {
        return nbJoueur;
    }
}
