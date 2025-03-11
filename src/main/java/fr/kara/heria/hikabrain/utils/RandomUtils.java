package fr.kara.heria.hikabrain.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RandomUtils {
	
	private RandomUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static final Random random;

	static {
		random = new Random(System.nanoTime());
	}

	public static Vector getRandomVector() {
		final double x = RandomUtils.random.nextDouble() * 2.0 - 1.0;
		final double y = RandomUtils.random.nextDouble() * 2.0 - 1.0;
		final double z = RandomUtils.random.nextDouble() * 2.0 - 1.0;
		return new Vector(x, y, z).normalize();
	}

	public static Vector getRandomCircleVector() {
		final double rnd = RandomUtils.random.nextDouble() * 2.0 * 3.141592653589793;
		final double x = Math.cos(rnd);
		final double z = Math.sin(rnd);
		return new Vector(x, 0.0, z);
	}

	public static Material getRandomMaterial(final Material[] materials) {
		Material mat = Material.AIR;
		while (mat == Material.AIR)
		{
			mat = materials[RandomUtils.random.nextInt(materials.length)];
		}
		return mat;
	}

	public static double getRandomAngle() {
		return RandomUtils.random.nextDouble() * 2.0 * Math.PI;
	}
	
	public static double getRandomAngle(Double limitInDegrees) {
		Double output = 100.0;
		while (output > Math.toRadians(limitInDegrees))
			output = RandomUtils.random.nextDouble() * 2.0 * Math.PI;
		return output;
	}

	public static Player getRandomPlayer() {
		if (Bukkit.getOnlinePlayers().isEmpty())
			return null;
		
		List<Player> rplayer = new ArrayList<Player>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			rplayer.add(p);
		}
		int alea = new Random().nextInt(rplayer.size());
		Player rplayer2 = rplayer.get(alea);
		return rplayer2;
	}
	
	public static Boolean getRandomByPercent(Integer percent)
	{
		Integer rdm = new Random().nextInt(101);
        return rdm < percent;
	}
	
	public static <T> T getRandom(List<T> list) {
		int rdm = random.nextInt(list.size());
		return list.get(rdm);
	}
	
	@SafeVarargs
	public static <T> T getRandom(T... list) {
		int rdm = random.nextInt(list.length);
		return list[rdm];
	}
}