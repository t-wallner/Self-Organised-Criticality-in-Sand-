// Computer Simulation Project: Self-organized criticality of sand
// author: thomas wallner
// student number: 1227490

// Imports
import java.io.*;

// main class 
public class Avalanche {

    public static void main (String agrs[]) throws IOException{

	// create graphical user interface for visualization 
	Viewer viewer= new Viewer();	
	viewer.setSize(500,526);
	viewer.setVisible(true);
	
	// create pile object for simulation
	// size of grid, critical value and clumpiness are user defined
	Pile p= new Pile(25,5,1.0,1,viewer);
	// start simulation for n iterations, also user defined
	p.updatePile(10000);

	// Process output data in a convenient format for excel
	int[] sResult=p.sProcess();
	// Write results to Avalanche results file
	FileIO io= new FileIO();
	io.write(sResult);
    }
}