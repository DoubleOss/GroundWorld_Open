package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine3;
import com.doubleos.gw.entity.EntityGeoAutoMine4;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel4 extends AnimatedTickingGeoModel<EntityGeoAutoMine4>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine4 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot4.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine4 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-4.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine4 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model4.animation.json");
    }
}
