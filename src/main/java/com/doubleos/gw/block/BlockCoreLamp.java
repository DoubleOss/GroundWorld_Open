package com.doubleos.gw.block;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.blockInterface.IHasModel;
import com.doubleos.gw.init.ModBlocks;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.variable.GroundItemStack;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCoreLamp extends BlockHorizontal implements IHasModel
{

    public BlockCoreLamp(String name, Material materialIn, boolean light, float lightValue)
    {
        super(materialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(GroundWorld.SdTab);
        setHardness(4f);
        if (light)
        {
            setLightLevel(1f);
        }


        setDefaultState(this.getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
        ModBlocks.BLOCKS.add(this);

        Item blockItem = new ItemBlock(this).setRegistryName(this.getRegistryName());
        blockItem.setMaxStackSize(1);

        ModItems.ITEMS.add(blockItem);

        //ModItems.ITEMS.add( new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        return state.withProperty(FACING, placer.getHorizontalFacing());
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public void registerModels()
    {
        GroundWorld.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "variants");
    }


    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {

        ItemStack stack = GroundItemStack.hashItemToItemStack.getOrDefault(ItemBlock.getItemFromBlock( this), new ItemStack(this));
        items.add(stack);

    }
}
