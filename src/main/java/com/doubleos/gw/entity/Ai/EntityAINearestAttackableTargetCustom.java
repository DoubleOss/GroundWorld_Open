package com.doubleos.gw.entity.Ai;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;

public class EntityAINearestAttackableTargetCustom<T extends EntityLivingBase> extends EntityAITarget {
    protected final Class<T> targetClass;
    private final int targetChance;
    protected final Sorter sorter;
    protected final Predicate<? super T> targetEntitySelector;
    protected T targetEntity;

    public EntityAINearestAttackableTargetCustom(EntityCreature creature, Class<T> classTarget, boolean checkSight) {
        this(creature, classTarget, checkSight, false);
    }

    public EntityAINearestAttackableTargetCustom(EntityCreature creature, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
        this(creature, classTarget, 10, checkSight, onlyNearby, null);
    }

    public EntityAINearestAttackableTargetCustom(EntityCreature creature, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate<? super T> targetSelector) {
        super(creature, checkSight, onlyNearby);
        this.targetClass = classTarget;
        this.targetChance = chance;
        this.sorter = new Sorter(creature);
        this.setMutexBits(1);
        this.targetEntitySelector = new Predicate<T>() {
            public boolean apply(@Nullable T entity) {
                if (entity == null) {
                    return false;
                } else if (targetSelector != null && !targetSelector.apply(entity)) {
                    return false;
                } else {
                    if (entity instanceof EntityPlayer) {
                        ItemStack helmet = ((EntityPlayer) entity).getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                        if (helmet != null && helmet.getItem() == Items.DIAMOND_HELMET) {
                            return false; // 다이아몬드 헬멧을 착용한 경우 타겟팅에서 제외
                        }
                    }
                    return !EntitySelectors.NOT_SPECTATING.apply(entity) && EntityAINearestAttackableTargetCustom.this.isSuitableTarget(entity, false);
                }
            }
        };
    }

    public boolean shouldExecute() {
        System.out.println("shouldExecute called");
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            System.out.println("Chance condition not met");
            return false;
        } else if (this.targetClass != EntityPlayer.class && this.targetClass != EntityPlayerMP.class) {
            List<T> list = this.taskOwner.world.<T>getEntitiesWithinAABB(this.targetClass, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);

            if (list.isEmpty()) {
                System.out.println("No targets found");
                return false;
            } else {
                Collections.sort(list, this.sorter);
                this.targetEntity = list.get(0);
                System.out.println("Target acquired: " + this.targetEntity.getName());
                return true;
            }
        } else {
            this.targetEntity = (T) this.taskOwner.world.getNearestAttackablePlayer(this.taskOwner.posX, this.taskOwner.posY + (double) this.taskOwner.getEyeHeight(), this.taskOwner.posZ, this.getTargetDistance(), this.getTargetDistance(), new Function<EntityPlayer, Double>() {
                @Nullable
                public Double apply(@Nullable EntityPlayer player) {
                    ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                    return 1.0D;
                }
            }, (Predicate<EntityPlayer>) this.targetEntitySelector);
            if (this.targetEntity != null) {
                System.out.println("Target acquired: " + this.targetEntity.getName());
            } else {
                System.out.println("No valid target found");
            }
            return this.targetEntity != null;
        }
    }

    public boolean shouldContinueExecuting() {
        if (this.targetEntity != null && this.targetEntity instanceof EntityPlayer) {
            ItemStack helmet = ((EntityPlayer) this.targetEntity).getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            if (helmet != null && helmet.getItem() == Items.DIAMOND_HELMET) {
                System.out.println("Target lost: " + this.targetEntity.getName() + " due to diamond helmet");
                return false; // 타겟이 다이아몬드 헬멧을 착용한 경우 계속해서 타겟으로 설정되지 않도록 함
            }
        }
        return super.shouldContinueExecuting();
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.taskOwner.getEntityBoundingBox().grow(targetDistance, 4.0D, targetDistance);
    }

    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        System.out.println("startExecuting called: " + this.targetEntity.getName());
        super.startExecuting();
    }
    public static class Sorter implements Comparator<Entity> {
        private final Entity entity;

        public Sorter(Entity entityIn) {
            this.entity = entityIn;
        }

        public int compare(Entity p_compare_1_, Entity p_compare_2_) {
            double d0 = this.entity.getDistanceSq(p_compare_1_);
            double d1 = this.entity.getDistanceSq(p_compare_2_);

            if (d0 < d1) {
                return -1;
            } else {
                return d0 > d1 ? 1 : 0;
            }
        }
    }
}
