/*
  David Fransch (FRNDAV011)
 CSC2002S
 ParallelAssignment
-Parallel Median Filter
 */
package par_ass1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.RecursiveAction;


public class ParFilter extends RecursiveAction {

    int filterSize;
    int filterAdjust;
    int medianPos;
    //double[] cleanArray = new double[10];//filter size
    ArrayList cleanArray = new ArrayList();
    double[] noiseArray;

    static int seq_thresh = 10000000;
    int lo;
    int hi;

    //Parallel Constructor
    public ParFilter(double[] oldArray, int filterSize, int l, int h) {
        this.lo =l;
        this.hi = h;
        noiseArray = oldArray;
        this.filterSize = filterSize;
        filterAdjust = noiseArray.length - this.filterSize; //might be len -1
        medianPos = (this.filterSize - 1) / 2;
        
    }
    //Method does computation in parallel until sequential threshold is reached
    protected void compute()
    {
        if (hi - lo <= seq_thresh) 
        {
            for (int i = 0; i < medianPos; i++) {
                cleanArray.add(noiseArray[i]);
            }
            //Populate temporary array with filtered values
            for (int j = 0; j <= filterAdjust; j++) {//<=
                ArrayList temp = new ArrayList();

                for (int k = 0; k < filterSize; k++) {
                    temp.add(noiseArray[j + k]);
                }

                Collections.sort(temp);
                Object medianVal = temp.get(medianPos);
                cleanArray.add(medianVal);
            }
            //Populate cleanArray from right to left with data values

            for (int m = cleanArray.size() - medianPos; m < noiseArray.length; m++) {
                //System.out.println(noiseArray[m]);
                cleanArray.add(noiseArray[m]);
            }
        }
        else{
             ParFilter left = new ParFilter(noiseArray, filterSize, lo, (hi + lo) / 2);
             ParFilter right = new ParFilter(noiseArray, filterSize, (hi + lo) / 2, hi);
             left.fork();
             right.compute();
             left.join();
        }
    }
    
     public ArrayList getParArray() {
//         System.out.println("size:"+ cleanArray.size());
//         for(int l = 0; l < cleanArray.size(); l++){
//           System.out.println(cleanArray.get(l));
//        }
        return cleanArray;
    }
}
