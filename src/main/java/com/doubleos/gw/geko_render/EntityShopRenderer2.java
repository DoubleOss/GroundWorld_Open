package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityShop1;
import com.doubleos.gw.entity.EntityShop2;
import com.doubleos.gw.geko_model.EntityShopModel1;
import com.doubleos.gw.geko_model.EntityShopModel2;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityShopRenderer2 extends GeoEntityRenderer<EntityShop2>
{
    public EntityShopRenderer2(RenderManager renderManager) {
        super(renderManager, new EntityShopModel2());
    }
}
