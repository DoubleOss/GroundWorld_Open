package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine10;
import com.doubleos.gw.entity.EntityGeoAutoMine11;
import com.doubleos.gw.geko_model.AutoMineModel10;
import com.doubleos.gw.geko_model.AutoMineModel11;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer11 extends GeoEntityRenderer<EntityGeoAutoMine11>
{
    public EntityAutoMineRenderer11(RenderManager renderManager) {
        super(renderManager, new AutoMineModel11());
    }
}
