package me.marvinweber.isaac.client.map;

import com.mojang.blaze3d.systems.RenderSystem;
import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.client.IsaacClient;
import me.marvinweber.isaac.mapgen.Room;
import me.marvinweber.isaac.mapgen.rooms.layouts.SpecialRooms;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class Map {
    private static final Identifier MAP_TEXTURES = new Identifier(Isaac.MOD_ID, "textures/gui/minimap.png");
    private ArrayList<Room> rooms;
    private Room currentRoom;
    public Map() {
        this.rooms = new ArrayList<>();
    }

    public void render(MatrixStack matrices) {

        if (IsaacClient.stateManager.gameState == Game.State.STOPPED || this.rooms.isEmpty() || this.currentRoom == null) return;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();

        int frameWidth = 55;
        int frameHeight = 49;
        int margin = 10;

        RenderSystem.setShaderTexture(0, MAP_TEXTURES);
        matrices.push();
        RenderSystem.enableBlend();
        assert minecraftClient.player != null;
        //matrices.translate(minecraftClient.getWindow().getScaledWidth() - frameWidth / 2 - 4, frameHeight / 2 - 4, 0);
        DrawableHelper.fill(matrices, minecraftClient.getWindow().getScaledWidth() - frameWidth - margin,  margin, 20, 20, Color.BLACK.getAlpha());
//        matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(minecraftClient.player.headYaw));
        DrawableHelper.drawTexture(matrices, minecraftClient.getWindow().getScaledWidth() - frameWidth - margin,  margin, 0, 0, frameWidth, frameHeight, 128, 256);
        matrices.translate(minecraftClient.getWindow().getScaledWidth() - frameWidth - margin, margin, 0);

        for (int x = this.currentRoom.gridX - 2; x < this.currentRoom.gridX + 4; x++) {
            for (int y = this.currentRoom.gridY - 2; y < this.currentRoom.gridY + 3; y++) {
                int finalX = x;
                int finalY = y;
                Optional<Room> roomOptional = this.rooms.stream().filter((room -> room.gridX == finalX && room.gridY == finalY)).findFirst();
                if (roomOptional.isPresent()) {
                    Room room = roomOptional.get();
                    int widthMargin = 8 * (room.gridX - this.currentRoom.gridX);
                    int heightMargin = 8 * (room.gridY - this.currentRoom.gridY);
                    System.out.println(room);
                    if(room.type == SpecialRooms.SECRET) continue;
                    if (room.gridX == this.currentRoom.gridX && room.gridY == this.currentRoom.gridY) {
                        DrawableHelper.drawTexture(matrices, frameWidth / 2 - 4 + widthMargin, frameHeight / 2 - 4 + heightMargin, 27, 192, 9, 8, 128, 256);
                    }
                    else if(room.entered) {
                        DrawableHelper.drawTexture(matrices, frameWidth / 2 - 4 + widthMargin, frameHeight / 2 - 4 + heightMargin, 27, 160, 9, 8, 128, 256);
                    }
                    else if (room.visible) {
                        DrawableHelper.drawTexture(matrices, frameWidth / 2 - 4 + widthMargin, frameHeight / 2 - 4 + heightMargin, 27, 224, 9, 8, 128, 256);
                    }
                }
            }
        }
        matrices.pop();
        RenderSystem.disableBlend();
        minecraftClient.getItemRenderer().renderInGuiWithOverrides(matrices, new ItemStack(Items.COMPASS), minecraftClient.getWindow().getScaledWidth() - 30, 70);
    }


    public void update() {

    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
