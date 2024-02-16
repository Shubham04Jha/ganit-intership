import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Ass2 {
    static String[][] variables = {{"a","b","c"},{"p","q","r"},{"x","y","z"},{"l","m","n"},{"u","v","w"},{"r","s","t"},{"b","c","d"},{"f","g","h"}};
    static int max_power =8,min_power = 1,max_coeff = 21, min_coeff = 1;
    private static int randomNumGen(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min+1)+min;
    }
    public static String termGen(int deg, ArrayList<Integer> arr,int coefficient){
        StringBuilder term = new StringBuilder("$");
        int numberOfVariables = randomNumGen(1,3);
        if(deg ==1) numberOfVariables =1;
        if(deg==2) numberOfVariables =2;
        int remDeg = deg;
        int varIdx = randomNumGen(0,variables.length-1);
        int varInnerIdx=0;
        if(numberOfVariables!=3) varInnerIdx = randomNumGen(0,3-numberOfVariables);
        if(coefficient!=1) term.append(coefficient);
        for(int i=1;i<=numberOfVariables;i++){
            term.append(variables[varIdx][varInnerIdx++]);
            term.append("^{");
            int tempDeg = remDeg;
            if(i == numberOfVariables){
                helperOfTermGen(term,arr,tempDeg);
            }
            else if(i==numberOfVariables-1){
                tempDeg = randomNumGen(1,remDeg-1);
                helperOfTermGen(term,arr,tempDeg);
            }
            else if(i==numberOfVariables-2){
                tempDeg = randomNumGen(1,remDeg-2);
                helperOfTermGen(term,arr,tempDeg);
            }
            remDeg-=tempDeg;
        }
        if(remDeg!=0) System.out.println("Wrong term generated");
        term.append("$");
        return term.toString();
    }
    private static void helperOfTermGen(StringBuilder term,ArrayList<Integer> arr,int tempDeg){
        if(tempDeg>1) {
            term.append(tempDeg);
            term.append("}");
            arr.add(tempDeg);
        }
        else if(tempDeg == 1){
            term.deleteCharAt(term.length()-1); // deleting {
            term.deleteCharAt(term.length()-1); // deleting ^
            arr.add(tempDeg);  // adding power 1 in the arr to use in brac genrator
        }
    }
    public static StringBuilder powerAdder(ArrayList<Integer> individualPowers ){
        int sum=0;
        StringBuilder str = new StringBuilder("$= ");
        for(int t = 0;t<individualPowers.size();t++){
            str.append(individualPowers.get(t));
            sum+=individualPowers.get(t);
            if(t!=individualPowers.size()-1){
                str.append(" + ");
            }
        }
        if(individualPowers.size()!=1)
        str.append(" = "+sum);
        str.append("$");
        return str;
    }
    // variation no 146
    // topic number 030402
    //DOD = 3, time alloted = 60, ans type 1;
    static int variation_no = 146,difficulty_level = 3, time_alloted = 60, anstype =1;
    static String topic_no = "030402",questiontype="Text";

    public static void main(String args[]) throws IOException,FileNotFoundException{
        String filename = "E:/Java Internship/ExcelSheets/VESIT_030402_146_Assign2_Shubham.xlsx";     //Location where excel file is getting generated
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Instruction");      //Generating first sheet as Instruction

        XSSFSheet sheet1 = workbook.createSheet("Questions");       //Generating second sheet as Questions

        //Adding first row in second sheet(Questions)
        String[] header = {"Sr. No","Question Type","Answer Type","Topic Number","Question (Text Only)","Correct Answer 1","Correct Answer 2",
                "Correct Answer 3","Correct Answer 4","Wrong Answer 1","Wrong Answer 2","Wrong Answer 3","Time in seconds","Difficulty Level",
                "Question (Image/ Audio/ Video)","Contributor's Registered mailId","Solution (Text Only)","Solution (Image/ Audio/ Video)","Variation Number"};
        XSSFRow rowhead = sheet1.createRow((short)0);

        //Set height and width to the column and row
        sheet1.setColumnWidth(4, 35*250);
        sheet1.setColumnWidth(16, 45*250);

        //Adding header to the second sheet
        for(int head=0; head<header.length; head++) {
            rowhead.createCell(head).setCellValue(header[head]);

        }
        int mapsize,mapsizeafter;
        HashMap<String, Integer> map = new HashMap<String, Integer> ();
        int q = 200;
        for(int i =1;i<q+1;i++) {
            // Create row
            XSSFRow row = sheet1.createRow(i);
            row.createCell(0).setCellValue(i);  // sr no
            row.createCell(1).setCellValue(questiontype); // q type
            row.createCell(2).setCellValue(anstype);   // ans type singular in this case
            row.createCell(3).setCellValue(topic_no); //topic number

            // Generate random terms to perform the operation
            ArrayList<Integer> soln1= new ArrayList<>();
            String[] termArr = new String[4]; // array to store the terms
            HashMap<Integer,Integer> coefficient_Tracker = new HashMap<>(); // for different coefficient
            int oddDeg = randomNumGen(min_power,max_power); // degree for correct ans
            int coeff1 = randomNumGen(min_coeff,max_coeff);
            termArr[0]=termGen(oddDeg,soln1,coeff1); // adding first term to the array
            coefficient_Tracker.put(coeff1,0);
            int deg = 3;
            while(true){
                deg = randomNumGen(min_power,max_power);
                if(deg!=oddDeg) break;
            } // deg other than oddDeg
            HashMap<String,Integer> notOddTermTracker = new HashMap<String, Integer>();

            for(int j1 = 1;j1<termArr.length;j1++){ // generating other terms with same degree other than the corDeg
                int size_of_termTrackerbefore = notOddTermTracker.size();
                int coeff2 = randomNumGen(min_coeff,max_coeff);
                if(coefficient_Tracker.containsKey(coeff2)) j1--;
                else {
                    termArr[j1] = termGen(deg, new ArrayList<>(),coeff2);
                    notOddTermTracker.put(termArr[j1], deg);
                    if (size_of_termTrackerbefore == notOddTermTracker.size())
                        j1--;  // size didn't increase means duplicate was added.
                }
            } // adding other terms

            // generating the correct option ! easy just access the 1st 2 elements of the termArr:
            String Correct_ans = termArr[0]+"<br>";

            // generating wrong options
            String wrong_ans = termArr[1]+"<br>";
            String wrong_ans1 = termArr[2]+"<br>";
            String wrong_ans2 = termArr[3]+"<br>";

            //Generate question english
            String  Que = "Identify the odd man out, as regards to the degree of the terms given <br>";
            //Generate question marathi
            String Que1 = "# पदांच्या कोटीवर आधारित कसोटी नुसार खाली दिलेल्या पैकी विसंगत पद ओळखा.<br>";
            String Question = ""+Que+" "+Que1+"";

            row.createCell(4).setCellValue(Question);

            row.createCell(5).setCellValue(Correct_ans);

            row.createCell(9).setCellValue(wrong_ans);
            row.createCell(10).setCellValue(wrong_ans1);
            row.createCell(11).setCellValue(wrong_ans2);

            row.createCell(12).setCellValue(time_alloted); // timealloted
            row.createCell(13).setCellValue(difficulty_level);  // difficulty level
            row.createCell(15).setCellValue("2022.shubham.jha@ves.ac.in");

            StringBuilder power_addition_in_soln = powerAdder(soln1);
            //Generate Solution
            String Solu ="Ans : "+ termArr[0]+"<br>By definition degree of a term in one variable, is the power of that variable. With more than one variable, the degree is the sum of the exponents of each variable.<br>"
                    +" Of the given terms, each term has degree $"+deg+"$, whereas only the term "+termArr[0]+" has degree "+power_addition_in_soln+" which is different than $"+deg+"$.<br> "+
                    "Hence the term "+termArr[0]+" is the 'odd man out'.<br>";
            String Sol1 = "#उत्तर : "+termArr[0]+"<br>पद जर एका चलातील असेल तर त्या चलाचा घातांक हाच त्या पदाची कोटी असते."+
                    " पद जर एकापेक्षा अधिक चलातील असेल तर सर्व चलांच्या घातांकांची बेरीज ही त्या पदाची कोटी असते.<br>"+
                    "दिलेल्या पदांपैकी सर्व पदांची कोटी $"+deg+"$ आहे. फक्त "+termArr[0]+" या पदाची कोटी "+power_addition_in_soln+" आहे."+
                    " म्हणजेच ती $"+deg+"$ पेक्षा वेगळी आहे.<br>"+" म्हणून दिलेल्या पदांच्या कोटीवर आधारित कसोटी नुसार "+termArr[0]+" हे पद विसंगत आहे.<br>";
            String Solution = " "+Solu+" "+Sol1+" ";
            StringBuilder questionpart2 = new StringBuilder();
            for(int iterator=0;iterator<termArr.length;iterator++){
                questionpart2.append(termArr[iterator]);
            }
            row.createCell(16).setCellValue(Solution);
            row.createCell(18).setCellValue(variation_no);

            mapsize = map.size();
            map.put(questionpart2.toString(), i);
            mapsizeafter = map.size();

            //In Java, a map can consist of virtually any number of key-value pairs, but the keys must always be unique — non-repeating.
            if(mapsize == mapsizeafter) {
                System.out.println("duplicate Question"+i+". " + Question);
                i--;
            }

        }


        int rowTotal = sheet1.getLastRowNum();
//			  System.out.println(rowTotal);
        XSSFRow row = sheet1.createRow((short)rowTotal+1);
        row.createCell(0).setCellValue("****");

        //Writing data to the file
        FileOutputStream fileout = new FileOutputStream(filename);
        workbook.write(fileout);
        fileout.close();

        System.out.println("file created");

    }

}