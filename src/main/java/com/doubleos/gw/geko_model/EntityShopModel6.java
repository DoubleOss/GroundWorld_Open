package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityShop5;
import com.doubleos.gw.entity.EntityShop6;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class EntityShopModel6 extends AnimatedTickingGeoModel<EntityShop6>
{

    @Override
    public ResourceLocation getModelLocation(EntityShop6 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/st_robo_6.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShop6 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/st_robo_6.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShop6 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/st_robo_6.animation.json");
    }
}
