package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityShop6;
import com.doubleos.gw.entity.EntitySubWayNpc;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class EntitySubWayModel extends AnimatedTickingGeoModel<EntitySubWayNpc>
{

    @Override
    public ResourceLocation getModelLocation(EntitySubWayNpc entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/st_robo_1.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySubWayNpc entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/st_robo_1.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntitySubWayNpc entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/st_robo_1.animation.json");
    }
}
