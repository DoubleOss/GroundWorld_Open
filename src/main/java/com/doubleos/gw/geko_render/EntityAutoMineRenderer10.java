package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine10;
import com.doubleos.gw.entity.EntityGeoAutoMine9;
import com.doubleos.gw.geko_model.AutoMineModel10;
import com.doubleos.gw.geko_model.AutoMineModel9;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer10 extends GeoEntityRenderer<EntityGeoAutoMine10>
{
    public EntityAutoMineRenderer10(RenderManager renderManager) {
        super(renderManager, new AutoMineModel10());
    }
}
