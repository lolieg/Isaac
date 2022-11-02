package me.marvinweber.isaac;

import me.marvinweber.isaac.entities.Player;
import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class ScoreboardUtil {
    public static String NameByScore(Scoreboard scoreboard, ScoreboardObjective scoreboardObjective, int Score) {
        for (ScoreboardPlayerScore playerScore : scoreboard.getAllPlayerScores(scoreboardObjective)) {
            if (playerScore.getScore() == Score) {
                return playerScore.getPlayerName();
            }
        }
        return null;
    }

    public static void tick(MinecraftServer t, Game g) {
        ServerScoreboard scoreboard = t.getScoreboard();

        if (g.currentRoom != null) {
            updateProperty(scoreboard, "roomType", g.currentRoom.getClass().getSimpleName());
        }
    }

    private static void updateProperty(ServerScoreboard scoreboard, String propertyName, String newValue) {
        Team team = scoreboard.getTeam(propertyName);
        if (team != null)
            team.setPrefix(new LiteralText(newValue));
    }

    public static void start(MinecraftServer t) {
        ServerScoreboard scoreboard = t.getScoreboard();
        ScoreboardObjective server = scoreboard.getObjective("server");
        if (server != null) {
            scoreboard.removeObjective(server);
        }
        server = scoreboard.addObjective("server", ScoreboardCriterion.DUMMY, new LiteralText("The Binding of Steve").formatted(Formatting.BOLD, Formatting.GOLD), ScoreboardCriterion.RenderType.INTEGER);
        scoreboard.setObjectiveSlot(1, server);

        addConstantProperty(scoreboard, server, "Room: ", 15);
        addUpdatingProperty(scoreboard, server, "roomType", 14, "NormalRoom");
    }

    private static void addConstantProperty(ServerScoreboard scoreboard, ScoreboardObjective server, String value, int pos) {
        ScoreboardPlayerScore onlineName = scoreboard.getPlayerScore(value, server);
        onlineName.setScore(pos);
    }

    private static void addUpdatingProperty(ServerScoreboard scoreboard, ScoreboardObjective server, String propertyName, int pos, String defaultValue) {
        Team team = new Team(scoreboard, propertyName);
        scoreboard.addTeam(propertyName);
        scoreboard.addPlayerToTeam("", team);
        team.setPrefix(new LiteralText(defaultValue));
        scoreboard.getPlayerScore("", server).setScore(pos);
    }

    public static void stop(MinecraftServer t) {
        ServerScoreboard scoreboard = t.getScoreboard();
        if(scoreboard.getObjectiveNames().contains("server"))
            scoreboard.removeObjective(scoreboard.getObjective("server"));

        for (Team team :
                scoreboard.getTeams()) {
            scoreboard.removeTeam(team);
        }
    }
}
