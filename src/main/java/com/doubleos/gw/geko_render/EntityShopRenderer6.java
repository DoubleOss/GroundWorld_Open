package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityShop5;
import com.doubleos.gw.entity.EntityShop6;
import com.doubleos.gw.geko_model.EntityShopModel5;
import com.doubleos.gw.geko_model.EntityShopModel6;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityShopRenderer6 extends GeoEntityRenderer<EntityShop6>
{
    public EntityShopRenderer6(RenderManager renderManager) {
        super(renderManager, new EntityShopModel6());
    }
}
