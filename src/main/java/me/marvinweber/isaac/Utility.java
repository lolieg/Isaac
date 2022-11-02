package me.marvinweber.isaac;

import net.minecraft.util.Pair;

import java.security.SecureRandom;
import java.util.Random;

public class Utility {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static SecureRandom rnd = new SecureRandom();

    public static int getRandomNumber(int min, int max, Random random) {
        return random.nextInt(max - min) + min;
    }

    public static Pair<Integer, Integer> splitNumber(int num) {
        return new Pair<>(num % 10, (num % 100) / 10);
    }

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
