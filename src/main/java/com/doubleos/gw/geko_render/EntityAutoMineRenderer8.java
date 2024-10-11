package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine7;
import com.doubleos.gw.entity.EntityGeoAutoMine8;
import com.doubleos.gw.geko_model.AutoMineModel7;
import com.doubleos.gw.geko_model.AutoMineModel8;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer8 extends GeoEntityRenderer<EntityGeoAutoMine8>
{
    public EntityAutoMineRenderer8(RenderManager renderManager) {
        super(renderManager, new AutoMineModel8());
    }
}
