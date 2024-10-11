package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityShop6;
import com.doubleos.gw.entity.EntitySubWayNpc;
import com.doubleos.gw.geko_model.EntityShopModel6;
import com.doubleos.gw.geko_model.EntitySubWayModel;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntitySubway extends GeoEntityRenderer<EntitySubWayNpc>
{
    public EntitySubway(RenderManager renderManager) {
        super(renderManager, new EntitySubWayModel());
    }
}
