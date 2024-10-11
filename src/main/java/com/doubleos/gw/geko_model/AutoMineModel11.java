package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine10;
import com.doubleos.gw.entity.EntityGeoAutoMine11;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel11 extends AnimatedTickingGeoModel<EntityGeoAutoMine11>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine11 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot11.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine11 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-11.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine11 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model11.animation.json");
    }
}
