package cn.edu.qqhru.train.vo;

import java.io.Serializable;

public class PositionVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int height;
	private int width;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return "PositionVo [x=" + x + ", y=" + y + ", height=" + height + ", width=" + width + "]";
	}

	public PositionVo(int x, int y, int height, int width) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	public PositionVo() {
		super();
	}
}
