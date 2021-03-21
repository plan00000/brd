package com.zzy.brd.util.kaptcha;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.google.code.kaptcha.util.Configurable;

/***
 * 自定义noiseProducer
 * @author wwy
 *
 */
public class SimpleNoiseProducer extends Configurable implements
		com.google.code.kaptcha.NoiseProducer {

	/**
	 * Draws a noise on the image. The noise curve depends on the factor values.
	 * Noise won't be visible if all factors have the value > 1.0f
	 * 
	 * @param image
	 *            the image to add the noise to
	 * @param factorOne
	 * @param factorTwo
	 * @param factorThree
	 * @param factorFour
	 */
	public void makeNoise(BufferedImage image, float factorOne,
			float factorTwo, float factorThree, float factorFour) {
		// Color color = getConfig().getNoiseColor();
		Random rand = new Random();
		int width = image.getWidth();
		int height = image.getHeight();

		Graphics2D graph = (Graphics2D) image.getGraphics();
		graph.setRenderingHints(new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON));
		for (int i = 0; i < 15; i++) {
			graph.setStroke(new BasicStroke(0.5f));
			Color color = new Color(rand.nextFloat(), rand.nextFloat(),
					rand.nextFloat());
			graph.setColor(color);
			graph.drawLine((int) (rand.nextFloat() * width),
					(int) (rand.nextFloat() * height),
					(int) (rand.nextFloat() * width),
					(int) (rand.nextFloat() * height));
			graph.drawArc((int) (rand.nextFloat() * width),
					(int) (rand.nextFloat() * height),
					(int) (rand.nextFloat() * width),
					(int) (rand.nextFloat() * height),
					(int) (rand.nextFloat() * 360),
					(int) (rand.nextFloat() * 360));
			graph.setStroke(new BasicStroke(1f));
			graph.drawArc((int) (rand.nextFloat() * width),
					(int) (rand.nextFloat() * height),
					(int) (rand.nextFloat() * 2), 
					(int) (rand.nextFloat() * 2),
					0, 360);
		}
		graph.dispose();

	}
}
