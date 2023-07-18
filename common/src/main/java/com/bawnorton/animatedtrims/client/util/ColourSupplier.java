package com.bawnorton.animatedtrims.client.util;

import java.awt.*;

@FunctionalInterface
public interface ColourSupplier {
    Color getColour(int frame);
}
