package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine2;
import com.doubleos.gw.entity.EntityGeoAutoMine3;
import com.doubleos.gw.geko_model.AutoMineModel2;
import com.doubleos.gw.geko_model.AutoMineModel3;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer3 extends GeoEntityRenderer<EntityGeoAutoMine3>
{
    public EntityAutoMineRenderer3(RenderManager renderManager) {
        super(renderManager, new AutoMineModel3());
    }
}
