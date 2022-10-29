package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.blocks.machines.AbstractMachineBlock;
import earth.terrarium.ad_astra.util.ModInventory;
import earth.terrarium.botarium.api.menu.ExtraDataMenuProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements ExtraDataMenuProvider, ModInventory, SidedInventory {

    private final DefaultedList<ItemStack> inventory;

    public AbstractMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        inventory = DefaultedList.ofSize(getInventorySize(), ItemStack.EMPTY);
    }

    public abstract void tick();

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }

    public int getInventorySize() {
        return 0;
    }

    public void setActive(boolean active) {
        if (this.getCachedState().contains(AbstractMachineBlock.LIT)) {
            if (this.getWorld() != null) {
                this.getWorld().setBlockState(this.getPos(), this.getCachedState().with(AbstractMachineBlock.LIT, active));
            }
        }
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void writeExtraData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (getInventorySize() > 0) {
            Inventories.readNbt(nbt, this.inventory);
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (getInventorySize() > 0) {
            Inventories.writeNbt(nbt, this.inventory);
        }
    }

    // Updates the chunk every time the energy is changed. Important for updating
    // the screen to show the latest energy value.
    @Override
    public void markDirty() {
        super.markDirty();

        if (this.world instanceof ServerWorld serverWorld) {
            serverWorld.getChunkManager().markForUpdate(this.pos);
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        ItemStack slotStack = this.getStack(slot);
        return slotStack.isEmpty() || (slotStack.isOf(stack.getItem()) && slotStack.getCount() <= slotStack.getMaxCount());
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.of(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.toNbt();
    }
}