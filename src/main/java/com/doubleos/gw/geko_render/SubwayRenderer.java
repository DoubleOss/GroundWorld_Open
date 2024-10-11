package com.doubleos.gw.geko_render;


import com.doubleos.gw.block.tile.TileCardReader;
import com.doubleos.gw.block.tile.TileSubwayUnlock;
import com.doubleos.gw.geko_model.CardTileModel;
import com.doubleos.gw.geko_model.SubwayUnlock;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class SubwayRenderer extends GeoBlockRenderer<TileSubwayUnlock>
{

    public SubwayRenderer()
    {
        super(new SubwayUnlock());
    }


}
