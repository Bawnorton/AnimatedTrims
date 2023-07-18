package com.bawnorton.animatedtrims.client.implementation;

import com.bawnorton.animatedtrims.config.Config;
import com.bawnorton.animatedtrims.config.ConfigManager;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public abstract class YACLImpl {
    public static Screen getScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("yacl.animatedtrims.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("yacl.animatedtrims.category.general"))
                        .tooltip(Text.translatable("yacl.animatedtrims.tooltip.general"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("yacl.animatedtrims.group.main"))
                                .description(OptionDescription.of(Text.translatable("yacl.animatedtrims.description.main")))
                                .option(Option.<Integer>createBuilder()
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("yacl.animatedtrims.description.wave_intensity"))
                                                .build())
                                        .name(Text.translatable("yacl.animatedtrims.name.wave_intensity"))
                                        .binding(50, () -> Config.getInstance().waveIntensity, (value) -> Config.getInstance().waveIntensity = value)
                                        .controller(option -> IntegerSliderControllerBuilder.create(option)
                                                .range(0, 100)
                                                .step(1)
                                                .valueFormatter(value -> Text.of(value + "%")))
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("yacl.animatedtrims.group.debug"))
                                .description(OptionDescription.of(Text.translatable("yacl.animatedtrims.description.debug")))
                                .option(Option.<Boolean>createBuilder()
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("yacl.animatedtrims.description.debug1"),
                                                        Text.translatable("yacl.animatedtrims.description.debug2"),
                                                        Text.translatable("yacl.animatedtrims.description.debug3"))
                                                .build())
                                        .name(Text.translatable("yacl.animatedtrims.name.debug"))
                                        .binding(false, () -> Config.getInstance().debug, (value) -> Config.getInstance().debug = value)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .collapsed(true)
                                .build())
                        .build()
                ).save(ConfigManager::saveConfig)
                .build()
                .generateScreen(parent);
    }
}
