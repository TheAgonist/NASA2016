package screen;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class Robot {
	
	public float CurrentX;
	public float CurrentY;
	private float width;
	private float height;
	public long window;
	private int list;
	private boolean last;
	private float ScreenW;
	private float ScreenH;
	private float offX;
	private float offY;
	private boolean selected;
	
	
	public Robot(float CurrX,float CurrY,long window,float width,float height,float SH,float SW,float oX,float oY,boolean selected)
	{
		this.CurrentX = CurrX;
		this.CurrentY = CurrY;
		this.window = window;
		this.width = width;
		this.height = height;
		this.ScreenH = SH;
		this.ScreenW = SW;
		this.offX = oX;
		this.offY = oY;
		this.selected = selected;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		createList();
	}

	private void createList() {
		list = GL11.glGenLists(1);
		GL11.glTranslatef(0,0,0);
		GL11.glColor3f(0f,0f,1f);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2f(CurrentX-width/2, CurrentY-height/2);
		GL11.glVertex2f(CurrentX+width/2, CurrentY-height/2);
		GL11.glVertex2f(CurrentX, CurrentY+height/2);
		GL11.glEnd();
		//System.out.println(CurrentX + " - " + width/2 +","+CurrentY +" - " +height/2);
		Update();
	}
	public boolean Update()
	{
		boolean bound = isBound();
		if(buttonLeftClicked())
		{
			buttonDown();
			last=true;
		}
		if(selected == true)
		{
			DrawCircle(CurrentX,CurrentY-0.01f,0.06f,300) ;
		}
		if(last == true && bound && glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1)== 1 && selected == false)
		{
			System.out.println("k");
			this.selected=true;
			buttonUp();
			return true;
			
		}
		
		return false;
	}

	private void DrawCircle(float cx, float cy, float r, int num_segments) 
	{ 
		GL11.glBegin(GL11.GL_LINE_LOOP); 
		for(int ii = 0; ii < num_segments; ii++) 
		{ 
			float theta = 2.0f * 3.1415926f * ii / num_segments;//get the current angle 

			float x = (float) (r * Math.cos(theta));//calculate the x component 
			float y = (float) (r * Math.sin(theta));//calculate the y component 

			GL11.glVertex2f(x + cx, y + cy);//output vertex 

		} 
		GL11.glEnd(); 
	}

	private void buttonDown() {
		// TODO Auto-generated method stub
		
	}

	public boolean buttonUp() {
		return true;
		
	}

	private boolean buttonLeftClicked() {
		if(isBound())
		{
			if(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1)== 1)
			{
				//System.out.println("kk2");
				return true;
			}
		}
		return false;
	}

	private boolean isBound() {
		    DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
	        DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
	        glfwGetCursorPos(window, b1, b2);
	        float left = CurrentX-width/2;
	        float right= CurrentX+ width/2;
	        float top= CurrentY+ height/2;
	        float bottom = CurrentY- height/2;
	        float RealX=(float) ((ScreenW * offX/2)+b1.get(0));
	        float RealY=(float) ((ScreenH * offY/2)+(360-b2.get(0)));
	        if(RealX/(ScreenW/2) - 1 >left && RealX/(ScreenW/2) - 1 <right)
	        {      
	        	
	        	
	        	if((RealY/(ScreenH/2) - 1)>bottom && (RealY/(ScreenH/2) - 1) <top)
	        	{
	        		
	        		return true;
	        	}
	        }
			return false;
		}
	}
	
	

