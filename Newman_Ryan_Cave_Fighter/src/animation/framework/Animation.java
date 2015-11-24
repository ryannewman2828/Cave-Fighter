/**
 * Ryan Newman
 * ICS4U - 1
 * Cave Fighter
 * June 10 2015 
*/

/**
 * This code was taken from: http://www.kilobolt.com/game-development-tutorial.html
 * I have edited this code slightly for functionality purposes only
 * Nothing major enough to be graded on
 */
package animation.framework;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

	private ArrayList<AnimFrame> frames;
	private int currentFrame;
	private long animTime;
	private long totalDuration;

	public Animation() {
		frames = new ArrayList();
		totalDuration = 0;

		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
	}

	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}

	
	public synchronized void setFrame(Image image, long duration, int index) {
		frames.set(index, new AnimFrame(image, totalDuration));
	}

	public synchronized void update(long elapsedTime) {
		if (frames.size() > 1) {
			animTime += elapsedTime;
			if (animTime >= totalDuration) {
				animTime = animTime % totalDuration;
				currentFrame = 0;

			}

			while (animTime > getFrame(currentFrame).endTime) {
				currentFrame++;

			}
		}
	}

	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}

	private AnimFrame getFrame(int i) {
		return (AnimFrame) frames.get(i);
	}

	private class AnimFrame {

		Image image;
		long endTime;
		
		
		public AnimFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}
