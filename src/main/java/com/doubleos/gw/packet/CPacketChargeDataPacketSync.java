package com.doubleos.gw.packet;

import com.doubleos.gw.util.GallData;
import com.doubleos.gw.variable.ChargeData;
import com.doubleos.gw.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketChargeDataPacketSync implements IMessage {
    private byte[] data;

    public CPacketChargeDataPacketSync() {
    }

    public CPacketChargeDataPacketSync(byte[] data) {
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

    public static class Handler implements IMessageHandler<CPacketChargeDataPacketSync, IMessage> {
        @Override
        public IMessage onMessage(CPacketChargeDataPacketSync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                ChargeData data = ChargeData.fromBytes(message.data);;
                if (data != null)
                {
                    // 서버 측 로직 처리
                    Variable.Instance().m_chargeDataList.add(data);

                }
            });
            return null;
        }
    }
}
