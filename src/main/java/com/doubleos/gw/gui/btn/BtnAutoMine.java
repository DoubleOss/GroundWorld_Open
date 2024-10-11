package com.doubleos.gw.gui.btn;

import com.doubleos.gw.GroundWorld;
import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.util.GallData;
import com.doubleos.gw.util.MailData;
import com.doubleos.gw.util.Render;
import com.doubleos.gw.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class BtnAutoMine extends GuiButton
{


    public String m_BtnName = "";
    Minecraft minecraft = Minecraft.getMinecraft();

    Variable variable = Variable.Instance();

    int m_btn_Width = 600 / 2;
    int m_btn_Height = 51 / 2;

    float m_btn_ImageWidth = 600 / 2f;
    float m_btn_ImageHeight = 51 / 2f;

    ArrayList<AutoMineArea> area = new ArrayList<>();

    public boolean m_giveActive = false;


    int textureId = 0;

    public BtnAutoMine(int buttonId, int x, int y, String buttonText)
    {
        super(buttonId, x, y, buttonText);
    }

    public BtnAutoMine(int buttonId, int x, int y, String buttonText, String memberName, MailData data)
    {
        super(buttonId, x, y, buttonText);

        m_BtnName = memberName;

        m_btn_ImageWidth = 68/3f;
        m_btn_ImageHeight = 68/3f;

        int m_btn_Width = (int) ((int) 493f/3f);
        int m_btn_Height = (int) ((int) 73f/3f);

        if(memberName.equals("뒤로가기"))
        {
            m_btn_Width = (int) ((int) 66f/3f);
            m_btn_Height = (int) ((int) 18f/3f);
        }
        else if(memberName.equals("슬라이더"))
        {
            float back_Width = 12/3f;
            float back_Height = 192f/3f;

            m_btn_Width = (int) back_Width;
            m_btn_Height = (int) back_Height;
        }


        this.width = m_btn_Width;
        this.height = m_btn_Height;


    }
    public BtnAutoMine(int buttonId, int x, int y, String buttonText, String memberName, ArrayList<AutoMineArea> array)
    {
        super(buttonId, x, y, buttonText);

        m_BtnName = memberName;

        m_btn_ImageWidth = 68/3f;
        m_btn_ImageHeight = 68/3f;

        int m_btn_Width = (int) ((int) 493f/3f);
        int m_btn_Height = (int) ((int) 73f/3f);

        if(memberName.equals("뒤로가기"))
        {
            m_btn_Width = (int) ((int) 66f/3f);
            m_btn_Height = (int) ((int) 18f/3f);
        }
        else if(memberName.equals("슬라이더"))
        {
            float back_Width = 12/3f;
            float back_Height = 192f/3f;

            m_btn_Width = (int) back_Width;
            m_btn_Height = (int) back_Height;
        }

        this.area = array;

        this.width = m_btn_Width;
        this.height = m_btn_Height;
    }


    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {


        String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";

        if(m_BtnName.equals("뒤로가기"))
        {
            if(active.equals("_active"))
            {
                float back_Width = 66f/3f;
                float back_Height = 16f/3f;
                GlStateManager.pushMatrix();
                {
                    GlStateManager.scale(1.05f, 1.05f, 1.05f);
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\btn\\뒤로가기.png"));
                    Render.drawTexture(this.x/1.05f-0.25f, this.y/1.05f-0.25f, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
                }
                GlStateManager.popMatrix();

            }
            else
            {
                float back_Width = 66f/3f;
                float back_Height = 16f/3f;
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\gall\\btn\\뒤로가기.png"));
                Render.drawTexture(this.x, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
            }

        }
        else if (m_BtnName.equals("슬라이더"))
        {
            float back_Width = 6/3f;
            float back_Height = 192f/3f;

            if(active.equals("_active"))
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\scroll_bar.png"));
                Render.drawTexture(this.x+1, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1, 0.5f, 0.5f, 0.5f);
            }
            else
            {
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\content\\scroll_bar.png"));
                Render.drawTexture(this.x+1, this.y, back_Width, back_Height, 0, 0, 1, 1, 3, 1);
            }

        }
        else
        {
            boolean nonActive = true;

            int btnId = 0;
            if(area.size() == 0)
            {
                nonActive = true;

            }
            else
            {
                btnId = Integer.parseInt(m_BtnName);
                //System.out.println(btnId + "  수정값  " +  (btnId-1) + "  " + nonActive);
                if(area.size()-1 >= (btnId-1))
                    nonActive = false;
                else
                    nonActive = true;
            }


            float icon_Width = 28f / 3f;
            float icon_Height = 40f / 3f;

            float squareStr_Width = 59f / 3f;
            float squareStr_Height = 59f / 3f;


            String name = m_BtnName + "번 부족";

            if(nonActive)
            {

                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\기본값.png"));
                Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 1, 1);
                mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\아이콘.png"));
                Render.drawTexture(this.x + 5, this.y  + 2.5f, icon_Width, icon_Height, 0, 0, 1, 1, 1, 1);


            }
            else
            {
                AutoMineArea mineArea = area.get(btnId-1);

                float waterPer = (float) mineArea.m_currentOil / mineArea.m_maxCurrentOil * 100f;
                float hungerPer = (float) mineArea.m_currentDurability / mineArea.m_maxDurability * 100f;



                if(active.equals("_active"))
                {

                    float background_Width = 301f/3f;
                    float background_Height = 90f/3f;

                    float waterBackground_Width = 253f/3f;
                    float waterBackground_Height = 23f/3f;

                    float water_Width = 249f/3f;
                    float water_Height = 19f/3f;

                    float circleHungger = 19f/3f;

                    if(hungerPer >= 30)
                    {
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\"+m_BtnName+"번 배경.png"));
                        Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 1, 1, 0.75f, 0.75f, 0.75f);
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\부족 아이콘.png"));
                        Render.drawTexture(this.x + 5, this.y + 2.5f, icon_Width, icon_Height, 0, 0, 1, 1, 1, 1, 0.75f, 0.75f, 0.75f);
                    }
                    else
                    {
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\에너지부족.png"));
                        Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 1, 1, 0.75f, 0.75f, 0.75f);
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\부족 아이콘.png"));
                        Render.drawTexture(this.x + 5, this.y + 2.5f, icon_Width, icon_Height, 0, 0, 1, 1, 1, 1, 0.75f, 0.75f, 0.75f);
                    }

                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\stat\\배경.png"));
                    Render.drawTexture(this.x - background_Width - 25, this.y - background_Height/2f + 10f, background_Width, background_Height, 0, 0, 1, 1, 1, 1);

                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\stat\\허기 백그라운드.png"));
                    Render.drawTexture(this.x - waterBackground_Width - 37, this.y - waterBackground_Height/2f + 4f, waterBackground_Width, waterBackground_Height, 0, 0, 1, 1, 1, 1);

                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\stat\\수분 백그라운드.png"));
                    Render.drawTexture(this.x - waterBackground_Width - 37, this.y - waterBackground_Height/2f + 15f, waterBackground_Width, waterBackground_Height, 0, 0, 1, 1, 1, 1);


                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\stat\\허기바.png")); // 내구도
                    Render.drawXLinearTexture(this.x - water_Width - 37.5f, this.y - water_Height/2f + 4f, water_Width, water_Height, hungerPer, 1, 2);

                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\stat\\수분바.png")); /// 기름
                    Render.drawXLinearTexture(this.x - water_Width - 37.5f, this.y - water_Height/2f + 15f, water_Width, water_Height, waterPer, 1, 2);


                    if (hungerPer > 0 && hungerPer <= 97)
                    {
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\stat\\허기 동그라미.png"));
                        Render.drawTexture(this.x - water_Width - 37.5f + water_Width * (hungerPer * 0.01f) - circleHungger/2f, this.y - circleHungger/2f + 4f, circleHungger, circleHungger, 0, 0, 1, 1, 2, 1);

                    }
                    if(waterPer > 0 && waterPer <= 97)
                    {

                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\stat\\수분 동그라미.png"));
                        Render.drawTexture(this.x - water_Width - 37.5f + water_Width * (waterPer * 0.01f) - circleHungger/2f, this.y - circleHungger/2f + 15f, circleHungger, circleHungger, 0, 0, 1, 1, 2, 1);

                    }


                    float durabilityStrWidth = 62f/3f;
                    float durabilityStrHeight = 19f/3f;

                    float oilStrWidth = 46f/3f;
                    float OilStrHeight = 20f/3f;


                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\stat\\허기.png"));
                    Render.drawTexture(this.x - durabilityStrWidth - 98f, this.y - durabilityStrHeight/2f + 4f, durabilityStrWidth, durabilityStrHeight, 0, 0, 1, 1, 2, 1);
                    mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\stat\\수분.png"));
                    Render.drawTexture(this.x - oilStrWidth - 102.5f, this.y - OilStrHeight/2f + 15.25f, oilStrWidth, OilStrHeight, 0, 0, 1, 1, 2, 1);



                }
                else
                {

                    if(hungerPer >= 30)
                    {
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\"+m_BtnName+"번 배경.png"));
                        Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 1, 1, 1f, 1f, 1f);
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\부족 아이콘.png"));
                        Render.drawTexture(this.x + 5, this.y + 2.5f, icon_Width, icon_Height, 0, 0, 1, 1, 1, 1, 1f, 1f, 1f);
                    }
                    else
                    {
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\에너지부족.png"));
                        Render.drawTexture(this.x, this.y, squareStr_Width, squareStr_Height, 0, 0, 1, 1, 1, 1, 1f, 1f, 1f);
                        mc.renderEngine.bindTexture(new ResourceLocation(GroundWorld.RESOURCEID, "textures\\gui\\phone\\automine\\btn\\부족 아이콘.png"));
                        Render.drawTexture(this.x + 5, this.y + 2.5f, icon_Width, icon_Height, 0, 0, 1, 1, 1, 1, 1f, 1f, 1f);
                    }
                }
            }

            Render.drawStringScaleResizeByLeftWidth(name, this.x + 27, this.y + 7f, 100, 0.85f, 1);
            BlockPos loc = BlockPos.ORIGIN;
            String pos = "";

            if(nonActive)
                pos = "부족을 찾을 수 없습니다.";
            else
            {
                AutoMineArea mineArea = area.get(btnId-1);


                loc = new BlockPos(mineArea.m_areaStartXPos,mineArea.m_areaStartYPos, mineArea.m_areaStartZPos);
                pos = "(x " + loc.getX() + ", y " + loc.getY() + ", z " + loc.getZ()+")";
            }

            Render.drawStringScaleResizeByLeftWidth(pos, this.x + 95, this.y + 8f, 100, 0.6f, 1);



        }

        //super.drawButton(mc, mouseX, mouseY, partialTicks);

    }


    public void drawStringScaleResizeByLeftWidth(String text, float x, float y, float depth, float scale, int color)
    {

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(x, y, depth);
            minecraft.fontRenderer.drawString(text, (x +stringSize)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }

}
