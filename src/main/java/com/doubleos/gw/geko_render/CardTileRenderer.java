package com.doubleos.gw.geko_render;


import com.doubleos.gw.block.tile.EmergenTile;
import com.doubleos.gw.block.tile.TileCardReader;
import com.doubleos.gw.geko_model.CardTileModel;
import com.doubleos.gw.geko_model.EmergenBlockModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CardTileRenderer extends GeoBlockRenderer<TileCardReader>
{

    public CardTileRenderer()
    {
        super(new CardTileModel());
    }


}
