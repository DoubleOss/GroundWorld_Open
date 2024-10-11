package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntitySubWay;
import com.doubleos.gw.entity.EntitySubWay2;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SubWayModel2 extends AnimatedGeoModel<EntitySubWay2> {
    @Override
    public ResourceLocation getAnimationFileLocation(EntitySubWay2 entity) {
        return new ResourceLocation(Reference.MODID, "animations/subway.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(EntitySubWay2 entity) {
        return new ResourceLocation(Reference.MODID, "geo/subway.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySubWay2 entity) {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/subway.png");
    }
}
