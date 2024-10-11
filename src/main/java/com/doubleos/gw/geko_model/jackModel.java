package com.doubleos.gw.geko_model;

import com.doubleos.gw.block.OBJItemBase;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class jackModel  extends AnimatedGeoModel<OBJItemBase>
{
    @Override
    public ResourceLocation getModelLocation(OBJItemBase objItemBase) {
        return new ResourceLocation(Reference.MODID, "geo/jack.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(OBJItemBase objItemBase) {
        return new ResourceLocation(Reference.MODID, "textures/item/jack.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(OBJItemBase objItemBase) {
        return new ResourceLocation(Reference.MODID, "animations/jackinthebox.animation.json");
    }
}
