package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.example.entity.GeoExampleEntity;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AutoMineModel extends AnimatedTickingGeoModel<EntityGeoAutoMine>
{

    @Override
    public ResourceLocation getModelLocation(EntityGeoAutoMine entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/robot1.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityGeoAutoMine entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/ye-1.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityGeoAutoMine entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/automine.animation.json");
    }
}
