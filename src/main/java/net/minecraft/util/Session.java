package net.minecraft.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;

import java.util.Map;
import java.util.UUID;

public class Session
{
    private final String username;
    private final String playerID;
    private final String token;
    private final Session.Type field_152429_d;

    public Session(String p_i1098_1_, String p_i1098_2_, String p_i1098_3_, String p_i1098_4_)
    {
        this.username = p_i1098_1_;
        this.playerID = p_i1098_2_;
        this.token = p_i1098_3_;
        this.field_152429_d = Session.Type.func_152421_a(p_i1098_4_);
    }

    public String getSessionID()
    {
        return "token:" + this.token + ":" + this.playerID;
    }

    public String getPlayerID()
    {
        return this.playerID;
    }

    public UUID getUniqueID() {
        return UUIDTypeAdapter.fromString(this.getPlayerID());
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getToken()
    {
        return this.token;
    }

    public GameProfile func_148256_e()
    {
        try
        {
            UUID var1 = UUIDTypeAdapter.fromString(this.getPlayerID());
            return new GameProfile(var1, this.getUsername());
        }
        catch (IllegalArgumentException var2)
        {
            return new GameProfile((UUID)null, this.getUsername());
        }
    }

    public Session.Type func_152428_f()
    {
        return this.field_152429_d;
    }

    public static enum Type
    {
        LEGACY("LEGACY", 0, "legacy"),
        MOJANG("MOJANG", 1, "mojang");
        private static final Map field_152425_c = Maps.newHashMap();
        private final String field_152426_d;

        private static final Session.Type[] $VALUES = new Session.Type[]{LEGACY, MOJANG};

        private Type(String p_i1096_1_, int p_i1096_2_, String p_i1096_3_)
        {
            this.field_152426_d = p_i1096_3_;
        }

        public static Session.Type func_152421_a(String p_152421_0_)
        {
            return (Session.Type)field_152425_c.get(p_152421_0_.toLowerCase());
        }

        static {
            Session.Type[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2)
            {
                Session.Type var3 = var0[var2];
                field_152425_c.put(var3.field_152426_d, var3);
            }
        }
    }import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TargetUtils {

    // En yakın düşman oyuncuyu bulur
    public static PlayerEntity getClosestTarget(double range) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.world == null) return null;

        PlayerEntity closest = null;
        double minDistance = range * range;

        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player || !player.isAlive()) continue;

            double distSq = mc.player.squaredDistanceTo(player);
            if (distSq < minDistance) {
                minDistance = distSq;
                closest = player;
            }
        }
        return closest;
    }

    // Hedefe bakmak için gerekli Yaw ve Pitch açılarını hesaplar
    public static float[] getRotationsToEntity(PlayerEntity target) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Vec3d eyePos = mc.player.getEyePos();
        Vec3d targetPos = target.getEyePos();

        double dx = targetPos.x - eyePos.x;
        double dy = targetPos.y - eyePos.y;
        double dz = targetPos.z - eyePos.z;

        double dist = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90.0F;
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, dist));

        return new float[]{
            MathHelper.wrapDegrees(yaw),
            MathHelper.wrapDegrees(pitch)
        };
    }
}public class ElytraHelper {

    public static void updateElytraTargeting() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || !mc.player.isFallFlying()) return;

        PlayerEntity target = TargetUtils.getClosestTarget(50.0); // 50 blok menzil
        if (target != null) {
            float[] rotations = TargetUtils.getRotationsToEntity(target);
            
            // Oyuncunun bakış yönünü hedefe kilitler
            mc.player.setYaw(rotations[0]);
            mc.player.setPitch(rotations[1]);
        }
    }
}
}import net.minecraft.util.Hand;

public class KillAura {

    public static void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.interactionManager == null) return;

        PlayerEntity target = TargetUtils.getClosestTarget(4.0); // 4 blok saldırı menzili
        if (target != null) {
            // Saldırı bekleme süresi dolduysa vur
            if (mc.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                float[] rotations = TargetUtils.getRotationsToEntity(target);
                
                // Bakış açısını hedefe yönlendir
                mc.player.setYaw(rotations[0]);
                mc.player.setPitch(rotations[1]);

                // Vuruş yap
                mc.interactionManager.attackEntity(mc.player, target);
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }
}
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class PeucClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                KillAura.onTick();
                ElytraHelper.updateElytraTargeting();
            }
        });
    }
}

