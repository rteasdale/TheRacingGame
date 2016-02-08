/**
 * ApplicationAdapter
 */

package Test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Test1 extends ApplicationAdapter {
	private Stage stage;
	private Skin skin;
        private Image image;

	@Override
	public void create () {
            stage = new Stage(new ScreenViewport());            
            FileHandle evilFileHandle = Gdx.files.internal("evil.png");
            
            stage = new Stage(new ScreenViewport());
            skin = new Skin(Gdx.files.internal("uiskin.json"));
		

            image = new Image(new Texture("shark.png"));
            image.setX(100);
            image.setY(100);

            final TextButton button = new TextButton("Click Me", skin, "default");
            button.setWidth(200);
            button.setHeight(50);

            final Dialog dialog = new Dialog("Click Message", skin);

            stage.addActor(button);
            stage.addActor(image);
            Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render () {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
	}
}
