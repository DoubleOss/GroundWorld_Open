package com.doubleos.gw.packet;


import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Packet
{
    public static SimpleNetworkWrapper networkWrapper;

    public static void init()
    {
        int packetId = 0;
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("groundworldpacket");


        networkWrapper.registerMessage(SPacketClientOpenInventory.class, SPacketClientOpenInventory.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(CPacketQuestInfo.class, CPacketQuestInfo.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPhoneOpen.class, CPacketPhoneOpen.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPhoneGuiViewStatus.class, CPacketPhoneGuiViewStatus.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPhoneStatus.class, CPacketPhoneStatus.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketCallPlayer.class, CPacketCallPlayer.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketMailData.class, CPacketMailData.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketMailDataClear.class, CPacketMailDataClear.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketTImerStart.class, CPacketTImerStart.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketTimerSync.class, CPacketTimerSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketInfomation.class, CPacketInfomation.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketDiscordCallReicive.class, CPacketDiscordCallReicive.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketOilBankOpen.class, CPacketOilBankOpen.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketSubwaySummon.class, CPacketSubwaySummon.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketChangeToolActive.class, CPacketChangeToolActive.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketEleBankOpen.class, CPacketEleBankOpen.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketOpenShop.class, CPacketOpenShop.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketShopDataItemSync.class, CPacketShopDataItemSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketColdWaterSync.class, CPacketColdWaterSync.class, packetId++ ,Side.CLIENT);
        networkWrapper.registerMessage(CPacketShopDataSync.class, CPacketShopDataSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketOpenAutoMine.class, CPacketOpenAutoMine.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(AutoMineAreaPacket.Handler.class, AutoMineAreaPacket.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketBatterySync.class, CPacketBatterySync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketSubwaySummon2.class, CPacketSubwaySummon2.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketSubwayReset.class, CPacketSubwayReset.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CCinemaDirect.class, CCinemaDirect.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketFadeEffectAdd.class, CPacketFadeEffectAdd.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketClueSync.class, CPacketClueSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketSummonSubwayLocation.class, CPacketSummonSubwayLocation.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPlaySound.class, CPacketPlaySound.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketGallDataPacketSync.Handler.class, CPacketGallDataPacketSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketOpenMovie.class, CPacketOpenMovie.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketOpenPage.class, CPacketOpenPage.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketEleBankOpen.class, CPacketEleBankOpen.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketCardReaderPlay.class, CPacketCardReaderPlay.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketRobotSync.class, CPacketRobotSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketDayStart.class, CPacketDayStart.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketChangeNickName.class, CPacketChangeNickName.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketAmbientSound.class, CPacketAmbientSound.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketOpenSubWayUi.class, CPacketOpenSubWayUi.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketNpcDialog.class, CPacketNpcDialog.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketBombOn.class, CPacketBombOn.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketChargeDataPacketSync.Handler.class, CPacketChargeDataPacketSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketChargeDataClear.class, CPacketChargeDataClear.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketShopAmountChange.class, CPacketShopAmountChange.class, packetId++, Side.CLIENT);


        networkWrapper.registerMessage(SPacketMailDataSave.class, SPacketMailDataSave.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketSendMailDataSync.class, SPacketSendMailDataSync.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketUrgentRead.class, SPacketUrgentRead.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketUrgentItem.class, SPacketUrgentItem.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketMailDataGive.class, SPacketMailDataGive.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketDisCordCallRecive.class, SPacketDisCordCallRecive.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketWatchStatus.class, SPacketWatchStatus.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketDiscordCallRoomMove.class, SPacketDiscordCallRoomMove.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketNewWorkBench.class, SPacketNewWorkBench.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketItemAdd.class, SPacketItemAdd.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketItemRemove.class, SPacketItemRemove.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketShopDataItemRemove.class, SPacketShopDataItemRemove.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketShopDataItemRemove.class, SPacketShopDataItemRemove.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketDrillChange.class, SPacketDrillChange.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketChargeDataSync.class, SPacketChargeDataSync.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketItemRemoveOfLore.class, SPacketItemRemoveOfLore.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketDefaultShopDataReload.class, SPacketDefaultShopDataReload.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketAddItemAmount.class, SPacketAddItemAmount.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketAreaitemRemove.class, SPacketAreaitemRemove.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketBatterySync.class, SPacketBatterySync.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketPosActive.class, SPacketPosActive.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketCallNot.class, SPacketCallNot.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketCallRefuse.class, SPacketCallRefuse.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketMailSendSaveData.class, SPacketMailSendSaveData.class, packetId++, Side.SERVER);


    }



    @SubscribeEvent
    public void ReciveToPacket(FMLNetworkEvent.ClientCustomPacketEvent event)
    {

        /*
        Variable variable = Variable.Instance();
        FMLProxyPacket packet = event.getPacket();
        Minecraft minecraft = Minecraft.getMinecraft();
        if (event.getPacket().channel().equals("HalfMod"))
        {
            String packetData = new String(ByteBufUtil.getBytes(packet.payload()), Charset.forName("UTF-8"));
            String[] list = packetData.split("_");

            if(list[0].equalsIgnoreCase("Money"))
            {
                String test = list[1].replace("$", "");
                test = test.replace(",", "");
                //minecraft.player.sendChatMessage(test);
                float money = Float.parseFloat(test);
                money += 0f;
                Variable.Instance().setMoney(money);
            }
            else if(list[0].equalsIgnoreCase("View"))
            {
                String dialogName = list[1];
                int minDialogPage = Integer.parseInt(list[2]);
                int maxDialogPage = Integer.parseInt(list[3]);

                variable.setCurrentDialogPage(1);

                variable.setMaxDialogPage(maxDialogPage);
                variable.setMinDialogPage(minDialogPage);
                variable.setDialogName(dialogName);

                Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new Dialog()));
            }

            else if(list[0].equalsIgnoreCase("Btn"))
            {
                Boolean gameExitBtn = Boolean.parseBoolean(list[1]);

                //variable.setGameExitBtn(gameExitBtn.booleanValue());

            }


        }

         */
    }

}
