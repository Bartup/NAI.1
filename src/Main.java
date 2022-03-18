import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {

        int k = Integer.parseInt(args[0]);
        String trainSetPath = args[1];
        String testSetPath = args[2];

        List<String> trainingList = csvToList(trainSetPath);

        List<String> testList = csvToList(testSetPath);



        String preConvertTest = testList.toString();
        String preConvertTraining = trainingList.toString();


        String pCTrain1 = preConvertTraining.replace("[","");
        String pCTrain2 = pCTrain1.replace("]","");
        String pCTest1 = preConvertTest.replace("[","");
        String pCTest2 = pCTest1.replace("]","");

        String[] convertedTestList = pCTest2.split(",");
        String[] convertedTreainingList = pCTrain2.split(",");
        int sampleSize = testList.size();


        compare(convertedTestList,convertedTreainingList,k,sampleSize);


    }
    public static List<String> csvToList(String path) throws IOException {
        List<String> stringList = new ArrayList<>();
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String textLine = bufferedReader.readLine();

        do {
            stringList.add(textLine);

            textLine = bufferedReader.readLine();

        } while(textLine != null);

        bufferedReader.close();

        return stringList;
    }


    public static void compare(String[] test, String[] train, int k, int sampleSize){

        int halfOfK = k/2 + 1;
        double succesNumber = 0;


        for (int i = 0; i < test.length; i+= 5){
            List<String> neighbourName = new ArrayList<>();
            List<String> limitNeighbourName = new ArrayList<>();
            int similarity = 0;
            for(int j = 0; j < train.length; j+= 5){
                double distance = Math.sqrt(
                         Math.pow((Double.parseDouble(test[i]) - Double.parseDouble(train[j])),2.0) +
                         Math.pow((Double.parseDouble(test[i+1]) - Double.parseDouble(train[j+1])),2.0) +
                         Math.pow((Double.parseDouble(test[i+2]) - Double.parseDouble(train[j+2])),2.0) +
                         Math.pow((Double.parseDouble(test[i+3]) - Double.parseDouble(train[j+3])),2.0)
                 );
                neighbourName.add(distance + " " + train[j+4]);
                Collections.sort(neighbourName);

            }
            for(int x = 0; x < k; x++ ){
                limitNeighbourName.add(neighbourName.get(x));
            }
            for(int y = 0; y < k; y++){
                if(limitNeighbourName.get(y).contains(test[i+4])){
                    similarity++;
                }
            }
            if (similarity >= halfOfK) {
                succesNumber++;
            }

        }
        System.out.println("Skutecznosc wynosi: " + ((succesNumber * 100)/ sampleSize) + "%");
        System.out.println(succesNumber);

    }

}
