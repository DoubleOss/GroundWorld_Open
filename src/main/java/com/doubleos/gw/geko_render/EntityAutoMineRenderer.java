package com.doubleos.gw.geko_render;

import com.doubleos.gw.entity.EntityGeoAutoMine;
import com.doubleos.gw.geko_model.AutoMineModel;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.example.client.model.entity.ExampleEntityModel;
import software.bernie.example.entity.GeoExampleEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EntityAutoMineRenderer extends GeoEntityRenderer<EntityGeoAutoMine>
{
    public EntityAutoMineRenderer(RenderManager renderManager) {
        super(renderManager, new AutoMineModel());
    }
}
