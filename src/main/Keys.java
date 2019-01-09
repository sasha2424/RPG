package main;

public class Keys {
	public boolean up, down, left, right, inventory, trackPlayer;

	public Keys() {
		up = false;
		down = false;
		left = false;
		right = false;
		inventory = false;
		trackPlayer = false;
	}

	public boolean getKey(String name) {
		switch (name) {
		case "up":
			return up;
		case "down":
			return down;
		case "left":
			return left;
		case "right":
			return right;
		case "inventory":
			return inventory;
		case "trackPlayer":
			return trackPlayer;
		}
		return false;
	}

	public void setKey(int keyCode, boolean state) {
		switch (keyCode) {
		case 37:
			left = state;
			break;
		case 65:
			left = state;
			break;
		case 38:
			up = state;
			break;
		case 87:
			up = state;
			break;
		case 39:
			right = state;
			break;
		case 68:
			right = state;
			break;
		case 40:
			down = state;
			break;
		case 83:
			down = state;
			break;
		case 89: // this is how a toggle key is setup
			if (!state)
				trackPlayer = !trackPlayer;
			break;
		case 69: // this is how a toggle key is setup
			if (!state)
				inventory = !inventory;
			break;

		}
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

}
