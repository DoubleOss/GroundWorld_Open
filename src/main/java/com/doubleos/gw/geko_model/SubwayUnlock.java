package com.doubleos.gw.geko_model;

import com.doubleos.gw.block.tile.TileCardReader;
import com.doubleos.gw.block.tile.TileSubwayUnlock;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SubwayUnlock extends AnimatedGeoModel<TileSubwayUnlock>
{

    @Override
    public ResourceLocation getModelLocation(TileSubwayUnlock entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/subwayunlock.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TileSubwayUnlock entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/blocks/subwayunlock.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileSubwayUnlock entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/subwaymain.animation.json");
    }
}
