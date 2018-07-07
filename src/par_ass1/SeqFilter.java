/*
David Fransch (FRNDAV011)
CSC2002S
ParallelAssignment
-Sequential Median Filter
 */
package par_ass1;


import java.util.ArrayList;
import java.util.Collections;

public class SeqFilter {
     //Instance Variables
    int filterSize;
    int filterAdjust;
    int medianPos;
    //double[] cleanArray = new double[10];//filter size
    ArrayList cleanArray = new ArrayList();
    double[] noiseArray;

    //Constructor
    public SeqFilter(double[] oldArray, int filterSize) {
        noiseArray = oldArray;
        this.filterSize = filterSize;
        filterAdjust = noiseArray.length - this.filterSize; //might be len -1
        medianPos = (this.filterSize - 1) / 2;
    }
    //Sequential median filter
    public void seqCompute() {
        //Populate cleanArray from left to right with unfiltered values
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
        for (int m = cleanArray.size() - medianPos; m < noiseArray.length; m++) {
                cleanArray.add(noiseArray[m]);
            }

    }
    //Returns filtered array
    public ArrayList getSeqArray(){
//        for(int l = 0; l < cleanArray.size(); l++){
//            //System.out.println("Unfiltered: "+ noiseArray[l]);
//           System.out.println(cleanArray.get(l));
//        }
        return cleanArray;
        
    }
}
