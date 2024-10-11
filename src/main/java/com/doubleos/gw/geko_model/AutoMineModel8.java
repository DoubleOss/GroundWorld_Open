package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine6;
import com.doubleos.gw.entity.EntityGeoAutoMine8;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel8 extends AnimatedTickingGeoModel<EntityGeoAutoMine8>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine8 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot8.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine8 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-8.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine8 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model8.animation.json");
    }
}
