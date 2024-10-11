package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityShop1;
import com.doubleos.gw.entity.EntityShop2;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class EntityShopModel2 extends AnimatedTickingGeoModel<EntityShop2>
{

    @Override
    public ResourceLocation getModelLocation(EntityShop2 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/st_robo_2.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShop2 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/st_robo_2.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShop2 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/st_robo_2.animation.json");
    }
}
