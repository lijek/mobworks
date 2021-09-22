package pl.lijek.mobworks;

import static org.lwjgl.opengl.GL11.*;

public class RenderHelper {
    public static void drawCuboid(int glMode, double minX, double minY, double minZ, double maxX, double maxY, double maxZ){
        //top
        glBegin(glMode);
        glVertex3d(minX, minY, minZ);
        glVertex3d(maxX, minY, minZ);
        glVertex3d(maxX, minY, maxZ);
        glVertex3d(minX, minY, maxZ);
        glEnd();
        //bottom
        glBegin(glMode);
        glVertex3d(minX, maxY, minZ);
        glVertex3d(maxX, maxY, minZ);
        glVertex3d(maxX, maxY, maxZ);
        glVertex3d(minX, maxY, maxZ);
        glEnd();
        //front
        glBegin(glMode);
        glVertex3d(minX, minY, minZ);
        glVertex3d(maxX, minY, minZ);
        glVertex3d(maxX, maxY, minZ);
        glVertex3d(minX, maxY, minZ);
        glEnd();
        //back
        glBegin(glMode);
        glVertex3d(minX, minY, maxZ);
        glVertex3d(maxX, minY, maxZ);
        glVertex3d(maxX, maxY, maxZ);
        glVertex3d(minX, maxY, maxZ);
        glEnd();
        //right
        glBegin(glMode);
        glVertex3d(minX, minY, minZ);
        glVertex3d(minX, maxY, minZ);
        glVertex3d(minX, maxY, maxZ);
        glVertex3d(minX, minY, maxZ);
        glEnd();
        //left
        glBegin(glMode);
        glVertex3d(maxX, minY, minZ);
        glVertex3d(maxX, maxY, minZ);
        glVertex3d(maxX, maxY, maxZ);
        glVertex3d(maxX, minY, maxZ);
        glEnd();
    }
}
