package me.marvinweber.isaac.client;

import me.marvinweber.isaac.Game;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class StateManager {
    public Game.State gameState;

    public StateManager() {
        this.gameState = Game.State.STOPPED;
    }
}
