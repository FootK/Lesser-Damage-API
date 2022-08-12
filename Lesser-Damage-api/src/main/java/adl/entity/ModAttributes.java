package adl.entity;

import adl.adl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = adl.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, adl.MOD_ID);


    // Maximum amount of lives you can have
    public static final RegistryObject<Attribute> MAXLIVES = ATTRIBUTES.register("maxlives",
            () -> new RangedAttribute("attribute.maxlives", 1, 1, 2048).setSyncable(true));
    // Chance to dodge an attack
    public static final RegistryObject<Attribute> DODGE = ATTRIBUTES.register("dodge",
            () -> new RangedAttribute("attribute.dodge", 0.00D, 0.00D, 1.00D).setSyncable(true));
    // Extra lives damage
    public static final RegistryObject<Attribute> LIFE_DAMAGE =  ATTRIBUTES.register("life_damage",
            () -> new RangedAttribute("attribute.life_damage", 1, 1, 192).setSyncable(true));
    
    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }



    public static double getMaxLives(LivingEntity entity) {
        return entity.getAttributes().getValue(ModAttributes.MAXLIVES.get());
    }

    public static double getLifeDamage(LivingEntity entity) {
        return entity.getAttributes().getValue(ModAttributes.LIFE_DAMAGE.get());
    }

    public static double getDodge(LivingEntity entity) {
        return entity.getAttributes().getValue(ModAttributes.DODGE.get());
    }
}
