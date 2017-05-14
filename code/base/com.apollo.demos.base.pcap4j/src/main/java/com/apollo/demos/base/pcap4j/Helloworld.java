/*
 * 此代码创建于 2017年5月13日 下午5:01:24。
 */
package com.apollo.demos.base.pcap4j;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.util.NifSelector;

public class Helloworld {

    public static void main(String[] args) {
        PcapHandle sendHandle = null;
        try {
            PcapNetworkInterface nif = new NifSelector().selectNetworkInterface();
            sendHandle = nif.openLive(1000, PromiscuousMode.PROMISCUOUS, 100);
            sendHandle.sendPacket("Pcap4J Helloworld.".getBytes());
            System.out.println("Send is OK.");

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            if (sendHandle != null) {
                sendHandle.close();
            }
        }
    }

}
