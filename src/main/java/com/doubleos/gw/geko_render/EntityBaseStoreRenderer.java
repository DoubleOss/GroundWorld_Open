package com.doubleos.gw.geko_render;

import com.doubleos.gw.block.OBJItemBase;
import com.doubleos.gw.entity.EntityBaseStore;
import com.doubleos.gw.geko_model.AutoMineModel7;
import com.doubleos.gw.geko_model.EntityBaseStoreModel;
import com.doubleos.gw.geko_model.jackModel;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class EntityBaseStoreRenderer extends GeoEntityRenderer<EntityBaseStore> {

    public EntityBaseStoreRenderer(RenderManager renderManager) {
        super(renderManager, new EntityBaseStoreModel());
    }

}
