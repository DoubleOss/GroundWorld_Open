package com.doubleos.gw.block;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.block.tile.BombTile;
import com.doubleos.gw.block.tile.EmergenTile;
import com.doubleos.gw.blockInterface.IHasModel;
import com.doubleos.gw.blockInterface.IObjModel;
import com.doubleos.gw.init.ModBlocks;
import com.doubleos.gw.init.ModItems;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import javax.annotation.Nullable;

public class BombBlock extends BlockDirectional implements IHasModel, IObjModel, ITileEntityProvider
{


    public EmergenTile m_tileEntity;

    private String controllerName = "controller";

    public BombBlock(String name, Material blockMaterialIn, boolean unbreaking)
    {
        super(blockMaterialIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(GroundWorld.SdTab);
        //this.setResistance(6000.0F);
        this.setHardness(100.5F);


        if (unbreaking)
            setBlockUnbreakable();

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }


    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        //event.getController().setAnimation(new AnimationBuilder().addAnimation("USE_LOOP"));
        return PlayState.CONTINUE;
    }



    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(FACING)).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }



    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING,
                EnumFacing.getDirectionFromEntityLiving(pos, placer).getOpposite());
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void registerModels() {

    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn) {
        return false;
    }



    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new BombTile();
    }

    @Override
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }
}
