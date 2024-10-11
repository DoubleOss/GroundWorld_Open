package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine6;
import com.doubleos.gw.entity.EntityGeoAutoMine7;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel7 extends AnimatedTickingGeoModel<EntityGeoAutoMine7>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine7 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot7.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine7 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-7.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine7 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model7.animation.json");
    }
}
