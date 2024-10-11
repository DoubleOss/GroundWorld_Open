package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine3;
import com.doubleos.gw.entity.EntityGeoAutoMine4;
import com.doubleos.gw.geko_model.AutoMineModel3;
import com.doubleos.gw.geko_model.AutoMineModel4;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer4 extends GeoEntityRenderer<EntityGeoAutoMine4>
{
    public EntityAutoMineRenderer4(RenderManager renderManager) {
        super(renderManager, new AutoMineModel4());
    }
}
