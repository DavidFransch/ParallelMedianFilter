/*
 David Fransch (FRNDAV011)
 CSC2002S
 ParallelAssignment
 -Driver Class
 */
package par_ass1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class FilterInterface {

    public static final ForkJoinPool fjPool = new ForkJoinPool();
    
    static long startTime = 0;
    private static void tick() {
        startTime = System.currentTimeMillis();
    }

    private static float toc() {
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        //Command line arguments 
        String filename = args[0];
        int filterSize = Integer.parseInt(args[1]);//must be factor of 3
        String outFile = args[2];

        //Read in data
        FileReader fileRead = new FileReader(new File(filename));
        BufferedReader br = new BufferedReader(fileRead);

        String line1 = br.readLine();
        String lines = "";
        String[] values;
        int arr_size = Integer.parseInt(line1);
        double[] array = new double[arr_size + 1];//[Integer.parseInt(line1)];//filterSize;
        ArrayList tmp = new ArrayList();

        float parTime = 0;
        float seqTime = 0;

        //Fill array with unfiltered values within text file
        for (int i = 0; i < arr_size; i++)
        {
            lines = br.readLine();
            tmp.add(lines);
            values = lines.split(" ");
            array[i] = Double.parseDouble(values[1].trim());
        }
        //Create new instances
        SeqFilter sq = new SeqFilter(array, filterSize);
        
        PrintWriter pr = new PrintWriter(outFile, "UTF-8");
        
        //Minimise likelihood that garbage collector runs during execution
        System.gc();
        
        //Select sequential of parallel algorithmn
//        if (args[3].toLowerCase().equals("sequential")) 
//        {
//            tick();
//            sq.seqCompute();
//            seqTime = toc();
//            sq.getSeqArray();
//            System.out.println("Sequential time: " + seqTime);
//
//            //Write to output file
//            for (int j = 0; j < sq.getSeqArray().size(); j++) {
//                pr.println(sq.getSeqArray().get(j));
//                //System.out.println();
//            }
  
        //}else if (args[3].toLowerCase().equals("parallel")) 
        //{

            tick();
            ParFilter par = new ParFilter(array, filterSize, 0, array.length - 1);
            parTime = toc();
            fjPool.invoke(par);
            System.out.println("Parallel time: " + parTime);
            //Write to output file
            for (int k = 0; k < par.getParArray().size(); k++) {
                pr.println(par.getParArray().get(k));
           // }
        //}
//        else if (args[3].toLowerCase().equals("test")){
//            for (int p = 0;  p< 524288; p++) {
//                pr.println(tmp.get(p));
//            }
        }
        
        pr.close();
        
    }
}
