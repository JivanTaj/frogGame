package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.codeandweb.physicseditor.PhysicsShapeCache;

import java.util.HashMap;

public class Frog {
    private Sprite frogSprite;
    private TextureAtlas textureAtlas;
    static SpriteBatch batch;
    private World world;
    private static PhysicsShapeCache physicsBodies;
    static Body myFrog;

    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    public Frog() {

    }
    public Sprite createFrog(float x, float y) {

        Frog frog = new Frog();
        frog.create();
        frog.render(batch, x, y);
        frog.dispose();

        return frogSprite;
    }

    public void create() {
        Box2D.init();
        //physicsBodies = new PhysicsShapeCache("frogStandMap.xml"); //maps body to shape of sprite
        //world = new World(new Vector2(0, -10), true);

        batch = new SpriteBatch();

        textureAtlas = new TextureAtlas("frogSheet.txt"); //sprite sheet reference
        frogSprite = textureAtlas.createSprite("frogStand");

        //render(batch);


        //addSprites();

        //myFrog = createBody("FrogStandby", 10, 20, 0);
    }
    public void render(SpriteBatch batch, float x, float y) {
        //stepWorld();
        batch.begin();

        //Vector2 position = myFrog.getPosition();
        //frogSprite.setPosition(position.x,position.y);
        frogSprite.setPosition(x, y);
        frogSprite.draw(batch);

        batch.end();
    }

    public void update(float x, float y) {
        frogSprite.setPosition(x, y);
        frogSprite.getX();
        frogSprite.getY();
    }
    private void drawSprite(String name, float x, float y) {
        Sprite sprite = sprites.get(name);

        sprite.setPosition(x, y);

        sprite.draw(batch);
    }
    public void dispose() {
        textureAtlas.dispose();
        //world.dispose();

        sprites.clear();
    }

    static final float SCALE = 0.05f;

    private void addSprites() {
        Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();

        for (TextureAtlas.AtlasRegion region : regions) {
            Sprite sprite = textureAtlas.createSprite(region.name);

            float width = sprite.getWidth() * SCALE;
            float height = sprite.getHeight() * SCALE;

            sprite.setSize(width, height);
            sprite.setOrigin(0, 0);

            sprites.put(region.name, sprite);
        }
    }


    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;

    float accumulator = 0;

    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    private Body createBody(String name, float x, float y, float rotation) {
        Body body = physicsBodies.createBody(name, world, SCALE, SCALE);
        body.setTransform(x, y, rotation);
        return body;
    }

}
