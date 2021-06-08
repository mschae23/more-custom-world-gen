package de.martenschaefer.morecustomworldgen.command;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecoratorConfigs;

public class SaveBiomeMapCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("savebiomemap")
                .requires(source -> source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);

        });
    }

    private static int execute(ServerCommandSource source) {
        BufferedImage img = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_RGB);

        BiomeSource biomeSource = BiomeDecoratorConfigs.getVanillaBiomeSource(source.getWorld().getSeed(), source.getRegistryManager().get(Registry.BIOME_KEY));

        for (int x = -1024; x < 1024; x++) {
            if (x % 256 == 0) {
                source.sendFeedback(new LiteralText(((x + 1024) / 2048.0) * 100 + "%"), false);
            }

            for (int z = -1024; z < 1024; z++) {
                Biome biome = biomeSource.getBiomeForNoiseGen(x, 63, z);

                img.setRGB(x + 1024, z + 1024, switch(biome.getCategory()) {
                    case OCEAN -> 0x00ccff;
                    case PLAINS -> 0x00ff00;
                    case TAIGA -> 0x00dd88;
                    case DESERT -> 0xaa66;
                    case ICY -> 0xffffff;
                    default -> 0x000000;
                });
            }
        }

        // save the biome map
        Path p = Paths.get("biomemap.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        source.sendFeedback(new LiteralText("Saved biome map!"), true);

        return 0;
    }

    private static int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
