package com.doubleos.gw.entity;

import com.doubleos.gw.automine.AutoMineArea;
import com.doubleos.gw.automine.AutoMineList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntitySubWayNpc extends EntityVillager implements IAnimatable, IAnimationTickable
{

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();

    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation((new AnimationBuilder()).addAnimation("st_robo1", true));
        return PlayState.CONTINUE;
    }

    public int name = 0;

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        setHealth(100f);
    }
    public EntitySubWayNpc(World worldIn) {
        super(worldIn);
        this.ignoreFrustumCheck = true;

        this.setSize(1f, 2f);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("지하철안내원");
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }


    @Override
    public String getCustomNameTag() {
        return "지하철안내원";
    }

    @Override
    public String getName() {
        return "지하철안내원";
    }


    @Override
    protected SoundEvent getAmbientSound()
    {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return null;
    }

    @Override
    protected void playHurtSound(DamageSource source)
    {

    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_GENERIC_DEATH;
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 50.0F, this::predicate));
    }

    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
    }

    public int tickTimer() {
        return this.ticksExisted;
    }

    public void tick() {
        super.onUpdate();

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;

        for(AutoMineArea mine : AutoMineList.Instance().m_mapAreaList)
        {
            BlockPos pos = new BlockPos(mine.m_areaStartXPos, mine.m_areaStartYPos, mine.m_areaStartZPos);

            if (pos.getDistance((int) this.posX, (int) this.posY, (int) this.posZ) >= 5f && pos.getDistance((int) this.posX, (int) this.posY, (int) this.posZ) <= 10f)
            {
                this.setPosition(pos.getX()+0.5, pos.getY()+ 0.01, pos.getZ()+0.5);
            }

        }

    }
}
