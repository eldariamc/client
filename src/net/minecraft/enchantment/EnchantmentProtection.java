package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class EnchantmentProtection extends Enchantment
{
    /** Holds the name to be translated of each protection type. */
    private static final String[] protectionName = new String[] {"all", "fire", "fall", "explosion", "projectile", "poison"};

    /**
     * Holds the base factor of enchantability needed to be able to use the enchant.
     */
    private static final int[] baseEnchantability = new int[] {1, 10, 5, 5, 3, 5};

    /**
     * Holds how much each level increased the enchantability factor to be able to use this enchant.
     */
    private static final int[] levelEnchantability = new int[] {11, 8, 6, 8, 6, 8};

    /**
     * Used on the formula of base enchantability, this is the 'window' factor of values to be able to use thing
     * enchant.
     */
    private static final int[] thresholdEnchantability = new int[] {20, 12, 10, 12, 15, 20};

    /**
     * Defines the type of protection of the enchantment, 0 = all, 1 = fire, 2 = fall (feather fall), 3 = explosion,
     * 4 = projectile and 5 = poison.
     */
    public final int protectionType;
    private static final String __OBFID = "CL_00000121";

    public EnchantmentProtection(int p_i1936_1_, int p_i1936_2_, int p_i1936_3_)
    {
        super(p_i1936_1_, p_i1936_2_, EnumEnchantmentType.armor);
        this.protectionType = p_i1936_3_;

        if (p_i1936_3_ == 2)
        {
            this.type = EnumEnchantmentType.armor_feet;
        }
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int p_77321_1_)
    {
        return baseEnchantability[this.protectionType] + (p_77321_1_ - 1) * levelEnchantability[this.protectionType];
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int p_77317_1_)
    {
        return this.getMinEnchantability(p_77317_1_) + thresholdEnchantability[this.protectionType];
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        if (protectionType == 2) // L'enchantement faether falling n'as plus qu'un niveau
            return 1;
        return 4;
    }

    /**
     * Calculates de damage protection of the enchantment based on level and damage source passed.
     */
    public int calcModifierDamage(int p_77318_1_, EnchantmentHelper.ModifierDamage p_77318_2_)
    {
        DamageSource source = p_77318_2_.source;
        if (source.canHarmInCreative())
        {
            return 0;
        }
        else
        {
             if (this.protectionType == 2 && source == DamageSource.fall) // Faether falling annule la chute
                p_77318_2_.cancelled = true;

            float var3 = (float)(6 + p_77318_1_ * p_77318_1_) / 3.0F;
            return this.protectionType == 0
                    ? MathHelper.floor_float(var3 * 0.75F)
                    : (this.protectionType == 1 && source.isFireDamage()
                    ? MathHelper.floor_float(var3 * 1.25F)
                    : (this.protectionType == 3 && source.isExplosion()
                    ? MathHelper.floor_float(var3 * 1.5F)
                    : (this.protectionType == 4 && source.isProjectile()
                    ? MathHelper.floor_float(var3 * 1.5F)
                    : (this.protectionType == 5 && source.isMagicDamage()
                    ? MathHelper.floor_float(var3 * 6F)
                    : 0))));
        }
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    public String getName()
    {
        return "enchantment.protect." + protectionName[this.protectionType];
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment p_77326_1_)
    {
        if (p_77326_1_ instanceof EnchantmentProtection)
        {
            EnchantmentProtection var2 = (EnchantmentProtection)p_77326_1_;
            return var2.protectionType == this.protectionType ? false : this.protectionType == 2 || var2.protectionType == 2;
        }
        else
        {
            return super.canApplyTogether(p_77326_1_);
        }
    }

    /**
     * Gets the amount of ticks an entity should be set fire, adjusted for fire protection.
     */
    public static int getFireTimeForEntity(Entity p_92093_0_, int p_92093_1_)
    {
        int var2 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, p_92093_0_.getLastActiveItems());

        if (var2 > 0)
        {
            p_92093_1_ -= MathHelper.floor_float((float)p_92093_1_ * (float)var2 * 0.15F);
        }

        return p_92093_1_;
    }

    public static double func_92092_a(Entity p_92092_0_, double p_92092_1_)
    {
        int var3 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, p_92092_0_.getLastActiveItems());

        if (var3 > 0)
        {
            p_92092_1_ -= (double)MathHelper.floor_double(p_92092_1_ * (double)((float)var3 * 0.15F));
        }

        return p_92092_1_;
    }
}
