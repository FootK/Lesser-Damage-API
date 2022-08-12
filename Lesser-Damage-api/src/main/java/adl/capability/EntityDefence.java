package adl.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class EntityDefence {
    // Makes you invulnerability against too weaker tools
    private int invulnerabilityLevel = 0;
    // Amount of lives you have
    private int lives = 1;
    // Last time lives reset
    private int lastTimeLivesReset;
    // Divides damage you take
    private long damageDivision = 1;
    // Saves the amount of damage taken for Division
    private long underDamageDivision;


    private int getInvulnerability() {
        return invulnerabilityLevel;
    }

    static int invul;
    public static int getInvulLevel(LivingEntity entity) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(L -> invul = L.getInvulnerability());
        return invul;
    }

    private void setInvul(int amount) {
        this.invulnerabilityLevel = amount;
    }

    public static void setinvulnerabilityLevel(LivingEntity entity, int amount) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(B -> B.setInvul(amount));
    }


    private long getDamageDiv() {
        return damageDivision;
    }

    private static long damageDiv;

    public static long getDamageDivision(LivingEntity entity) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(L -> damageDiv = L.getDamageDiv());
        return damageDiv;
    }



    public static void setDamageDivision(LivingEntity entity, long amount) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(L -> L.setDamageDiv(amount));
    }

    public void setDamageDiv(long amount) {
        if (amount > 2147483647l) {
            this.damageDivision = 2147483647l;
            System.out.println(2147483647l);

        } else if (amount < 0l) {
            this.damageDivision = 1l;
            System.out.println(0l);

        } else {
            this.damageDivision = amount;
            System.out.println(amount);

        }
    }


    public long getUnderDamageDiv() {
        return underDamageDivision;
    }

    private static long underDamageDiv;

    public static long getUnderDamageDivision(LivingEntity entity) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(L -> underDamageDiv = L.getUnderDamageDiv());
        return underDamageDiv;
    }

    private void setUnderDamageDiv(long amount) {
        this.underDamageDivision = amount;
    }

    public static void setUnderDamageDivision(LivingEntity entity, long amount) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(L -> L.setUnderDamageDiv(amount));
    }


    private int getALife() {
        return lives;
    }

    private static int Lives;

    public static int getLives(LivingEntity entity) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(L -> Lives = L.getALife());
        return Lives;
    }

    private void setLife(int amount) {
        if (amount > 2048) {
            this.lives = 2048;

        } else if (amount < 1) {
            this.lives = 1;

        } else {
            this.lives = amount;
        }
    }

    public static void setLives(LivingEntity entity, int amount) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(L -> L.setLife(amount));
    }

    private int getlastTime() {
        return lastTimeLivesReset;
    }

    private static int LastTimeReset;
    public static int getLastTimeReset(LivingEntity entity) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(L -> LastTimeReset = L.getlastTime());
        return LastTimeReset;
    }

    private void setLastTime(int amount) {
        if (!(amount >= 2048) || !(amount < 0)) {
            this.lastTimeLivesReset = amount;

        } else if (amount >= 2048) {
            this.lastTimeLivesReset = 2048;

        } else if (amount <= 1) {
            this.lastTimeLivesReset = 0;
        }
    }

    public static void setLastTimeReset(LivingEntity entity, int amount) {
        entity.getCapability(EntityDefenceProvider.ENTITY_LIVES).ifPresent(L -> L.setLastTime(amount));
    }


    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("invulnerabilityLevel", invulnerabilityLevel);

        nbt.putInt("lives", lives);
        nbt.putInt("i", lastTimeLivesReset);

        nbt.putLong("damageDivision", damageDivision);
        nbt.putLong("underDamageDivision", underDamageDivision);
    }
    public void loadNBTData(CompoundTag nbt) {
        invulnerabilityLevel = nbt.getInt("invulnerabilityLevel");

        lives = nbt.getInt("lives");
        lastTimeLivesReset = nbt.getInt("lastTimeReset");

        damageDivision = nbt.getLong("damageDivision");
        underDamageDivision = nbt.getLong("underDamageDivision");

    }

}