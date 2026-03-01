package com.ancestralarcane.network;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class AncestralArcaneNetwork {
    public static void register(PayloadRegistrar registrar) {
        registrar.playBidirectional(
                TableActionC2SPacket.TYPE,
                TableActionC2SPacket.CODEC,
                TableActionC2SPacket::handle);
    }
}
