package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityShop3;
import com.doubleos.gw.entity.EntityShop4;
import com.doubleos.gw.geko_model.EntityShopModel3;
import com.doubleos.gw.geko_model.EntityShopModel4;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityShopRenderer4 extends GeoEntityRenderer<EntityShop4>
{
    public EntityShopRenderer4(RenderManager renderManager) {
        super(renderManager, new EntityShopModel4());
    }
}
