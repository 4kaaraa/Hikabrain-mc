package fr.kara.heria.hikabrain.config;

public enum MessageConfigEnum {
    PREFIX("§6§lHIKABRAIN §8┃"),
    ;




    // Paramater
    private final String click;

    MessageConfigEnum(String click) {
        this.click = click;
    }

    public String getClick() {
        return click;
    }

    @Override
    public String toString() {
        return click;
    }
}
