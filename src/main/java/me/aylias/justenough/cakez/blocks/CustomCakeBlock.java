package me.aylias.justenough.cakez.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomCakeBlock extends Block {


    public enum Effect implements StringRepresentable {
        ABSORPTION("ABSORPTION"),
        BAD_OMEN("BAD_OMEN"),
        BLINDNESS("BLINDNESS"),
        CONFUSION("CONFUSION"),
        CONDUIT_POWER("CONDUIT_POWER"),
        DAMAGE_BOOST("DAMAGE_BOOST"),
        DAMAGE_RESISTANCE("DAMAGE_RESISTANCE"),
        DARKNESS("DARKNESS"),
        DIG_SLOWDOWN("DIG_SLOWDOWN"),
        DIG_SPEED("DIG_SPEED"),
        DOLPHINS_GRACE("DOLPHINS_GRACE"),
        FIRE_RESISTANCE("FIRE_RESISTANCE"),
        GLOWING("GLOWING"),
        HARM("HARM"),
        HEAL("HEAL"),
        HEALTH_BOOST("HEALTH_BOOST"),
        HERO_OF_THE_VILLAGE("HERO_OF_THE_VILLAGE"),
        HUNGER("HUNGER"),
        INVISIBILITY("INVISIBILITY"),
        JUMP("JUMP"),
        LEVITATION("LEVITATION"),
        LUCK("LUCK"),
        MOVEMENT_SLOWDOWN("MOVEMENT_SLOWDOWN"),
        MOVEMENT_SPEED("MOVEMENT_SPEED"),
        NIGHT_VISION("NIGHT_VISION"),
        POISON("POISON"),
        REGENERATION("REGENERATION"),
        SATURATION("SATURATION"),
        SLOW_FALLING("SLOW_FALLING"),
        UNLUCK("UNLUCK"),
        WATER_BREATHING("WATER_BREATHING"),
        WEAKNESS("WEAKNESS"),
        WITHER("WITHER"),
        NO_EFFECT("NO_EFFECT");
        // Add any additional methods or logic as needed

        private final String name;

        private Effect(String p_156018_) {
            this.name = p_156018_;
        }

        public String toString() {
            return this.name;
        }
        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum Color implements StringRepresentable {
        white("white"),
        orange("orange"),
        magenta("magenta"),
        light_blue("light_blue"),
        yellow("yellow"),
        lime("lime"),
        pink("pink"),
        gray("gray"),
        light_gray("light_gray"),
        cyan("cyan"),
        purple("purple"),
        blue("blue"),
        brown("brown"),
        green("green"),
        red("red"),
        black("black"),
        no_color("no_color");
        
        // Additional methods or logic
        private final String name;

        private Color(String p_156018_) {
            this.name = p_156018_;
        }

        public String toString() {
            return this.name;
        }
        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum Candle implements StringRepresentable {
        white("white"),
        orange("orange"),
        magenta("magenta"),
        light_blue("light_blue"),
        yellow("yellow"),
        lime("lime"),
        pink("pink"),
        gray("gray"),
        light_gray("light_gray"),
        cyan("cyan"),
        purple("purple"),
        blue("blue"),
        brown("brown"),
        green("green"),
        red("red"),
        black("black"),
        no_candle("no_color");

        // Additional methods or logic
        private final String name;

        private Candle(String p_156018_) {
            this.name = p_156018_;
        }

        public String toString() {
            return this.name;
        }
        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public static final int MAX_BITES = 6;
    public static final IntegerProperty BITES = BlockStateProperties.BITES;
    public static final EnumProperty<Effect> EFFECT = EnumProperty.create("justenoughcakez:effect", Effect.class);
    public static final EnumProperty<Color> COLOR = EnumProperty.create("justenoughcakez:color", Color.class);
    public static final EnumProperty<Candle> CANDLE = EnumProperty.create("justenoughcakez:candle", Candle.class);
    public static final int FULL_CAKE_SIGNAL = getOutputSignal(0);
    protected static final float AABB_OFFSET = 1.0F;
    protected static final float AABB_SIZE_PER_BITE = 2.0F;
    protected static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(3.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(5.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(7.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(9.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(11.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D), Block.box(13.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D)};

    public static final MobEffect[] mobEffects = new MobEffect[]{MobEffects.ABSORPTION, MobEffects.BAD_OMEN, MobEffects.BLINDNESS, MobEffects.CONFUSION, MobEffects.CONDUIT_POWER, MobEffects.DAMAGE_BOOST, MobEffects.DAMAGE_RESISTANCE, MobEffects.DARKNESS, MobEffects.DIG_SLOWDOWN, MobEffects.DIG_SPEED, MobEffects.DOLPHINS_GRACE, MobEffects.FIRE_RESISTANCE, MobEffects.GLOWING, MobEffects.HARM, MobEffects.HEAL, MobEffects.HEALTH_BOOST, MobEffects.HERO_OF_THE_VILLAGE, MobEffects.HUNGER, MobEffects.INVISIBILITY, MobEffects.JUMP, MobEffects.LEVITATION, MobEffects.LUCK, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.MOVEMENT_SPEED, MobEffects.NIGHT_VISION, MobEffects.POISON, MobEffects.REGENERATION, MobEffects.SATURATION, MobEffects.SLOW_FALLING, MobEffects.UNLUCK, MobEffects.WATER_BREATHING, MobEffects.WEAKNESS, MobEffects.WITHER};

    public CustomCakeBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(BITES, Integer.valueOf(0))
                        .setValue(EFFECT, Effect.NO_EFFECT)
                        .setValue(COLOR, Color.no_color)
                        .setValue(CANDLE, Candle.no_candle)
        );
    }

    public VoxelShape getShape(BlockState p_51222_, BlockGetter p_51223_, BlockPos p_51224_, CollisionContext p_51225_) {
        return SHAPE_BY_BITE[p_51222_.getValue(BITES)];
    }

    public InteractionResult use(BlockState p_51202_, Level p_51203_, BlockPos p_51204_, Player p_51205_, InteractionHand p_51206_, BlockHitResult p_51207_) {
        ItemStack itemstack = p_51205_.getItemInHand(p_51206_);
        Item item = itemstack.getItem();
        if (itemstack.is(ItemTags.CANDLES) && p_51202_.getValue(BITES) == 0) {
            Block block = Block.byItem(item);
            if (block instanceof CandleBlock) {
                if (!p_51205_.isCreative()) {
                    itemstack.shrink(1);
                }

                p_51203_.playSound((Player) null, p_51204_, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                p_51203_.setBlockAndUpdate(p_51204_, CandleCakeBlock.byCandle(block));
                p_51203_.gameEvent(p_51205_, GameEvent.BLOCK_CHANGE, p_51204_);
                p_51205_.awardStat(Stats.ITEM_USED.get(item));
                return InteractionResult.SUCCESS;
            }
        }

        if (p_51203_.isClientSide) {
            if (eat(p_51203_, p_51204_, p_51202_, p_51205_).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return eat(p_51203_, p_51204_, p_51202_, p_51205_);
    }

    protected static InteractionResult eat(LevelAccessor p_51186_, BlockPos p_51187_, BlockState p_51188_, Player p_51189_) {
        if (!p_51189_.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            p_51189_.awardStat(Stats.EAT_CAKE_SLICE);
            p_51189_.getFoodData().eat(2, 0.1F);
            int i = p_51188_.getValue(BITES);
            p_51186_.gameEvent(p_51189_, GameEvent.EAT, p_51187_);
            if (i < 6) {
                p_51186_.setBlock(p_51187_, p_51188_.setValue(BITES, Integer.valueOf(i + 1)), 3);
            } else {
                p_51186_.removeBlock(p_51187_, false);
                p_51186_.gameEvent(p_51189_, GameEvent.BLOCK_DESTROY, p_51187_);
            }

            return InteractionResult.SUCCESS;
        }
    }

    public BlockState updateShape(BlockState p_51213_, Direction p_51214_, BlockState p_51215_, LevelAccessor p_51216_, BlockPos p_51217_, BlockPos p_51218_) {
        return p_51214_ == Direction.DOWN && !p_51213_.canSurvive(p_51216_, p_51217_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_51213_, p_51214_, p_51215_, p_51216_, p_51217_, p_51218_);
    }

    public boolean canSurvive(BlockState p_51209_, LevelReader p_51210_, BlockPos p_51211_) {
        return p_51210_.getBlockState(p_51211_.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51220_) {
        p_51220_.add(BITES);
        p_51220_.add(EFFECT);
        p_51220_.add(COLOR);
        p_51220_.add(CANDLE);
    }

    public int getAnalogOutputSignal(BlockState p_51198_, Level p_51199_, BlockPos p_51200_) {
        return getOutputSignal(p_51198_.getValue(BITES));
    }

    public static int getOutputSignal(int p_152747_) {
        return (7 - p_152747_) * 2;
    }

    public boolean hasAnalogOutputSignal(BlockState p_51191_) {
        return true;
    }

    public boolean isPathfindable(BlockState p_51193_, BlockGetter p_51194_, BlockPos p_51195_, PathComputationType p_51196_) {
        return false;
    }
}
