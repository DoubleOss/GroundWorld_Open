package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine10;
import com.doubleos.gw.entity.EntityGeoAutoMine9;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel10 extends AnimatedTickingGeoModel<EntityGeoAutoMine10>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine10 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot10.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine10 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-10.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine10 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model10.animation.json");
    }
}
