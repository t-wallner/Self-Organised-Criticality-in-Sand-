// Imports
import java.io.*;

// class for handling File Input and Output
public class FileIO{

    // variables

    // constructors 
    public FileIO()
    {
    }

    // methods
    // write method writes out results from simulation to a file, so that data
    // can be processed and analzed in microsoft excel
    public void write(int[] results) throws IOException {
    
	FileWriter outputfile = new FileWriter("AvalancheResults.txt");
	BufferedWriter out = new BufferedWriter(outputfile);

	for(int i=0;i<results.length;i++)
	    {
		out.write(String.valueOf(results[i])+"\n");
	    }
	out.close();
	outputfile.close();
    }

}