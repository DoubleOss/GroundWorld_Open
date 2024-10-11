package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine8;
import com.doubleos.gw.entity.EntityGeoAutoMine9;
import com.doubleos.gw.geko_model.AutoMineModel8;
import com.doubleos.gw.geko_model.AutoMineModel9;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer9 extends GeoEntityRenderer<EntityGeoAutoMine9>
{
    public EntityAutoMineRenderer9(RenderManager renderManager) {
        super(renderManager, new AutoMineModel9());
    }
}
