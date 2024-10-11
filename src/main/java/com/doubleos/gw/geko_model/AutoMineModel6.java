package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine5;
import com.doubleos.gw.entity.EntityGeoAutoMine6;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel6 extends AnimatedTickingGeoModel<EntityGeoAutoMine6>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine6 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot6.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine6 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-6.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine6 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model6.animation.json");
    }
}
