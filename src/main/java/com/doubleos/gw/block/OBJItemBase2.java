package com.doubleos.gw.block;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.blockInterface.IHasModel;
import com.doubleos.gw.blockInterface.IObjModel;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.variable.GroundItemStack;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class OBJItemBase2 extends ItemPickaxe implements IHasModel, IObjModel, IAnimatable
{
    public AnimationFactory factory = new AnimationFactory(this);

    public boolean m_powerOn = false;

    private String controllerName = "controller";
    public OBJItemBase2(String name, String koreaName)
    {
        super(ToolMaterial.DIAMOND);

        this.efficiency = 40;
        setUnlocalizedName(name);

        setRegistryName(name);
        setCreativeTab(GroundWorld.SdTab);

        ModItems.ITEMS.add(this);
        ModItems.itemKoreaName.add(koreaName);

    }

    @Override
    public void registerModels() {

    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }


    @Override
    public void registerControllers(AnimationData animationData) {

        AnimationController<OBJItemBase2> controller = new AnimationController<OBJItemBase2>(this,
                controllerName, 20, this::predicate);
        animationData.addAnimationController(controller);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        //event.getController().setAnimation(new AnimationBuilder().addAnimation("USE_LOOP"));
        return PlayState.CONTINUE;
    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            ItemStack stack = GroundItemStack.hashItemToItemStack.getOrDefault(this, new ItemStack(this));
            items.add(stack);

        }
    }

    public void offDrill(World worldIn, EntityPlayer player, EnumHand hand)
    {

        Variable.Instance().m_hashDrillPower.put(player.getName(), false);

        if (worldIn.isRemote)
        {
            if(player.getName().equals(Variable.Instance().clientPlayerName))
            {
                for(ItemStack invenStack : player.inventory.mainInventory)
                {
                    if(invenStack.getItem().equals(invenStack.getItem()))
                    {
                        AnimationController<?> controller = GeckoLibUtil.getControllerForStack(this.factory, invenStack, controllerName);
                        controller.setAnimation(new AnimationBuilder().addAnimation("USE_LOOP", false));
                        controller.clearAnimationCache();

                    }
                }
                GroundWorld.instance.m_shake = false;
            }

        }

        //Packet.networkWrapper.sendToServer(new SPacketDrillChange(player.getName()));

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
//        if (!worldIn.isRemote) {
//            return super.onItemRightClick(worldIn, player, hand);
//        }
        // Gets the item that the player is holding, should be a JackInTheBox

        Variable variable = Variable.Instance();

        boolean power = variable.m_hashDrillPower.getOrDefault(player.getName(), false);
        ItemStack stack = player.getHeldItem(hand);

        // Always use GeckoLibUtil to get animationcontrollers when you don't have
        // access to an AnimationEvent
        AnimationController<?> controller = GeckoLibUtil.getControllerForStack(this.factory, stack, controllerName);

        if (!power)
        {
            // If you don't do this, the popup animation will only play once because the
            // animation will be cached.
            if (worldIn.isRemote)
            {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("USE_LOOP", true));
                GroundWorld.instance.m_shake = true;
                player.sendMessage(new TextComponentString("드릴 전원 On"));

            }

            power = true;



        }
        else
        {
            power = false;
            if (worldIn.isRemote)
            {
                GroundWorld.instance.m_shake = false;
                controller.setAnimation(new AnimationBuilder().addAnimation("USE_LOOP", false));
                player.sendMessage(new TextComponentString("드릴 전원 Off"));

            }


        }
        variable.m_hashDrillPower.put(player.getName(), power);


        System.out.println(variable.m_hashDrillPower.get(player.getName()));
        return super.onItemRightClick(worldIn, player, hand);
    }

}
