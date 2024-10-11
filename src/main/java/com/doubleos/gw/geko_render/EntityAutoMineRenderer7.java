package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine6;
import com.doubleos.gw.entity.EntityGeoAutoMine7;
import com.doubleos.gw.geko_model.AutoMineModel6;
import com.doubleos.gw.geko_model.AutoMineModel7;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer7 extends GeoEntityRenderer<EntityGeoAutoMine7>
{
    public EntityAutoMineRenderer7(RenderManager renderManager) {
        super(renderManager, new AutoMineModel7());
    }
}
