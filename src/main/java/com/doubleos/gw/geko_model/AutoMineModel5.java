package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine4;
import com.doubleos.gw.entity.EntityGeoAutoMine5;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel5 extends AnimatedTickingGeoModel<EntityGeoAutoMine5>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine5 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot5.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine5 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-5.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine5 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/model5.animation.json");
    }
}
