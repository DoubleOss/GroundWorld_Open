package com.doubleos.gw.geko_model;


import com.doubleos.gw.block.tile.EmergenTile;

import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EmergenBlockModel extends AnimatedGeoModel<EmergenTile>
{

    @Override
    public ResourceLocation getModelLocation(EmergenTile objItemBase) {
        return new ResourceLocation(Reference.MODID, "geo/table.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EmergenTile objItemBase) {
        return new ResourceLocation(Reference.MODID, "textures/blocks/table.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EmergenTile objItemBase) {
        return new ResourceLocation(Reference.MODID, "animations/table.animation.json");
    }

}
