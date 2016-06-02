package com.mmoclient.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class KeyInputListener {

	private float counter, textAnimationCounter;
	private String text;

	private int maxLength;
	private boolean useSymbols;

	public KeyInputListener(boolean useSymbols, int maxLength) {
		text = "";

		counter = 0.2f;
		textAnimationCounter = 0;

		this.useSymbols = useSymbols;
		this.maxLength = maxLength;
	}

	public void update(float delta) {

		int tempLength = text.length();
		textAnimationCounter += delta;
		if (textAnimationCounter > 1)
			textAnimationCounter -= 1;

		if (text.length() < maxLength) {
			if (Gdx.input.isKeyJustPressed(Keys.Q))
				text += "q";
			if (Gdx.input.isKeyJustPressed(Keys.W))
				text += "w";
			if (Gdx.input.isKeyJustPressed(Keys.E))
				text += "e";
			if (Gdx.input.isKeyJustPressed(Keys.R))
				text += "r";
			if (Gdx.input.isKeyJustPressed(Keys.T))
				text += "t";
			if (Gdx.input.isKeyJustPressed(Keys.Y))
				text += "y";
			if (Gdx.input.isKeyJustPressed(Keys.U))
				text += "u";
			if (Gdx.input.isKeyJustPressed(Keys.I))
				text += "i";
			if (Gdx.input.isKeyJustPressed(Keys.O))
				text += "o";
			if (Gdx.input.isKeyJustPressed(Keys.P))
				text += "p";
			if (Gdx.input.isKeyJustPressed(Keys.A))
				text += "a";
			if (Gdx.input.isKeyJustPressed(Keys.S))
				text += "s";
			if (Gdx.input.isKeyJustPressed(Keys.D))
				text += "d";
			if (Gdx.input.isKeyJustPressed(Keys.F))
				text += "f";
			if (Gdx.input.isKeyJustPressed(Keys.G))
				text += "g";
			if (Gdx.input.isKeyJustPressed(Keys.H))
				text += "h";
			if (Gdx.input.isKeyJustPressed(Keys.J))
				text += "j";
			if (Gdx.input.isKeyJustPressed(Keys.K))
				text += "k";
			if (Gdx.input.isKeyJustPressed(Keys.L))
				text += "l";
			if (Gdx.input.isKeyJustPressed(Keys.Z))
				text += "z";
			if (Gdx.input.isKeyJustPressed(Keys.X))
				text += "x";
			if (Gdx.input.isKeyJustPressed(Keys.C))
				text += "c";
			if (Gdx.input.isKeyJustPressed(Keys.V))
				text += "v";
			if (Gdx.input.isKeyJustPressed(Keys.B))
				text += "b";
			if (Gdx.input.isKeyJustPressed(Keys.N))
				text += "n";
			if (Gdx.input.isKeyJustPressed(Keys.M))
				text += "m";

			if (Gdx.input.isKeyJustPressed(Keys.NUM_1))
				text += "1";
			if (Gdx.input.isKeyJustPressed(Keys.NUM_2))
				text += "2";
			if (Gdx.input.isKeyJustPressed(Keys.NUM_3))
				text += "3";
			if (Gdx.input.isKeyJustPressed(Keys.NUM_4))
				text += "4";
			if (Gdx.input.isKeyJustPressed(Keys.NUM_5))
				text += "5";
			if (Gdx.input.isKeyJustPressed(Keys.NUM_6))
				text += "6";
			if (Gdx.input.isKeyJustPressed(Keys.NUM_7))
				text += "7";
			if (Gdx.input.isKeyJustPressed(Keys.NUM_8))
				text += "8";
			if (Gdx.input.isKeyJustPressed(Keys.NUM_9))
				text += "9";
			if (Gdx.input.isKeyJustPressed(Keys.NUM_0))
				text += "0";

			if (Gdx.input.isKeyJustPressed(Keys.SPACE))
				text += " ";

			if (Gdx.input.isKeyJustPressed(Keys.MINUS))
				text += "-";
		}
		if ((Gdx.input.isKeyPressed(Keys.BACKSPACE)) && text.length() > 0)
			counter -= delta;
		if ((Gdx.input.isKeyJustPressed(Keys.BACKSPACE)) && text.length() > 0) {
			text = text.substring(0, text.length() - 1);
		} else if (counter < 0) {
			if (text.length() >= 1) {
				text = text.substring(0, text.length() - 1);
				counter += 0.2f;
			}
		}

		if ((text.length() > tempLength)
				&& (Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT) || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))) {
			char newChar = text.charAt(text.length() - 1);
			if (newChar == 'q')
				newChar = 'Q';
			if (newChar == 'w')
				newChar = 'W';
			if (newChar == 'e')
				newChar = 'E';
			if (newChar == 'r')
				newChar = 'R';
			if (newChar == 't')
				newChar = 'T';
			if (newChar == 'y')
				newChar = 'Y';
			if (newChar == 'u')
				newChar = 'U';
			if (newChar == 'i')
				newChar = 'I';
			if (newChar == 'o')
				newChar = 'O';
			if (newChar == 'p')
				newChar = 'P';
			if (newChar == 'a')
				newChar = 'A';
			if (newChar == 's')
				newChar = 'S';
			if (newChar == 'd')
				newChar = 'D';
			if (newChar == 'f')
				newChar = 'F';
			if (newChar == 'g')
				newChar = 'G';
			if (newChar == 'h')
				newChar = 'H';
			if (newChar == 'j')
				newChar = 'J';
			if (newChar == 'k')
				newChar = 'K';
			if (newChar == 'l')
				newChar = 'L';
			if (newChar == 'z')
				newChar = 'Z';
			if (newChar == 'x')
				newChar = 'X';
			if (newChar == 'c')
				newChar = 'C';
			if (newChar == 'v')
				newChar = 'V';
			if (newChar == 'b')
				newChar = 'B';
			if (newChar == 'n')
				newChar = 'N';
			if (newChar == 'm')
				newChar = 'M';

			if (useSymbols) {
				if (newChar == '1')
					newChar = '!';
				if (newChar == '2')
					newChar = '"';
				if (newChar == '3')
					newChar = '#';
				if (newChar == '4')
					newChar = '�';
				if (newChar == '5')
					newChar = '%';
				if (newChar == '6')
					newChar = '&';
				if (newChar == '7')
					newChar = '/';
				if (newChar == '8')
					newChar = '(';
				if (newChar == '9')
					newChar = ')';
				if (newChar == '0')
					newChar = '=';
				if (newChar == '-')
					newChar = '_';
			}

			text = text.substring(0, text.length() - 1) + newChar;
		}
	}

	public String getText() {
		return text;
	}

	public void clearText() {
		text = "";
	}

	public float getAnimationCounter() {
		return textAnimationCounter;
	}
}
