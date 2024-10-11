package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine8;
import com.doubleos.gw.entity.EntityGeoAutoMine9;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel9 extends AnimatedTickingGeoModel<EntityGeoAutoMine9>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine9 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot9.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine9 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-9.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine9 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model9.animation.json");
    }
}
