package adl.event;

import adl.entity.ModAttributes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.UUID;

import static adl.capability.EntityDefence.*;
import static adl.entity.ModAttributes.getDodge;
import static adl.entity.ModAttributes.getMaxLives;


public final class EntityEvents {
    public static void preInit() {
        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(EventPriority.NORMAL, false, LivingAttackEvent.class, EntityEvents::onAttacked);
        forgeBus.addListener(EventPriority.NORMAL, false, LivingDamageEvent.class, EntityEvents::onDamaged);
        forgeBus.addListener(EventPriority.NORMAL, false, LivingEvent.LivingTickEvent.class, EntityEvents::onEntityUpdate);
        forgeBus.addListener(EventPriority.NORMAL, false, EntityJoinLevelEvent.class, EntityEvents::onEntityJoin);

    }



    private static void onAttacked(LivingAttackEvent ev) {
        if (ev.getSource() == DamageSource.OUT_OF_WORLD) {
            ev.setCanceled(false);
        } else if (!(ev.getEntity() instanceof LivingEntity)) {
        } else if (ev.getEntity() instanceof Player && ((Player) ev.getEntity()).isCreative()) {
            ev.setCanceled(true);
        } else if ((int) (Math.random() * 100) + 1 <= getDodge(ev.getEntity()) * 100) {
            ev.setCanceled(true);
        }
    }
    private static void onEntityUpdate(LivingEvent.LivingTickEvent ev) {

        if (ev.getEntity().getLevel() instanceof ServerLevel) {
            if (!ev.getEntity().isAlive()) {
                setLastTimeReset(ev.getEntity(), 0);

            } else if (getLastTimeReset(ev.getEntity()) >= 60) {
                setLastTimeReset(ev.getEntity(), 0);

                setLives(ev.getEntity(), (int) getMaxLives(ev.getEntity()));
            } else if (ev.getEntity().tickCount % 20 == 0 && getLives(ev.getEntity()) < (int) getMaxLives(ev.getEntity())) {
                setLastTimeReset(ev.getEntity(), getLastTimeReset(ev.getEntity()) + 1);
            } else if (getMaxLives(ev.getEntity()) == getLives(ev.getEntity()) && getLastTimeReset(ev.getEntity()) != 0) {
                setLastTimeReset(ev.getEntity(), 0);
            }
        }
    }

    private static void onDamaged(final LivingDamageEvent ev) {
        if (getDamageDivision(ev.getEntity()) > 1 && ev.getSource() != DamageSource.OUT_OF_WORLD) {
            System.out.println("start");

            if (ev.getAmount() > 0) {
                setUnderDamageDivision(ev.getEntity(), (long) (getUnderDamageDivision(ev.getEntity()) + ev.getAmount()));
                ev.setAmount(0);
            }

            if (getUnderDamageDivision(ev.getEntity()) >= getDamageDivision(ev.getEntity())) {
                long amount = getUnderDamageDivision(ev.getEntity()) / getDamageDivision(ev.getEntity());
                ev.setAmount(amount);
                setUnderDamageDivision(ev.getEntity(), (long) (amount - getDamageDivision(ev.getEntity())));

                ev.getSource().bypassMagic().bypassEnchantments().bypassArmor();

            }
        }


        if (ev.getSource() == DamageSource.OUT_OF_WORLD) {
        } else if (ev.getEntity().getHealth() - ev.getAmount() > 0) {

        } else if (ev.getSource().getEntity() instanceof Projectile projectile && projectile.getOwner() instanceof LivingEntity && ev.getEntity().getHealth() - ev.getAmount() <= 0 && getLives(ev.getEntity()) - ModAttributes.getLifeDamage((LivingEntity) ev.getSource().getEntity()) >= 1) {
            setLives(ev.getEntity(), getLives(ev.getEntity()) - (int) ModAttributes.getLifeDamage((LivingEntity) projectile.getOwner()));
            ev.getEntity().setHealth(ev.getEntity().getMaxHealth());
            ev.setCanceled(true);

        } else if (!(ev.getSource().getEntity() instanceof LivingEntity) && ev.getEntity().getHealth() - ev.getAmount() <= 0 && getLives(ev.getEntity()) - 1 >= 1) {
            setLives(ev.getEntity(), getLives(ev.getEntity()) - 1);
            ev.getEntity().setHealth(ev.getEntity().getMaxHealth());
            ev.setCanceled(true);

        } else if (ev.getSource().getEntity() instanceof LivingEntity && ev.getEntity().getHealth() - ev.getAmount() <= 0 && getLives(ev.getEntity()) - ModAttributes.getLifeDamage((LivingEntity) ev.getSource().getEntity()) >= 1) {
            setLives(ev.getEntity(), getLives(ev.getEntity()) - (int) ModAttributes.getLifeDamage(ev.getEntity()));
            ev.getEntity().setHealth(ev.getEntity().getMaxHealth());
            ev.setCanceled(true);

        } else {
            setUnderDamageDivision(ev.getEntity(), 0l);

        }
    }

    public static void onEntityJoin(EntityJoinLevelEvent ev) {
        if (!(ev.getEntity() instanceof Player) && ev.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) ev.getEntity();
            setLives(entity, (int) getMaxLives(entity));
        }
    }
}
