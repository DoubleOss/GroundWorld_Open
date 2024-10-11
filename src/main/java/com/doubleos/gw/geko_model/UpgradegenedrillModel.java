package com.doubleos.gw.geko_model;

import com.doubleos.gw.block.OBJItemBase2;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UpgradegenedrillModel extends AnimatedGeoModel<OBJItemBase2>
{

    @Override
    public ResourceLocation getModelLocation(OBJItemBase2 objItemBase) {
        return new ResourceLocation(Reference.MODID, "geo/upgradegenedrill.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(OBJItemBase2 objItemBase) {
        return new ResourceLocation(Reference.MODID, "textures/item/upgradegenedrill.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(OBJItemBase2 objItemBase) {
        return new ResourceLocation(Reference.MODID, "animations/upgradegenedrill.animation.json");
    }
}
