package com.doubleos.gw.geko_render;


import com.doubleos.gw.block.tile.BombTile;
import com.doubleos.gw.block.tile.EmergenTile;
import com.doubleos.gw.geko_model.BombTileModel;
import com.doubleos.gw.geko_model.EmergenBlockModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class BombRenderer extends GeoBlockRenderer<BombTile>
{

    public BombRenderer()
    {
        super(new BombTileModel());
    }


}
