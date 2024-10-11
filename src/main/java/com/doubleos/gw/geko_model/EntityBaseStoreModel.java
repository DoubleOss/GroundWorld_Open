package com.doubleos.gw.geko_model;

import com.doubleos.gw.entity.EntityBaseStore;
import com.doubleos.gw.entity.EntityGeoAutoMine12;
import com.doubleos.gw.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class EntityBaseStoreModel extends AnimatedTickingGeoModel<EntityBaseStore>
{

    @Override
    public ResourceLocation getModelLocation(EntityBaseStore entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "geo/basestore.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityBaseStore entityGeoAutoMine)
    {
        return new ResourceLocation(Reference.MODID, "textures/item/basestore.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityBaseStore entityGeoAutoMine) {
        return new ResourceLocation(Reference.MODID, "animations/basestore.animation.json");
    }
}
