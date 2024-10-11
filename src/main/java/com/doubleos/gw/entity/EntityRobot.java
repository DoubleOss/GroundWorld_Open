package com.doubleos.gw.entity;

import com.doubleos.gw.entity.Ai.EntityAINearestAttackableTargetCustom;
import com.doubleos.gw.entity.Ai.EntityAIRobotAttack;
import com.doubleos.gw.handler.GwSoundHandler;
import com.doubleos.gw.init.ModItems;
import com.doubleos.gw.packet.CPacketPhoneOpen;
import com.doubleos.gw.packet.CPacketRobotSync;
import com.doubleos.gw.packet.Packet;
import com.doubleos.gw.util.InventoryUtils;
import com.doubleos.gw.variable.GroundItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;

import javax.annotation.Nullable;
import java.util.Timer;
import java.util.TimerTask;

public class EntityRobot extends EntityVillager implements IAnimatable, IAnimationTickable {


    private int randomTickDivider;
    public boolean is_Attack = false;
    public boolean is_Walking = false;

    public boolean is_Death = false;

    private long lastAttackTime;

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("감시 로봇");
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }


    @Override
    public String getCustomNameTag() {
        return "감시 로봇";
    }

    @Override
    public String getName() {
        return "감시 로봇";
    }

    public EntityRobot(World worldIn) {
        super(worldIn);
        this.setSize(1f, 1.95F);
        this.lastAttackTime = 0;
    }

    @Override
    protected void updateAITasks()
    {

    }

    AnimationFactory factory = new AnimationFactory(this);

    @Override
    public void registerControllers(AnimationData data)
    {

        data.addAnimationController(
                new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {

        if (isDeath())
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
        }
        else if (isAttacking())
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", true));
        }
        else
        {
            if ((event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
            }
            else
            {
                if(event.getController().getCurrentAnimation() != null)
                {
                    if(event.getController().getCurrentAnimation().animationName.equals("idle"))
                        event.getController().clearAnimationCache();
                    //System.out.println(event.getController().getCurrentAnimation().animationName);
                }

                event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            }
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    EntityAIRobotAttack aiRobot;
    EntityAIMoveTowardsRestriction aiMove;
    EntityAIWatchClosest aiClosest;
    EntityAILookIdle aiLook;

    EntityAIHurtByTarget aiTargetHurt;

    EntityAINearestAttackableTarget<EntityPlayer> aiTargetAttack;



    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {

    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles(EnumParticleTypes particleType)
    {
    }
    @Override
    protected void initEntityAI()
    {
        aiRobot = new EntityAIRobotAttack(this, 1.0D, true);
        aiMove = new EntityAIMoveTowardsRestriction(this, 1.0D);
        aiClosest = new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F);
        aiLook =  new EntityAILookIdle(this);

        aiTargetHurt =  new EntityAIHurtByTarget(this, true);
        aiTargetAttack = new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true);



        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, aiRobot);
        this.tasks.addTask(2, aiMove);
        this.tasks.addTask(3, aiClosest);
        this.tasks.addTask(3,aiLook);

        this.targetTasks.addTask(1, aiTargetHurt);
        this.targetTasks.addTask(2, aiTargetAttack);
    }

    private boolean isWithinAttackRange(Entity entityIn) {
        double distance = this.getDistanceSq(entityIn);
        double reach = 3.5D; // 기본 공격 범위를 설정합니다 (4 블록 예시)
        return distance <= reach * reach;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.38D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    }


    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = this.isWithinAttackRange(entityIn);
        if(! isAttacking())
        {
            if (flag)
            {
                if(! isDeath())
                {
                    setAttacking(true);
                    java.util.Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run()
                        {
                            entityIn.attackEntityFrom(DamageSource.causeMobDamage(EntityRobot.this), (float) 20f);
                        }
                    }, 200l);

                    this.swingArm(EnumHand.MAIN_HAND);
                    this.lastAttackTime = System.currentTimeMillis();
                }

            }
        }

        return flag;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!world.isRemote)
        {
            long currentTime = System.currentTimeMillis();

            if (this.isAttacking() && currentTime - this.lastAttackTime > 1200) {
                this.setAttacking(false);
            }
            else if(this.is_Death && currentTime - this.lastAttackTime > 3000)
            {
                setDeath(false);
            }
        }

    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(! isDeath())
        {
            if(this.getHealth()-amount <= 0)
            {
                if (!this.dead && ! isDeath())
                {
                    this.setHealth(1.0F);
                    this.cause = source;
                    setDeath(true);
                    this.lastAttackTime = System.currentTimeMillis();

                }
                return false;
            }
            else if (source.getImmediateSource() instanceof EntityPlayerMP)
            {
                EntityPlayerMP playerMP = (EntityPlayerMP) source.getImmediateSource();
                if(playerMP.getHeldItemMainhand().getItem().equals(ModItems.hammer))
                {
                    if(this.getHealth()-25f <= 0)
                    {
                        if (!this.dead && ! isDeath())
                        {
                            InventoryUtils.decreaseItemStack(playerMP, playerMP.getHeldItemMainhand().getItem(), 1);
                            this.setHealth(1.0F);
                            this.cause = source;
                            setDeath(true);
                            this.lastAttackTime = System.currentTimeMillis();

                        }
                        return false;
                    }
                    else
                    {
                        InventoryUtils.decreaseItemStack(playerMP, playerMP.getHeldItemMainhand().getItem(), 1);
                        return super.attackEntityFrom(source, 25F);
                    }


                }
                else
                {
                    return super.attackEntityFrom(source, amount);
                }
            }
            else
                return super.attackEntityFrom(source, amount);
        }
        else
        {
            return false;
        }

    }

    DamageSource cause;

    @Override
    public void onDeath(DamageSource cause)
    {

    }

    public boolean isDeath() {
        return is_Death;
    }
    public boolean isAttacking() {
        return is_Attack;
    }


    public void setDeath(boolean death)
    {
        is_Death = death;
        if (!world.isRemote)
        {
            if (death)
            {
                this.tasks.removeTask(aiRobot);
                this.tasks.removeTask(aiMove);
                this.tasks.removeTask(aiClosest);
                this.tasks.removeTask(aiLook);
                this.tasks.removeTask(aiTargetHurt);

                this.targetTasks.removeTask(aiTargetHurt);
                this.targetTasks.removeTask(aiTargetAttack);

                Packet.networkWrapper.sendToAll(new CPacketRobotSync(this.getEntityId(), "death"));
                this.playSound(GwSoundHandler.ROBOT_DEATH, 1, 1);
            }
            else
            {
                if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, cause)) return;

                ItemStack core = GroundItemStack.ROBOT_CORE.copy();
                core.setCount(1);

                int mixRadnom = (int) (( Math.random() * 3) + 3);

                ItemStack mix = GroundItemStack.MIXGOLD.copy();
                mix.setCount(mixRadnom);
                EntityItem coreEntity = new EntityItem(this.world);
                coreEntity.setItem(core);

                EntityItem mixEntity =new EntityItem(this.world);
                mixEntity.setItem(mix);
                this.capturedDrops.add(mixEntity);
                this.capturedDrops.add(coreEntity);



                for (EntityItem item : capturedDrops)
                {
                    item.setPosition(this.posX, this.posY, this.posZ);
                    world.spawnEntity(item);
                }

                setDead();

            }

        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return GwSoundHandler.ROBOT_DEATH;
    }

    public void setAttacking(boolean attacking)
    {
        is_Attack = attacking;
        if (!world.isRemote)
        {
            if (attacking)
                Packet.networkWrapper.sendToAll(new CPacketRobotSync(this.getEntityId(), "attack"));
            else
                Packet.networkWrapper.sendToAll(new CPacketRobotSync(this.getEntityId(), "walk"));
        }
    }

    public boolean isWalking() {
        return is_Walking;
    }

    public void setWalking(boolean walking) {
        is_Walking = walking;
        if (!world.isRemote) {
            if(walking)
                Packet.networkWrapper.sendToAll(new CPacketRobotSync(this.getEntityId(), "walk"));
            else
                Packet.networkWrapper.sendToAll(new CPacketRobotSync(this.getEntityId(), "idle"));
        }
    }

    public String getCurrentState() {
        if (isAttacking()) {
            return "attack";
        } else if (isWalking()) {
            return "walk";
        } else {
            return "idle";
        }
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return GwSoundHandler.ROBOT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return GwSoundHandler.HAMMER;
    }


    public int tickTimer() {
        return this.ticksExisted;
    }

    @Override
    public void tick() {
        super.onUpdate();
    }
}
