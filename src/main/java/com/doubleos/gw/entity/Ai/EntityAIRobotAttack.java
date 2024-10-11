package com.doubleos.gw.entity.Ai;

import com.doubleos.gw.util.AnimationState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.monster.EntityZombie;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;

public class EntityAIRobotAttack extends EntityAIAttackMelee {
    private final EntityCreature attacker;
    private long lastAttackTime;


    public AnimationController controller;

    public EntityAIRobotAttack(EntityCreature creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
        this.attacker = creature;
        this.lastAttackTime = 0;
    }

    @Override
    public void updateTask() {
        EntityLivingBase target = this.attacker.getAttackTarget();

        if (target != null) {
            double distanceToTarget = this.attacker.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
            boolean canSeeTarget = this.attacker.getEntitySenses().canSee(target);

            if (canSeeTarget && distanceToTarget <= this.getAttackReachSqr(target) && this.isTimeToAttack())
            {
                this.attacker.swingArm(EnumHand.MAIN_HAND);
                this.attacker.attackEntityAsMob(target);
                this.attacker.getNavigator().clearPath(); // 공격 시 이동 멈춤
                this.lastAttackTime = System.currentTimeMillis();
            } else {
                // 공격 딜레이 동안 이동하지 않도록 함
                if (System.currentTimeMillis() - this.lastAttackTime < 1300) {
                    this.attacker.getNavigator().clearPath();
                }
            }
        }

        super.updateTask();
    }

    private boolean isTimeToAttack() {
        long currentTime = System.currentTimeMillis();
        return currentTime - this.lastAttackTime >= 1500; // 1.5초 딜레이
    }

    @Override
    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
        return (double) (this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width);
    }
}

