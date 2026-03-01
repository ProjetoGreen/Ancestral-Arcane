package com.ancestralarcane.network;

import com.ancestralarcane.block.ArcaneSmithingTableBlockEntity;
import com.ancestralarcane.ui.menu.ArcaneSmithingMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TableActionC2SPacket(int actionId) implements CustomPacketPayload {
    public static final Type<TableActionC2SPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath("ancestral_arcane", "table_action"));
    public static final StreamCodec<FriendlyByteBuf, TableActionC2SPacket> CODEC = StreamCodec.composite(
            net.minecraft.network.codec.ByteBufCodecs.INT, TableActionC2SPacket::actionId,
            TableActionC2SPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final TableActionC2SPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof ArcaneSmithingMenu menu) {
                menu.handleAction(payload.actionId);
            }
        });
    }
}
