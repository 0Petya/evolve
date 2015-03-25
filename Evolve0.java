import java.util.Scanner;
import java.util.Arrays;
import java.io.*;

public class Evolve0
{
	public static void main(String[] args)
	{
		try
		{
			// begin by putting all Files in the current directory in an array
			File f = new File(".");
			File[] files = f.listFiles();
    		int N = 0;

    		// figure out how many Files start with "Evolve"
    		// and end in ".java"
    		// those Files are the actual Evolve Files that we are creating
			for (int i = 0; i < files.length; i++)
    		{
    			String s = files[i].toString();
    			if (s.substring(2, 8).equals("Evolve"))
    				if (s.substring(s.length() - 5, s.length()).equals(".java"))
    					N++;
    		}

    		// create an array to store the iteration associated with the Evolve File
    		int family[] = new int[N];
    		int j = 0;

			// store all those iterations in an array
    		for (int i = 0; i < files.length; i++)
    		{
    			String s = files[i].toString();
    			if (s.substring(2, 8).equals("Evolve"))
    			{
    				if (s.substring(s.length() - 4, s.length()).equals("java"))
    				{
    					int count = Integer.parseInt(s.substring(8, s.lastIndexOf(".")));

    					family[j] = count;
    					j++;
    				}
    			}
    		}

    		// sort the array so that the most current iteration is the last one
    		// AKA this iteration
    		Arrays.sort(family);
			int iteration = family[N - 1];



			// creates a File object using the name of the current iteration
			// then creates a new File object for the next iteration
			File file = new File("Evolve" + iteration + ".java");
			iteration++;
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Evolve" + iteration + ".java")));

			// begins scanning this entire File
			// and writes it to the next File
			Scanner in = new Scanner(file);
			while (in.hasNextLine())
			{
				String s = in.nextLine();

				// edit the class name of the next File
				if (s.equals("public class Evolve" + (iteration - 1)))
					s = s.substring(0, s.length() - (("" + (iteration - 1)).length())).concat("" + iteration);

				out.println(s);
			}

			out.close();
			in.close();

			// now send commands to the command line to compile and run the next file
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("javac Evolve" + iteration + ".java").waitFor();
      		runtime.exec("java Evolve" + iteration);
		}
		catch (Exception e) {System.out.println("FAILED");}
	}
}
