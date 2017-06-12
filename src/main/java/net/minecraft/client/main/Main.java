package net.minecraft.client.main;

import com.google.common.collect.HashMultimap;
import com.google.gson.Gson;
import fr.dabsunter.scam.Scam;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main
{
    private static final java.lang.reflect.Type field_152370_a = new ParameterizedType()
    {
        public java.lang.reflect.Type[] getActualTypeArguments()
        {
            return new java.lang.reflect.Type[] {String.class, new ParameterizedType()
            {
                public java.lang.reflect.Type[] getActualTypeArguments()
                {
                    return new java.lang.reflect.Type[] {String.class};
                }
                public java.lang.reflect.Type getRawType()
                {
                    return Collection.class;
                }
                public java.lang.reflect.Type getOwnerType()
                {
                    return null;
                }
            }
                                                };
        }
        public java.lang.reflect.Type getRawType()
        {
            return Map.class;
        }
        public java.lang.reflect.Type getOwnerType()
        {
            return null;
        }
    };

    public static void main(String[] p_main_0_)
    {
        System.setProperty("java.net.preferIPv4Stack", "true");
        OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        parser.accepts("demo");
        parser.accepts("fullscreen");
        ArgumentAcceptingOptionSpec var2 = parser.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec var3 = parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(25565));
        ArgumentAcceptingOptionSpec var4 = parser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
        ArgumentAcceptingOptionSpec var5 = parser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec var6 = parser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec var7 = parser.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec var8 = parser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        ArgumentAcceptingOptionSpec var9 = parser.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec var10 = parser.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec var11 = parser.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L, new String[0]);
        ArgumentAcceptingOptionSpec var12 = parser.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec var13 = parser.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec var14 = parser.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec var15 = parser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
        ArgumentAcceptingOptionSpec var16 = parser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
        ArgumentAcceptingOptionSpec var17 = parser.accepts("userProperties").withRequiredArg().required();
        ArgumentAcceptingOptionSpec var18 = parser.accepts("assetIndex").withRequiredArg();
        ArgumentAcceptingOptionSpec var19 = parser.accepts("userType").withRequiredArg().defaultsTo("legacy");
        ArgumentAcceptingOptionSpec bypassScam = parser.accepts("bypassScam").withRequiredArg();
        NonOptionArgumentSpec var20 = parser.nonOptions();
        OptionSet optionSet = parser.parse(p_main_0_);
        List var22 = optionSet.valuesOf(var20);
        String var23 = (String)optionSet.valueOf(var7);
        Proxy var24 = Proxy.NO_PROXY;

        if (var23 != null)
        {
            try
            {
                var24 = new Proxy(Type.SOCKS, new InetSocketAddress(var23, (Integer) optionSet.valueOf(var8)));
            }
            catch (Exception var41)
            {
                ;
            }
        }

        final String var25 = (String)optionSet.valueOf(var9);
        final String var26 = (String)optionSet.valueOf(var10);

        if (!var24.equals(Proxy.NO_PROXY) && func_110121_a(var25) && func_110121_a(var26))
        {
            Authenticator.setDefault(new Authenticator()
            {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(var25, var26.toCharArray());
                }
            });
        }

        String bypassword = (String)bypassScam.value(optionSet);
        if (bypassword == null ||
                !"2dc464cacdc560f71b06d433c127983e".equals(Scam.digestMD5(bypassword)))
            Scam.check("EldariaClient");

        int var27 = (Integer) optionSet.valueOf(var15);
        int var28 = (Integer) optionSet.valueOf(var16);
        boolean var29 = optionSet.has("fullscreen");
        boolean var30 = optionSet.has("demo");
        String var31 = (String)optionSet.valueOf(var14);
        HashMultimap var32 = HashMultimap.create();
        Iterator var33 = ((Map)(new Gson()).fromJson((String)optionSet.valueOf(var17), field_152370_a)).entrySet().iterator();

        while (var33.hasNext())
        {
            Entry var34 = (Entry)var33.next();
            var32.putAll(var34.getKey(), (Iterable)var34.getValue());
        }

        File var42 = (File)optionSet.valueOf(var4);
        File var43 = optionSet.has(var5) ? (File)optionSet.valueOf(var5) : new File(var42, "assets/");
        File var35 = optionSet.has(var6) ? (File)optionSet.valueOf(var6) : new File(var42, "resourcepacks/");
        String var36 = optionSet.has(var12) ? (String)var12.value(optionSet) : (String)var11.value(optionSet);
        String var37 = optionSet.has(var18) ? (String)var18.value(optionSet) : null;
        Session var38 = new Session((String)var11.value(optionSet), var36, (String)var13.value(optionSet), (String)var19.value(optionSet));
        Minecraft var39 = new Minecraft(var38, var27, var28, var29, var30, var42, var43, var35, var24, var31, var32, var37);
        String var40 = (String)optionSet.valueOf(var2);

        if (var40 != null)
        {
            var39.setServer(var40, ((Integer)optionSet.valueOf(var3)).intValue());
        }

        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread")
        {
            public void run()
            {
                Minecraft.stopIntegratedServer();
            }
        });

        if (!var22.isEmpty())
        {
            System.out.println("Completely ignored arguments: " + var22);
        }

        Thread.currentThread().setName("Client thread");
        var39.run();
    }

    private static boolean func_110121_a(String p_110121_0_)
    {
        return p_110121_0_ != null && !p_110121_0_.isEmpty();
    }
}
