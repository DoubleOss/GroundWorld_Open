package com.doubleos.gw.packet;

import com.doubleos.gw.automine.AutoMine;
import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.automine.AutoMineList;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AutoMineAreaPacket implements IMessage {
    private byte[] data;

    public AutoMineAreaPacket() {
    }

    public AutoMineAreaPacket(byte[] data) {
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int length = buf.readInt();
        this.data = new byte[length];
        buf.readBytes(this.data);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.data.length);
        buf.writeBytes(this.data);
    }

    public static class Handler implements IMessageHandler<AutoMineAreaPacket, IMessage> {
        @Override
        public IMessage onMessage(AutoMineAreaPacket message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                AutoMineArea autoMineArea = AutoMineArea.fromBytes(message.data);
                if (autoMineArea != null) {
                    // 서버 측 로직 처리
                    autoMineArea.initHash();
                    AutoMineList.Instance().m_mapAreaList.set(autoMineArea.areaId, autoMineArea);

                }
            });
            return null;
        }
    }
}
