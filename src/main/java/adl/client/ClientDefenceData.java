package adl.client;

public class ClientDefenceData {
    private static int EntityLives;
    private static int damageDivider;

    public static void set(int lives) {
        ClientDefenceData.EntityLives = lives;
    }
    public static void setDamageDivider(long damageDivider) {
        ClientDefenceData.damageDivider = (int) damageDivider;
    }

    public static int getEntityLives() {
        return EntityLives;
    }

    public static int getDamageDivider() {
        return damageDivider;
    }
}
