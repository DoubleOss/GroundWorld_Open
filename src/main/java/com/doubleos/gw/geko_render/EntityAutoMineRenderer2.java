package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine;
import com.doubleos.gw.entity.EntityGeoAutoMine2;
import com.doubleos.gw.geko_model.AutoMineModel;
import com.doubleos.gw.geko_model.AutoMineModel2;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer2 extends GeoEntityRenderer<EntityGeoAutoMine2>
{
    public EntityAutoMineRenderer2(RenderManager renderManager) {
        super(renderManager, new AutoMineModel2());
    }
}
