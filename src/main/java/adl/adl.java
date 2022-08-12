package adl;

import adl.client.renderer.gui.hud.DamageDivider;
import adl.client.renderer.gui.hud.LivesStatusRenderer;
import adl.config.adlClientConfigs;
import adl.entity.ModAttributes;
import adl.event.EntityEvents;
import adl.event.PlayerEvents;
import adl.packets.ModMessages;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;


@Mod(adl.MOD_ID)
public class adl {
    public static final String MOD_ID = "adl";

    public static IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();


    public adl()
    {
        initRegistrations();
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::attributeSetup);

        ModAttributes.register(eventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, adlClientConfigs.SPEC, "adl-client.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    private static void initRegistrations() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            LivesStatusRenderer.init();
            DamageDivider.init();
        });
    }

    private void attributeSetup(final EntityAttributeModificationEvent ev) {

        for (EntityType<? extends LivingEntity> type : ev.getTypes()) {
            ev.add(type, ModAttributes.MAXLIVES.get());
            ev.add(type, ModAttributes.LIFE_DAMAGE.get());
            ev.add(type, ModAttributes.DODGE.get());
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            EntityEvents.preInit();
            PlayerEvents.preInit();

            ModMessages.register();
        });
    }
}
