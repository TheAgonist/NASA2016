package screen;



import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.text.DecimalFormat;


public class Screen {
 
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    private GLFWMouseButtonCallback MButtonCallback;
   // private Button Sq;
    
    private float OffsetX;
    private float OffsetY;
    public int id;
    public int[][] map = {
    	    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0},
    	    {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
    	    {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
    	    {0,0,0,1,1,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0},
    	    {0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    	    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
    	};
    
    
    
    // The window handle
    private long window;
    private int WIDTH = 600;
    private int HEIGHT = 600;
    private boolean selected;
    private float a;
    private float b;
    private double c;
 
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        
        try {
            init();
            loop();
           
            // Destroy window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.free();
            }finally {
            // Terminate GLFW and free the GLFWErrorCallback
            glfwTerminate();
            errorCallback.free();
            
          
        }
    }
 
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (  !glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

 
        // Create the window
        window = glfwCreateWindow(WIDTH,HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                	glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
            }
        });
 
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
       
        glfwSetWindowPos(
            window,
            (vidmode.width() - WIDTH) / 2,
            (vidmode.height() - HEIGHT) / 2
        );
        
        glfwSetWindowSize(window, 360, 360);
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    
    public void newRobot()
    {
    	Robot nR = new Robot(0f,0f,window,0.1f,0.1f,HEIGHT,WIDTH,OffsetX,OffsetY,selected);
    	if(nR.Update())
    	{
    		selected= true;
    	}
    }
    /*public void NewButton(float i,float j)
    {

    	Button b = new Button(i, j, 0.05f, 0.05f,window,id,WIDTH,HEIGHT);
    	id++;
    
    }*/
    
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();                
        // Set the clear color
       glClearColor(0.0f, 1.0f, 0.5f, 0.0f);                    
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
       while ( !glfwWindowShouldClose(window) ) {
           glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);// clear the framebuffer
            
           glfwGetWindowUserPointer	(window	)	;
           
           DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
           DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
           glfwGetCursorPos(window, b1, b2);
           //System.out.println(b1.get(0) +"    " + b2.get(0));
           int a;
           int b;
           
           
           IntBuffer w = BufferUtils.createIntBuffer(1);
           IntBuffer h = BufferUtils.createIntBuffer(1);
           glfwGetWindowSize(window, w, h);
           int width = w.get(0);
           int height = h.get(0);
           //System.out.println(width + "  " + height);
           
           glfwSetMouseButtonCallback(window, MButtonCallback = new GLFWMouseButtonCallback(){

			@Override
			public void invoke(long window, int key, int action, int mods) {
				// TODO Auto-generated method stub
				if ( key == GLFW_MOUSE_BUTTON_2 && action == GLFW_RELEASE )
                {
					System.out.println("asd");
					c=Math.sqrt( Math.pow((WIDTH * OffsetX/2)+b1.get(0),2) + Math.pow((HEIGHT * OffsetY/2)+(360-b2.get(0)),2) );
					System.out.print("c=Math.sqrt( Math.pow(("+ WIDTH +"*" + OffsetX+ ")+ " + b1.get(0)+",2) + Math.pow((" + HEIGHT +  "*" + OffsetY +")+(360-" +b2.get(0)+"),2) );");
					System.out.println(c);
                }
			}
           });
           
           
           glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
               @Override
               public void invoke(long window, int key, int scancode, int action, int mods) {
            	  
            	   if ( key == GLFW_KEY_RIGHT && action == GLFW_RELEASE )
                   {
                	   GL11.glTranslatef(-0.1f,0f,0); // We will detect this in our rendering loop
                	   OffsetX +=0.1f;
    
                	   OffsetX=round(OffsetX,2);
                   }
                   else if ( key == GLFW_KEY_LEFT && action == GLFW_RELEASE )
                   {
                	   GL11.glTranslatef(0.1f,0f,0);
                	   OffsetX -=0.1f;
         
                	   OffsetX=round(OffsetX,2);
                   }
                   else if ( key == GLFW_KEY_UP && action == GLFW_RELEASE )
                   {
                	   GL11.glTranslatef(0,-0.1f,0);
                	   OffsetY += 0.1f;
                	
                	   OffsetY=round(OffsetY,2);
                   }
                   else if ( key == GLFW_KEY_DOWN && action == GLFW_RELEASE )
                   {
                	   GL11.glTranslatef(0f,0.1f,0);
                	   OffsetY -= 0.1f;
             
                	   OffsetY=round(OffsetY,2);
                   }
                   System.out.println("OffsetX:" + OffsetX + "  " + " OffsetY:"+OffsetY );
               
               }
           });
           
           
           /*
               DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, b1, b2);
            System.out.println("x : " + b1.get(0) + ", y = " + b2.get(0));
            
            if(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1)== 1)
            {
            	System.out.println("K!");
            }else
            	System.out.println("no K");
            */
           
           for(float i = 0;i<20;i++)
           {
           	for(float j =0;j<20;j++)
           	{
    	    	GL11.glColor3f(i/20,j/20,0f);
    	        glBegin(GL_QUADS);
    	        glVertex2f((i-0.5f)/10 -0.95f, (j-0.5f)/10 -0.95f);
    	        glVertex2f((i-0.5f)/10 -0.95f, (j+0.5f)/10 -0.95f);
    	        glVertex2f((i+0.5f)/10 -0.95f, (j+0.5f)/10 -0.95f);
    	        glVertex2f((i+0.5f)/10 -0.95f, (j-0.5f)/10 -0.95f);
    	        glEnd();
           	}
           }
           newRobot();
           
           /* for(float i = 0;i<20;i++)
            {
            	for(float j =0;j<20;j++)
            	{
            		if(map[(int) i][(int) j]==1)
            		{
            	 	NewButton(i/10 - 0.95f,j/10 -0.95f);
            		}
            		else
            		{
            			GL11.glColor3f(i/20,j/20,0f);
            	        glBegin(GL_QUADS);
            	        glVertex2f((i-0.5f)/10 -0.95f, (j-0.5f)/10 -0.95f);
            	        glVertex2f((i-0.5f)/10 -0.95f, (j+0.5f)/10 -0.95f);
            	        glVertex2f((i+0.5f)/10 -0.95f, (j+0.5f)/10 -0.95f);
            	        glVertex2f((i+0.5f)/10 -0.95f, (j-0.5f)/10 -0.95f);
            	        glEnd();
            		}
            	}
            } 
            id=0;*/
            
            
            glfwSwapBuffers(window); // swap the color buffers
    		
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
 
    public static void main(String[] args) {
        new Screen().run();
        
        
    
    }
 
}