package com.ancestralarcane.client;

import com.ancestralarcane.item.WandItem;
import com.ancestralarcane.data.CustomDataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class TetherRenderer {

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null || mc.isPaused()) return;

        // Check both hands for a wand
        renderTetherForHand(player, mc, InteractionHand.MAIN_HAND);
        renderTetherForHand(player, mc, InteractionHand.OFF_HAND);
    }

    private void renderTetherForHand(LocalPlayer player, Minecraft mc, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!(stack.getItem() instanceof WandItem)) return;

        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        if (!data.contains("linked_pos") || !data.contains("linked_dim")) return;

        String linkedDim = data.getString("linked_dim");
        String currentDim = mc.level.dimension().location().toString();

        if (!linkedDim.equals(currentDim)) return;

        BlockPos linkedPos = BlockPos.of(data.getLong("linked_pos"));
        Vec3 end = Vec3.atCenterOf(linkedPos);
        Vec3 start = player.getEyePosition(1.0F).subtract(0, 0.4, 0); // Start slightly below eyes

        double distSq = start.distanceToSqr(end);
        if (distSq > 64 * 64) return; // Limit to 64 blocks range

        double dist = Math.sqrt(distSq);
        Vec3 dir = end.subtract(start).normalize();

        // Spawn a line of particles
        // Use a lower count to avoid lag, but enough to look like a beam
        int particleCount = (int) (dist * 1.5);
        for (int i = 0; i < particleCount; i++) {
            double step = (double) i / 1.5;
            Vec3 pos = start.add(dir.scale(step));
            
            // Subtle Portal particles along the line
            mc.level.addParticle(ParticleTypes.PORTAL, 
                pos.x, pos.y, pos.z, 
                (mc.level.random.nextDouble() - 0.5) * 0.1, 
                (mc.level.random.nextDouble() - 0.5) * 0.1, 
                (mc.level.random.nextDouble() - 0.5) * 0.1);
        }
    }
}
