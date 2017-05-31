package lab6;
import javax.vecmath.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.util.Enumeration;
import java.util.Hashtable;

public class lab6 extends JFrame
{
    public Canvas3D myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

    public lab6()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);
        simpUniv.getViewingPlatform().setNominalViewingTransform();
        this.createSceneGraph(simpUniv);

        BranchGroup bgLight = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f lightColour1 = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f lightDir1 = new Vector3f(-1.0f, 0.0f, -0.5f);
        DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);
        bgLight.addChild(light1);
        simpUniv.addBranchGraph(bgLight);


        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);


        this.setTitle("Mike");
        this.setSize(700, 700);
        this.getContentPane().add("Center", myCanvas3D);
        this.setVisible(true);
    }

    public void createSceneGraph(SimpleUniverse simpUniv)
    {
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        Scene trainerScene = null;
        Scene p = null;
        try
        {
            trainerScene = f.load("d://mike.obj");
        }
        catch (Exception e)
        {
            System.out.println("File loading failed:" + e);
        }
        Transform3D scaling = new Transform3D();
        scaling.setScale(1.0/6);
        Transform3D tfRoach = new Transform3D();
        tfRoach.rotX(Math.PI/2);
        tfRoach.mul(scaling);
        TransformGroup tgRoach = new TransformGroup(tfRoach);
        Hashtable trainerNamedObjects = trainerScene.getNamedObjects();
        Enumeration enumer = trainerNamedObjects.keys();
        while (enumer.hasMoreElements()) {
            String name = (String) enumer.nextElement();
            System.out.println("Name: " + name);
        }
        Transform3D trTrainer = new Transform3D();
        trTrainer.setScale(1.0/4);
        TransformGroup tgTrainer = new TransformGroup(trTrainer);
        TransformGroup sceneGroup = new TransformGroup();
        //sceneGroup.addChild(trainerScene.getSceneGroup());


        Shape3D[] trainer = new Shape3D[]
                {
                        //(Shape3D) trainerNamedObjects.get("monstr"),
                        //(Shape3D) trainerNamedObjects.get("left_leg"),
                        //(Shape3D) trainerNamedObjects.get("right_leg"),
                };
        for (Shape3D shape : trainer)
        {
            tgTrainer.addChild(shape.cloneTree());
        }
        //////////////////////////////////////////////////////////


        //////////////////////////////////////////////////////////
        Shape3D body = (Shape3D) trainerNamedObjects.get("monstr");
        Appearance App = new Appearance();
        setToMyDefaultAppearance(App, new Color3f(0.2f, 0.6f, 0.2f));
        body.setAppearance(App);
//        TextureLoader textureLoad = new TextureLoader("d://mike1.jpg",null);// завантажуємо зображення текстури
//        ImageComponent2D textureIm = textureLoad.getImage();
//        Texture2D aTexture = new Texture2D(Texture2D.BASE_LEVEL,Texture2D.RGB,textureIm.getWidth(), textureIm.getHeight(),0);//створюємо текстуру
//        aTexture.setImage(0,textureIm);//додаємо в текстуру зображення
//        Appearance textureApp = new Appearance();
//        textureApp.setTexture(aTexture);
//        TextureAttributes textureAttr = new TextureAttributes();
//        textureAttr.setTextureMode(TextureAttributes.COMBINE);
//        textureApp.setTextureAttributes(textureAttr);
//        body.setAppearance(textureApp); //накладаємо текстуру на об’єкт


        TransformGroup tgBody = new TransformGroup();
        tgBody.addChild(body.cloneTree());
        Transform3D tCrawlB = new Transform3D();
        tCrawlB.rotY(-Math.PI/2);
        long crawlTimeB = 10000;//час, за який тарган переповзе екран
        Alpha crawlAlphaB = new Alpha(1, Alpha.INCREASING_ENABLE, 0, 0, crawlTimeB,0,0,0,0,0);
        float crawlDistanceB = 9.0f; //відстань, на яку просунеться об’єкт
        PositionInterpolator posICrawlB = new PositionInterpolator(crawlAlphaB,
                tgBody,tCrawlB, -9.0f, crawlDistanceB);
        BoundingSphere bsB = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        posICrawlB.setSchedulingBounds(bsB);
        tgBody.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgBody.addChild(posICrawlB);
        tgTrainer.addChild(tgBody);


        //////////////////////////////////////////////////////////
        Shape3D lleg = (Shape3D) trainerNamedObjects.get("left_leg");
        Appearance legApp1 = new Appearance();
        setToMyDefaultAppearance(legApp1, new Color3f(0.2f, 0.6f, 0.2f));
        lleg.setAppearance(legApp1);

        TransformGroup tgLeg1 = new TransformGroup();
        tgLeg1.addChild(lleg.cloneTree());

        Transform3D minArrowRotationAxis2 = new Transform3D();
        minArrowRotationAxis2.rotZ(Math.PI/2);
        Alpha minRotationAlpha2 = new Alpha(-1, Alpha.INCREASING_ENABLE, 100, 0, 1000,0,0,0,0,0);
        RotationInterpolator minArrRotation2 = new RotationInterpolator(minRotationAlpha2, tgLeg1, minArrowRotationAxis2, (float)Math.PI/6, -(float)Math.PI/6); //опис руху стрілки
        BoundingSphere bounds2 = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        minArrRotation2.setSchedulingBounds(bounds2);
        //
        TransformGroup tgLeg3 = new TransformGroup();
        Transform3D tCrawl = new Transform3D();
        tCrawl.rotY(-Math.PI/2);
        long crawlTime = 10000;//час, за який тарган переповзе екран
        Alpha crawlAlpha = new Alpha(1, Alpha.INCREASING_ENABLE, 0, 0, crawlTime,0,0,0,0,0);
        float crawlDistance = 9.0f; //відстань, на яку просунеться об’єкт
        PositionInterpolator posICrawl = new PositionInterpolator(crawlAlpha,
                tgLeg3,tCrawl, -9.0f, crawlDistance);
        BoundingSphere bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        posICrawl.setSchedulingBounds(bs);
        //
        tgLeg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgLeg1.addChild(minArrRotation2);
        tgLeg3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgLeg3.addChild(posICrawl);
        tgLeg3.addChild(tgLeg1);
        tgTrainer.addChild(tgLeg3);
        //tgTrainer.addChild(tgLeg1);
        //////////////////////////////////////////////////////////
        Shape3D rleg = (Shape3D) trainerNamedObjects.get("right_leg");
        Appearance legApp2 = new Appearance();
        setToMyDefaultAppearance(legApp2, new Color3f(0.2f, 0.6f, 0.2f));
        rleg.setAppearance(legApp2);

        TransformGroup tgRLeg1 = new TransformGroup();
        tgRLeg1.addChild(rleg.cloneTree());

        Transform3D minArrowRotationAxis3 = new Transform3D();
        minArrowRotationAxis3.rotZ(Math.PI/2);
        Alpha minRotationAlpha3 = new Alpha(-1, Alpha.INCREASING_ENABLE, 100, 0, 1000,0,0,0,0,0);
        RotationInterpolator minArrRotation3 = new RotationInterpolator(minRotationAlpha3, tgRLeg1, minArrowRotationAxis3, -(float)Math.PI/6, (float)Math.PI/6); //опис руху стрілки
        BoundingSphere bounds3 = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        minArrRotation3.setSchedulingBounds(bounds3);
        //
        TransformGroup tgRLeg3 = new TransformGroup();
        Transform3D tCrawlR = new Transform3D();
        tCrawlR.rotY(-Math.PI/2);
        long crawlTimeR = 10000;//час, за який тарган переповзе екран
        Alpha crawlAlphaR = new Alpha(1, Alpha.INCREASING_ENABLE, 0, 0, crawlTimeR,0,0,0,0,0);
        float crawlDistanceR = 9.0f; //відстань, на яку просунеться об’єкт
        PositionInterpolator posICrawlR = new PositionInterpolator(crawlAlphaR,
                tgRLeg3,tCrawlR, -9.0f, crawlDistanceR);
        BoundingSphere bsR = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        posICrawlR.setSchedulingBounds(bsR);
        //
        tgRLeg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgRLeg1.addChild(minArrRotation3);
        tgRLeg3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgRLeg3.addChild(posICrawlR);
        tgRLeg3.addChild(tgRLeg1);
        tgTrainer.addChild(tgRLeg3);

        //////////////////////////////////////////////////////////
        Shape3D ball1 = (Shape3D) trainerNamedObjects.get("right_hand");
        Appearance ballApp1 = new Appearance();
        setToMyDefaultAppearance(ballApp1, new Color3f(0.2f, 0.6f, 0.2f));
        ball1.setAppearance(ballApp1);

        TransformGroup tgRH = new TransformGroup();
        Transform3D tCrawlRH = new Transform3D();
        tCrawlRH.rotY(-Math.PI/2);
        long crawlTimeRH = 10000;//час, за який тарган переповзе екран
        Alpha crawlAlphaRH = new Alpha(1, Alpha.INCREASING_ENABLE, 0, 0, crawlTimeRH,0,0,0,0,0);
        float crawlDistanceRH = 9.0f; //відстань, на яку просунеться об’єкт
        PositionInterpolator posICrawlRH = new PositionInterpolator(crawlAlphaRH,
                tgRH,tCrawlRH, -9.0f, crawlDistanceRH);
        BoundingSphere bsRH = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        posICrawlRH.setSchedulingBounds(bsRH);

        TransformGroup tgBall1 = new TransformGroup();
        tgBall1.addChild(ball1.cloneTree());

        Transform3D minArrowRotationAxis1 = new Transform3D();
        minArrowRotationAxis1.rotZ(Math.PI/2);
        Alpha minRotationAlpha1 = new Alpha(-1, Alpha.INCREASING_ENABLE, 1000, 0, 2000,0,0,0,0,0);
        RotationInterpolator minArrRotation1 = new RotationInterpolator(minRotationAlpha1, tgBall1, minArrowRotationAxis1, (float)Math.PI/3, -(float)Math.PI/3); //опис руху стрілки
        BoundingSphere bounds1 = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        minArrRotation1.setSchedulingBounds(bounds1);
        tgBall1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgBall1.addChild(minArrRotation1);
        tgRH.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgRH.addChild(posICrawlRH);
        tgRH.addChild(tgBall1);
        tgTrainer.addChild(tgRH);


        ////////////////////////////////////////
        Shape3D ball = (Shape3D) trainerNamedObjects.get("left_hand");
        Appearance ballApp = new Appearance();
        setToMyDefaultAppearance(ballApp, new Color3f(0.2f, 0.6f, 0.2f));
        ball.setAppearance(ballApp);

        TransformGroup tgLH = new TransformGroup();
        Transform3D tCrawlLH = new Transform3D();
        tCrawlLH.rotY(-Math.PI/2);
        long crawlTimeLH = 10000;//час, за який тарган переповзе екран
        Alpha crawlAlphaLH = new Alpha(1, Alpha.INCREASING_ENABLE, 0, 0, crawlTimeLH,0,0,0,0,0);
        float crawlDistanceLH = 9.0f; //відстань, на яку просунеться об’єкт
        PositionInterpolator posICrawlLH = new PositionInterpolator(crawlAlphaLH,
                tgLH,tCrawlLH, -9.0f, crawlDistanceLH);
        BoundingSphere bsLH = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        posICrawlLH.setSchedulingBounds(bsLH);

        TransformGroup tgBall = new TransformGroup();
        tgBall.addChild(ball.cloneTree());

        Transform3D minArrowRotationAxis = new Transform3D();
        minArrowRotationAxis.rotZ(Math.PI/2);
        Alpha minRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 1000, 0, 2000,0,0,0,0,0);
        RotationInterpolator minArrRotation = new RotationInterpolator(minRotationAlpha, tgBall, minArrowRotationAxis, -(float)Math.PI/3, (float)Math.PI/3); //опис руху стрілки
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        minArrRotation.setSchedulingBounds(bounds);
        tgBall.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgBall.addChild(minArrRotation);
        tgLH.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgLH.addChild(posICrawlLH);
        tgLH.addChild(tgBall);
        tgTrainer.addChild(tgLH);

        //////////////////////////////////////////


        BranchGroup theScene = new BranchGroup();
        theScene.addChild(tgTrainer);

        ////////////////////////////////////
        TextureLoader t = new TextureLoader("d://Monsters.jpg", myCanvas3D);
        Background bg = new Background(t.getImage());
        bg.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds8 = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        bg.setApplicationBounds(bounds8);
        theScene.addChild(bg);

        theScene.compile();
        simpUniv.addBranchGraph(theScene);
    }

    public static void main(String[] args)
    {
        lab6 pokemon_trainer = new lab6();
    }

    TransformGroup translate(Node node,Vector3f vector){

        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(vector);
        TransformGroup transformGroup =
                new TransformGroup();
        transformGroup.setTransform(transform3D);

        transformGroup.addChild(node);
        return transformGroup;
    }

    public static void setToMyDefaultAppearance(Appearance app, Color3f col) {
        app.setMaterial(new Material(col, col, col, col, 150.0f));
    }
}



