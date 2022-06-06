package com.pddgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;


/**
 *
 * Запуск игрового приложения с переохдом на стартовый экран и загрузкой параметров
 *
 */
public class Pddgame extends com.pddgame.game.screens.DirectedGame {

	@Override
	public void create() {
		Gdx.app.setLogLevel(1);
		com.pddgame.game.game.Assets.instance.init(new AssetManager());
		com.pddgame.game.utils.GamePreferences.instance.load();
		setScreen(new com.pddgame.game.screens.StartScreen(this));
	}
}
