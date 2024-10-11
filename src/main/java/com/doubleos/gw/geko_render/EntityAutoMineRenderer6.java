package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine5;
import com.doubleos.gw.entity.EntityGeoAutoMine6;
import com.doubleos.gw.geko_model.AutoMineModel5;
import com.doubleos.gw.geko_model.AutoMineModel6;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer6 extends GeoEntityRenderer<EntityGeoAutoMine6>
{
    public EntityAutoMineRenderer6(RenderManager renderManager) {
        super(renderManager, new AutoMineModel6());
    }
}
