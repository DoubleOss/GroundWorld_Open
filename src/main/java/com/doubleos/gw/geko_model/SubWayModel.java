package com.doubleos.gw.geko_model;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.entity.EntitySubWay;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.example.entity.BikeEntity;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SubWayModel extends AnimatedGeoModel<EntitySubWay> {
    @Override
    public ResourceLocation getAnimationFileLocation(EntitySubWay entity) {
        return new ResourceLocation(Reference.MODID, "animations/subway.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(EntitySubWay entity) {
        return new ResourceLocation(Reference.MODID, "geo/subway.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySubWay entity) {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/subway.png");
    }
}
