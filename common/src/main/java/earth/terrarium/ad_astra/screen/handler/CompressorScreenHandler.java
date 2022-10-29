package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.CompressorBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

import java.util.List;

public class CompressorScreenHandler extends AbstractMachineScreenHandler<CompressorBlockEntity> {

    public CompressorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (CompressorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));

    }

    public CompressorScreenHandler(int syncId, PlayerInventory inventory, CompressorBlockEntity entity) {
        super(ModScreenHandlers.COMPRESSOR_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[]{new Slot(entity, 0, 40, 62), new Slot(entity, 1, 92, 62) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        }});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 30;
    }

    @Override
    public void syncClientScreen() {
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), List.of()), this.player);
    }
}