package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityShop3;
import com.doubleos.gw.entity.EntityShop4;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class EntityShopModel4 extends AnimatedTickingGeoModel<EntityShop4>
{

    @Override
    public ResourceLocation getModelLocation(EntityShop4 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/st_robo_4.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShop4 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/st_robo_4.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShop4 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/st_robo_4.animation.json");
    }
}
