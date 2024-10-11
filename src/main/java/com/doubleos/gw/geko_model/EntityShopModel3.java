package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityShop2;
import com.doubleos.gw.entity.EntityShop3;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class EntityShopModel3 extends AnimatedTickingGeoModel<EntityShop3>
{

    @Override
    public ResourceLocation getModelLocation(EntityShop3 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/st_robo_3.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityShop3 entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/model/entity/st_robo_3.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityShop3 entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/st_robo_3.animation.json");
    }
}
