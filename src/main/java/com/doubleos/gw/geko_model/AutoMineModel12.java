package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine11;
import com.doubleos.gw.entity.EntityGeoAutoMine12;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel12 extends AnimatedTickingGeoModel<EntityGeoAutoMine12>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine12 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot12.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine12 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-12.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine12 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model12.animation.json");
    }
}
