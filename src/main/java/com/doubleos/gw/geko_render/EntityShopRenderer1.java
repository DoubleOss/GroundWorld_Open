package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine;
import com.doubleos.gw.entity.EntityShop1;
import com.doubleos.gw.geko_model.AutoMineModel;
import com.doubleos.gw.geko_model.EntityShopModel1;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityShopRenderer1 extends GeoEntityRenderer<EntityShop1>
{
    public EntityShopRenderer1(RenderManager renderManager) {
        super(renderManager, new EntityShopModel1());
    }
}
