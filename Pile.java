// Imports
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.image.BufferedImage;

// pile class - creates 2d array of sand piles, then offers various methods for simulation
public class Pile {
    
    // Variables
    private int n; // length & width of array
    private int[][] sand; // 2D array of sand pile
    // Record D the frequency of avalanche of size s
    private int[] sData=new int[10000]; // data recording - 
    private int[] sResult; // processed data
    private int sCount;// keeps count of frequency of size of avalachnes
    private int critical; // user defined critical value
    private double stickiness;// user defined, probability of avalanche occuring at critical value
    private int clumpSize; // user defined, the width/length of clump that is added
    private Graphics graphics;
    private Viewer viewer;
    
    // Constructors    
    Random random=new Random();
    public Pile (int n, int critical,double stickiness,int clumpSize,Viewer viewer)
    {
	this.n=n;
	this.critical=critical;
	this.stickiness=stickiness;
	this.clumpSize=clumpSize;
	this.viewer=viewer;
	this.graphics=viewer.getBI().getGraphics();
	sand = new int[n][n];
	fillPile();	
    }
    
    // Methods

    // updatePile -this method adds a random grain of sand. Given a pile reaches its  critical value, this method
    // calls on the handleCritical method. It also records data on the frequency of avalanche sizes,
    public void updatePile(int maxIterations)
    {
	int iterations=0;
	int c=0;
	while(iterations<maxIterations)
	    {
		sCount=0;
		int addSandRow=random.nextInt(n);
		int addSandColumn= random.nextInt(n);
		sand[addSandRow][addSandColumn]++; // add sand grain to random position
		if(isCritical(addSandRow,addSandColumn)==true)
		    {
			if(random.nextDouble()<=stickiness)// add stickiness to model
			    {
				// call on handle critical method if avalanche occurs
				handleCritical(addSandRow,addSandColumn);
			    }
		    }
		draw(); // update the graphical user interface
		if(sCount!=0)
		    {// keep track of the frequency of avalanche sizes
			sData[c+1]=sCount;
			c++;
		    }
		iterations++;
	    }
	
    }

    // handel Critical - this method handles the event of a critical value. The method implements the rules that are
    // outlines in the model section of the report
    public void handleCritical(int i,int j)
    {
	int m=0;
	int[][] sandNext = new int[n][n];
	sandNext=sand;
	sCount++;
	sandNext[i][j]=sand[i][j]-4;// subtract 4 from critical pile
	// add 1 to adjacent piles if they exist
	// like add sand on plate, if critical is at end of plate, 
	// then sand falls of plate, and is lost from the system (being the plate)
	if(i<(n-1))
	    {
		sandNext[i+1][j]=sand[i+1][j]+1;
		if(isCritical((i+1),j)==true)
		    {
			if(random.nextDouble()<=stickiness)
			    {
				handleCritical((i+1),j);
			    }
		    }
	    }
	if(i>0)
	    {
	       	sandNext[i-1][j]=sand[i-1][j]+1;
		if(isCritical((i-1),j)==true)
		    {
			if(random.nextDouble()<=stickiness)
			    {
				handleCritical((i-1),j);
			    }
		    }
	    }
	if(j<(n-1))
	    {
		sandNext[i][j+1]=sand[i][j+1]+1;
		if(isCritical(i,(j+1))==true)
		    {
		       	if(random.nextDouble()<=stickiness)
			    {
				handleCritical(i,(j+1));
			    }
		    }
	    }
	if(j>0)
	    {
		sandNext[i][j-1]=sand[i][j-1]+1;
		if(isCritical(i,(j-1))==true)
		    {
			if(random.nextDouble()<=stickiness)
			    {
				handleCritical(i,(j-1));
			    }
		    }
	    }
	sand=sandNext;
	
    }
    
    // fillPile - fills the grid of piles with a randomly generated number of sand grains, between 0 
    // and the critical value
    public void fillPile()
    {
	for(int i=0;i<n;i++)
	    {
		for(int j=0;j<n;j++)
		    {
			// generate random value between 0 and critical
			sand[i][j]=random.nextInt(critical);
		    }
	    }
    }
    
    // draw - this method is responsible for the visualizing the size of a sand pile using HSBColor
    public void draw()
    {
	for(int i=0;i<n;i++)
	    {
		for(int j=0;j<n;j++)
		    {
			float h,s,b;
			h=0.0f;
			s=1.0f;
			b=1.0f;
		
			h=(float)sand[i][j]/(float)critical;
			b=(float)sand[i][j]/(float)critical;
			// apply a color depending on how close to value is to critical
			graphics.setColor(Color.getHSBColor(h,s,b));
			// draw a rectangle, each representing one sand pile
			graphics.fillRect(j*20,i*20,20,20);
		    }
	    }
	viewer.drawImage();
	try {
	    Thread.sleep(1);
	    } catch (InterruptedException e) {
		return;
	    }

    }

    // isCritical - this method has parameters i,j the row and column of the sand pile of interest
    // it then returns if the sand pile of interest is equal or above the critical value
    public boolean isCritical(int i, int j)
    {
	boolean test=false;
	if(sand[i][j]>=critical)
	{
	    test=true;
	}
	return test;
    }

    // print - this method prints out the current arrangement of the pile
    // print was used during the coding stage to catch onto errors
    public void print()
    {
	for(int i=0;i<n;i++)
	    {
		for(int j=0;j<n;j++)
		    {
			System.out.print(sand[i][j]+" ");
		    }
		System.out.println();
	    }
    }

    // maxValue - this method simply finds the maximum value in sData
    // essentially it finds the maximum size of an avalanche that occured during simulation
    public int sMaxValue()
    {
	int max=sData[0];
	for(int i=1;i<sData.length;i++)
	    {
		if(sData[i]>max)
		    {
			max=sData[i];
		    }
	    }
	return max;
    }

    // sProcess - this method processes the raw data collected during simulation
    // It arranges the data in such a way that the 1st position of the output corresponds
    // to the number of times an avalanche of size 1 occured. So the the 2nd position gives
    // the number of times an avalanches of size 2 occured etc.
    public int[] sProcess()
    {
	// the size of the processed data is precidely the max value of the raw data
	sResult=new int[sMaxValue()];
	System.out.println("MAX VALUE: "+sMaxValue());
	int size=1;
	boolean stop=false;
	while(stop==false)
	    {
		int amount=0;
		for(int i=0;i<10000;i++)
		    {
			if(sData[i]==size)
			    {
				amount++;
			    }
		    }
		sResult[size-1]=amount;
		if(size==sMaxValue())
		    {
			stop=true;
		    }
		size++;
	    }
	return sResult;
    }

    // Accessors and Mutators
    public int getN()
    {
	return n;
    }
    public void setN(int n)
    {
	n=n;
    }
    public int getCritical()
    {
	return critical;
    }
    public void setCritical(int c)
    {
	critical=c;
    }
    public int getSand(int i,int j)
    {
	return sand[i][j];
    }
    public void setSand(int i,int j, int value)
    {
	sand[i][j]=value;
    }
    public int[] getSData()
    {
	return sData;
    }


}