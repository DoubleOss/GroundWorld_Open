package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityGeoAutoMine12;
import com.doubleos.gw.entity.EntityShop1;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class EntityShopModel1 extends AnimatedTickingGeoModel<EntityShop1>
{

    @Override
    public ResourceLocation getModelLocation(EntityShop1 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/st_robo_1.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShop1 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/st_robo_1.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShop1 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/st_robo_1.animation.json");
    }
}
