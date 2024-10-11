package com.doubleos.gw.geko_model;

import com.doubleos.gw.block.tile.BombTile;
import com.doubleos.gw.block.tile.TileCardReader;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BombTileModel extends AnimatedGeoModel<BombTile>
{

    @Override
    public ResourceLocation getModelLocation(BombTile entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/bomb.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BombTile entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/blocks/bomb.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BombTile entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/bomb.animation.json");
    }
}
