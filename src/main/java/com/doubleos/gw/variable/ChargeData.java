package com.doubleos.gw.variable;


import com.doubleos.gw.util.GallData;
import net.minecraft.util.math.BlockPos;

import java.io.*;

public class ChargeData implements Serializable {
    //charge_title_i
    //charge_area_i
    //charge_type_i //기름 or 전기
    //charge_amount_i
    //charge_infi_i // 무제한 여부


    String title = "";
    String area = "";

    String type = "";

    int amount = 0;

    boolean infinity = false;

    public BlockPos getPos() {
        return new BlockPos(posX, posY, posZ);
    }


    public int getChargeDataId() {
        return chargeDataId;
    }

    public void setChargeDataId(int chargeDataId) {
        this.chargeDataId = chargeDataId;
    }

    int chargeDataId = 0;
    //BlockPos pos = BlockPos.ORIGIN;

    public int posX = 0;
    public int posY = 0;
    public int posZ = 0;


    private static final long serialVersionUID = 245443543554l;

    public byte[] toBytes() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(this);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ChargeData fromBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (ChargeData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public ChargeData(int chargeDataId, String title, String area, String type, int amount, boolean infinity, BlockPos pos) {
        this.chargeDataId = chargeDataId;
        this.title = title;
        this.area = area;
        this.type = type;
        this.amount = amount;
        this.infinity = infinity;
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();

    }

    public ChargeData(int chargeDataId, String title, String area, String type, int amount, boolean infinity) {
        this.chargeDataId = chargeDataId;
        this.title = title;
        this.area = area;
        this.type = type;
        this.amount = amount;
        this.infinity = infinity;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isInfinity() {
        return infinity;
    }

    public void setInfinity(boolean infinity) {
        this.infinity = infinity;
    }

}
