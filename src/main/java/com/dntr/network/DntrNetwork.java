package com.dntr.network;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class DntrNetwork {
    public static void register(PayloadRegistrar registrar) {
        registrar.playBidirectional(
                TableActionC2SPacket.TYPE,
                TableActionC2SPacket.CODEC,
                TableActionC2SPacket::handle);
    }
}
