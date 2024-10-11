package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityRobot;
import com.doubleos.gw.util.Reference;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ZombieToRobotModel extends AnimatedGeoModel<EntityRobot>
{
    @Override
    public ResourceLocation getModelLocation(EntityRobot object) {
        return new ResourceLocation(Reference.MODID, "geo/robomob.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityRobot object) {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/robomob.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityRobot animatable) {
        return new ResourceLocation(Reference.MODID, "animations/robomob.animation.json");
    }
}
