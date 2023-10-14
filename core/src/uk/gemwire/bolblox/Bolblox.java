package uk.gemwire.bolblox;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import uk.gemwire.bolblox.format.xml.Roblox;

import javax.xml.bind.JAXBContext;

public class Bolblox extends ApplicationAdapter {

	private Roblox level;

	private BitmapFont font;
	private SpriteBatch batch;

	@Override
	public void create () {
		FileHandle handle = Gdx.files.internal("sword_fighting.rbxlx");
		try {
			JAXBContext context = JAXBContext.newInstance(Roblox.class);
			level = (Roblox) context.createUnmarshaller().unmarshal(handle.file());
		} catch(Exception x) {
			throw new RuntimeException(x);
		}

		batch = new SpriteBatch();
		font = new BitmapFont();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();

		font.draw(batch, "There are " + level.getItem().size() + " items in the level", ScreenUtils.getFrameBufferTexture().getRegionWidth() / 2, ScreenUtils.getFrameBufferTexture().getRegionHeight() / 2);

		batch.end();

	}
	
	@Override
	public void dispose () {
	}
}
