package textures;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import processing.core.PImage;

public class TextureLoader {

	private static HashMap<String, PImage> textures;

	public static void loadTextures() {
		if (textures == null) {
			textures = new HashMap<String, PImage>();
		}
		File path = new File("sprites");

		File[] files = path.listFiles();
		for (File f : files) {
			if (!f.getName().substring(f.getName().indexOf('.')).equals(".png")) {
				continue;
			}
			try {
				BufferedImage image = ImageIO.read(f);
				String name = f.getName().substring(0, f.getName().indexOf('.'));
				textures.put(name, new PImage(image));
			} catch (IOException e) {
				System.out.println("Not a file: " + f.getName());
				e.printStackTrace();
			}
		}
	}

	public static PImage getTexture(String name) {
		if (textures == null) {
			loadTextures();
		}
		return textures.get(name);
	}
}
