package com.doubleos.gw.geko_render;


import com.doubleos.gw.block.tile.EmergenTile;
import com.doubleos.gw.geko_model.EmergenBlockModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class EmergenRenderer extends GeoBlockRenderer<EmergenTile>
{

    public EmergenRenderer()
    {
        super(new EmergenBlockModel());
    }


}
