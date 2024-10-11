package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityShop4;
import com.doubleos.gw.entity.EntityShop5;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class EntityShopModel5 extends AnimatedTickingGeoModel<EntityShop5>
{

    @Override
    public ResourceLocation getModelLocation(EntityShop5 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/st_robo_5.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShop5 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/st_robo_5.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShop5 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/st_robo_5.animation.json");
    }
}
