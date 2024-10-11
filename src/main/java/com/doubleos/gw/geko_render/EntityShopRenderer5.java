package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityShop4;
import com.doubleos.gw.entity.EntityShop5;
import com.doubleos.gw.geko_model.EntityShopModel4;
import com.doubleos.gw.geko_model.EntityShopModel5;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityShopRenderer5 extends GeoEntityRenderer<EntityShop5>
{
    public EntityShopRenderer5(RenderManager renderManager) {
        super(renderManager, new EntityShopModel5());
    }
}
