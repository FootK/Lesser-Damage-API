package adl.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class EntityDefenceProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<EntityDefence> ENTITY_LIVES = CapabilityManager.get(new CapabilityToken<EntityDefence>() { });

    private EntityDefence lives = null;
    private final LazyOptional<EntityDefence> optional = LazyOptional.of(this::createEntityLives);

    private EntityDefence createEntityLives() {
        if(this.lives == null) {
            this.lives = new EntityDefence();
        }

        return this.lives;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ENTITY_LIVES) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createEntityLives().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createEntityLives().loadNBTData(nbt);
    }
}

