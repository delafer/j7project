package org.delafer.xanderView.orientation;

import java.util.HashMap;
import java.util.Map;

public class OrientationCommons {

	//public enum Action {Left, Right, TurnAbout, VerticalFlip, HorizontalFlip };

	public enum Action {RotateLeft(0), RotateRight(1), FlipVertical(2), FlipHorizontal(3);
	int code;
	Action(int n) {code = n;}
	int code() {return code;}
	};

	final static int stOriginal 		= 0;
	final static int stRotatedLeft 		= 1;
	final static int stRotatedRight 	= 2;
	final static int stTurnedAbout 		= 3;
	final static int stFlipVertical 	= 4;
	final static int stFlipHorizonal 	= 5;
	final static int stHFlipAndRight 	= 6;
	final static int stHFlipAndLeft		= 7;


	public enum Orientation {

		Original(stOriginal, stRotatedLeft, stRotatedRight, stFlipVertical, stFlipHorizonal),
		RotatedLeft(stRotatedLeft, stTurnedAbout, stOriginal, stHFlipAndLeft, stHFlipAndRight),
		RotatedRight(stRotatedRight, stOriginal, stTurnedAbout, stHFlipAndRight, stHFlipAndLeft),
		TurnedAbout(stTurnedAbout, stRotatedRight, stRotatedLeft, stFlipHorizonal, stFlipVertical),
		FlipVertical(stFlipVertical, stHFlipAndRight, stHFlipAndLeft, stOriginal, stTurnedAbout),
		FlipHorizonal(stFlipHorizonal, stHFlipAndLeft, stHFlipAndRight, stTurnedAbout, stOriginal),

		HFlipAndRight(stHFlipAndRight, stFlipHorizonal, stFlipVertical, stRotatedRight, stRotatedLeft),
		HFlipAndLeft(stHFlipAndLeft, stFlipVertical, stFlipHorizonal, stRotatedLeft, stRotatedRight);

	int[] gotos;

	Orientation(int ownCode, int onLeft, int onRight, int onVertical, int onHorizontal) {
		gotos = new int[] {onLeft, onRight, onVertical, onHorizontal};

	}

	Orientation newState(Action action) {
		int newCode = gotos[action.code];
		switch (newCode) {
		case stOriginal:
			return Orientation.Original;
		case stRotatedLeft:
			return Orientation.RotatedLeft;
		case stRotatedRight:
			return Orientation.RotatedRight;
		case stTurnedAbout:
			return Orientation.TurnedAbout;
		case stFlipVertical:
			return Orientation.FlipVertical;
		case stFlipHorizonal:
			return Orientation.FlipHorizonal;
		case stHFlipAndRight:
			return Orientation.HFlipAndRight;
		case stHFlipAndLeft:
			return Orientation.HFlipAndLeft;
		default:
			return this;
		}
	}

	};


	public static void main(String[] args) {
		System.out.println(Orientation.TurnedAbout.newState(Action.FlipHorizontal));
	}

	//left vert = right horiz = vert right = horiz left
	//vert left = horiz right = left hori = right vert


}
