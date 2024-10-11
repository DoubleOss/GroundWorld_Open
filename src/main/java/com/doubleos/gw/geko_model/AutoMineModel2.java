package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine;
import com.doubleos.gw.entity.EntityGeoAutoMine2;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel2 extends AnimatedTickingGeoModel<EntityGeoAutoMine2>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine2 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot2.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine2 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-2.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine2 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model2.animation.json");
    }
}
