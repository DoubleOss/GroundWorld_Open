package com.doubleos.gw.block;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.block.tile.BlockNamsanTileEntity;
import com.doubleos.gw.blockInterface.IHasModel;
import com.doubleos.gw.blockInterface.IObjModel;
import com.doubleos.gw.init.ModBlocks;
import com.doubleos.gw.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class NamSanTileBlock extends Block implements IHasModel, IObjModel, ITileEntityProvider
{

    public NamSanTileBlock(String name, Material blockMaterialIn, boolean unbreaking)
    {
        super(blockMaterialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(GroundWorld.SdTab);
        setLightOpacity(0);
        setLightLevel(1f);



        if (unbreaking)
            setBlockUnbreakable();

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {

    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }
    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new BlockNamsanTileEntity();
    }
}
