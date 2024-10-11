package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityShop2;
import com.doubleos.gw.entity.EntityShop3;
import com.doubleos.gw.geko_model.EntityShopModel2;
import com.doubleos.gw.geko_model.EntityShopModel3;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityShopRenderer3 extends GeoEntityRenderer<EntityShop3>
{
    public EntityShopRenderer3(RenderManager renderManager) {
        super(renderManager, new EntityShopModel3());
    }
}
