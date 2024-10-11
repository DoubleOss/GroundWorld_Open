package com.doubleos.gw.geko_model;

import com.doubleos.gw.block.tile.EmergenTile;
import com.doubleos.gw.block.tile.TileCardReader;
import com.doubleos.gw.entity.EntityGeoAutoMine;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class CardTileModel extends AnimatedGeoModel<TileCardReader>
{

    @Override
    public ResourceLocation getModelLocation(TileCardReader entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/cardreader.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TileCardReader entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/blocks/cardreader.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TileCardReader entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/cardreader.animation.json");
    }
}
