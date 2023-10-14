package uk.gemwire.bolblox;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.ScreenUtils;
import uk.gemwire.bolblox.format.xml.Item;
import uk.gemwire.bolblox.format.xml.Roblox;

import javax.xml.bind.JAXBContext;
import java.util.ArrayList;
import java.util.List;

public class Bolblox extends ApplicationAdapter {

	private Roblox level;

	private BitmapFont font;
	private SpriteBatch batch;
	public ModelBatch modelBatch;

	public PerspectiveCamera cam;
	public Environment environment;


	private List<Item> parts;
	public List<Model> partModels;
	public List<ModelInstance> instances;

	@Override
	public void create () {
		FileHandle handle = Gdx.files.internal("Place2.rbxl");
		try {
			JAXBContext context = JAXBContext.newInstance(Roblox.class);
			level = (Roblox) context.createUnmarshaller().unmarshal(handle.file());
			parts = new ArrayList<Item>();

			for (Item i : level.getItem()) {
				if (i.getClazz().equals("Workspace")) {
					for (Object o : i.getItemOrProperties()) {
						if (o instanceof Item) {
							if (((Item) o).getClazz().equals("Part"))
								parts.add((Item) o);
						}
					}
				}
			}
		} catch(Exception x) {
			throw new RuntimeException(x);
		}

		batch = new SpriteBatch();
		font = new BitmapFont();
		cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		partModels = new ArrayList<Model>();
		instances = new ArrayList<ModelInstance>();

		modelBatch = new ModelBatch();

		ModelBuilder modelBuilder = new ModelBuilder();

		for (Item part : parts) {

		}

		 modelBuilder.createBox(1f, 1f, 1f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);


		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);

		for (ModelInstance i : instances)
			modelBatch.render(i, environment);

		modelBatch.end();

		batch.begin();

		font.draw(batch, "There are " + level.getItem().size() + " items in the level", Gdx.graphics.getWidth() / 2 - 100,  50);
		font.draw(batch, "There are " + parts.size() + " Parts in the level.", Gdx.graphics.getWidth() / 2 - 100,  25);

		batch.end();


	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
		for (Model m : partModels)
			m.dispose();
	}
}
