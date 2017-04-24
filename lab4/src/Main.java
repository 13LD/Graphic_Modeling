import java.awt.event.*;
import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;


public class Main implements ActionListener, KeyListener {

    private TransformGroup treeTransformGroup = new TransformGroup();
    private Transform3D rotateX = new Transform3D();
    private Transform3D rotateY = new Transform3D();
    private Timer timer;
    private float angle_x = 0;
    private float angle_y = 0;
    private int sign_y = 0;
    private int sign_x = 0;
    private float diff = 0.21f;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        timer = new Timer(30, this);
        timer.start();
        BranchGroup scene = createSceneGraph();
        SimpleUniverse u = new SimpleUniverse();
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(scene);
        u.getCanvas().addKeyListener(this);
    }

    private BranchGroup createSceneGraph() {

        BranchGroup objRoot = new BranchGroup();
        treeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildSnowman();
        objRoot.addChild(treeTransformGroup);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                100.0);
        Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(0.0f, 0.0f, -2.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color,
                light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);

        return objRoot;
    }

    private void buildSnowman() {
        //nose
        TransformGroup tgTop = new TransformGroup();
        Transform3D transformTop = new Transform3D();
        Cone coneTop = getCone(0.3f, 0.05f, getBigConeAppearance());
        Vector3f vectorTop = new Vector3f(0.2f, 0.2f, 0.55f);
        transformTop.setTranslation(vectorTop);
        tgTop.setTransform(transformTop);
        tgTop.addChild(coneTop);
        treeTransformGroup.addChild(tgTop);

        //body
        createBall(0.3f, 0.2f, 0.1f, -0.25f, new Color3f(0.01f, 0.31f, 0.9f));
        createBall(0.2f, 0.2f, 0.1f, 0.25f, new Color3f(0.01f, 0.31f, 0.9f));
        createBall(0.1f, 0.2f, 0.1f, 0.55f, new Color3f(0.01f, 0.31f, 0.9f));

        //eyes
        createBall(0.01f, 0.23f, 0.17f, 0.6f, new Color3f(-1f, -1f, -1f));
        createBall(0.01f, 0.17f, 0.17f, 0.6f, new Color3f(-1f, -1f, -1f));
    }

    private void createBall(float radius, float x, float y, float z, Color3f emissive) {
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        Sphere cone = getSphere(radius, emissive);
        Vector3f vector = new Vector3f(x, y, z);
        transform.setTranslation(vector);
        tg.setTransform(transform);
        tg.addChild(cone);
        treeTransformGroup.addChild(tg);
    }

    public static Sphere getSphere(float radius, Color3f emissiveColor) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Sphere(radius, primflags, getXMassBallsAppearence( emissiveColor));
    }
    private static Appearance getXMassBallsAppearence( Color3f emissive) {
        Appearance ap = new Appearance();
        Color3f ambient = new Color3f(0.2f, 0.15f, .15f);
        Color3f diffuse = new Color3f(1.2f, 1.15f, .15f);
        Color3f specular = new Color3f(0.0f, 0.0f, 0.0f);
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }


    private static Cone getCone(float height, float radius, Appearance ap) {
        int primflags = Primitive.GENERATE_NORMALS
                + Primitive.GENERATE_TEXTURE_COORDS;
        return new Cone(radius, height, primflags, ap);
    }

    private static Appearance getBigConeAppearance() {
        Appearance ap = new Appearance();
        Color3f emissive = new Color3f(0.21f, 0.15f, 0.0f);
        Color3f ambient = new Color3f(0.37f, 0.24f, 0.0f);
        Color3f diffuse = new Color3f(0.55f, 0.42f, 0.18f);
        Color3f specular = new Color3f(0.18f, 0.12f, 0.0f);
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        rotateX.rotX(angle_x);
        rotateY.rotZ(angle_y);

        rotateX.mul(rotateY);

        treeTransformGroup.setTransform(rotateX);

        angle_x += sign_x * 0.05;
        angle_y += sign_y * 0.05;

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'в')
            sign_y += 1;
        else if (e.getKeyChar() == 'a' || e.getKeyChar() == 'ф')
            sign_y += -1;
        else if (e.getKeyChar() == 's' || e.getKeyChar() == 'ы' || e.getKeyChar() == 'і')
            sign_x += 1;
        else if (e.getKeyChar() == 'w' || e.getKeyChar() == 'ц')
            sign_x += -1;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
