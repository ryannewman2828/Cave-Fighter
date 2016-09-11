/**
 * This code was taken from: http://www.kilobolt.com/game-development-tutorial.html
 * I have edited this code slightly for functionality purposes only
 */
package cave.fighter.animation.framework;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

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

	public synchronized Animation clone(){
		Animation anim = new Animation();
	    for (int i = 0; i < frames.size(); i++) {
			anim.addFrame(frames.get(i).image, 100);
		}
	    return anim;
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
