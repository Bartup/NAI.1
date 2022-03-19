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

        System.out.println("Czy chcesz podać nowy obiekt do sprawdzenia klasyfikacji? (tak/nie)");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        if(answer.equals("tak")){
            newTest(convertedTreainingList);
            System.out.println("Kontunuowac dodawanie obiektow do klasyfikacji? (tak/nie)");
            String answerNext = scanner.nextLine();

            while( answerNext.equals("tak")){
                newTest(convertedTreainingList);
                System.out.println("Kontunuowac dodawanie obiektow do klasyfikacji? (tak/nie)");
                answerNext = scanner.nextLine();
            }

        }
        System.out.println("Program zakonczony");



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

    public static void newTest(String[] train){
        List<String> neighborName = new ArrayList<>();
        List<String> limitNeighborName = new ArrayList<>();
        List<Integer> flower = new ArrayList<>();
        int irisSetosa=0;
        int irisVersicolor = 0;
        int irisVirginica =0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Prosze podac pierwszy atrybut");
        double a1 = scanner.nextDouble();
        System.out.println("Prosze podac drugi atrybut");
        double a2 = scanner.nextDouble();
        System.out.println("Prosze podac trzeci atrybut");
        double a3 = scanner.nextDouble();
        System.out.println("Prosze podac czwarty atrybut");
        double a4 = scanner.nextDouble();
        System.out.println("Prosze podac liczbe k");
        int k = scanner.nextInt();

        for(int i = 0; i < train.length; i += 5){
            double distance = Math.sqrt(
                    Math.pow(a1 - Double.parseDouble(train[i]), 2.0) +
                    Math.pow(a2 - Double.parseDouble(train[i + 1]), 2.0) +
                    Math.pow(a3 - Double.parseDouble(train[i + 2]), 2.0) +
                    Math.pow(a4 - Double.parseDouble(train[i + 3]), 2.0)
            );
            neighborName.add(distance + " " + train[i + 4]);
            Collections.sort(neighborName);
        }
        for(int i = 0; i <k; i++) {
            limitNeighborName.add(neighborName.get(i));
        }
        for(int i = 0; i<k; i++) {
            if(limitNeighborName.get(i).contains("Iris-setosa")) {
                irisSetosa++;
            }
            else if(limitNeighborName.get(i).contains("Iris-versicolor")) {
                irisVersicolor++;
            }
            else if(limitNeighborName.get(i).contains("Iris-virginica")) {
                irisVirginica++;
            }
        }
        flower.add(irisSetosa);
        flower.add(irisVersicolor);
        flower.add(irisVirginica);

        if(irisSetosa > irisVersicolor && irisSetosa > irisVirginica) {
            System.out.println("Wynik przydziału: iris-setosa");
        }else if(irisVersicolor > irisSetosa && irisVersicolor > irisVirginica) {
            System.out.println("Wynik przydziału: iris-versicolor");
        }else {
            System.out.println("Wynik przydziału: iris-virginica");
        }
    }

}
