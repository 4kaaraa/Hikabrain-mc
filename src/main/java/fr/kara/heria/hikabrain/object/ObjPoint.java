package fr.kara.heria.hikabrain.object;

import lombok.Getter;

public class ObjPoint {
    @Getter
    private static int bleuePoint, redPoint;

    public ObjPoint(int bleuePoint, int redPoint) {
        ObjPoint.bleuePoint = bleuePoint;
        ObjPoint.redPoint = redPoint;
    }

    public static void setBleuePoint(int bleuePoint) {
        ObjPoint.bleuePoint = bleuePoint;
    }

    public static void setRedPoint(int redPoint) {
        ObjPoint.redPoint = redPoint;
    }
}
