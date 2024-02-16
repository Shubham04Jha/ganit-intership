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

public class Main {
    static String[] variables = {"a", "b", "c", "d", "f", "g", "h", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static int randomNumGen(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min+1)+min;
    }
    public static String termGen(int deg, ArrayList<Integer> arr){
        StringBuilder term = new StringBuilder("$");
        int numberOfVariables = randomNumGen(1,3);
        int remDeg = deg;
        int varIdx = randomNumGen(0,variables.length-4);
        term.append(randomNumGen(2,9));
        for(int i=1;i<=numberOfVariables;i++){
            term.append(variables[varIdx++]);
            term.append("^{");
            int tempDeg = remDeg;
            if(i == numberOfVariables){
                if(remDeg>1) term.append(remDeg);
                arr.add(tempDeg);
                term.append("}");
            }
            else if(i==numberOfVariables-1){
                tempDeg = randomNumGen(1,remDeg-1);
                if(tempDeg>1) term.append(tempDeg);
                arr.add(tempDeg);
                term.append("}");
            }
            else if(i==numberOfVariables-2){
                tempDeg = randomNumGen(1,remDeg-2);
                if(tempDeg>1) term.append(tempDeg);
                arr.add(tempDeg);
                term.append("}");
            }
            remDeg-=tempDeg;
        }
        if(remDeg!=0) System.out.println("Wrong term generated");
        term.append("$");
        return term.toString();
    }
    public static StringBuilder bracGen(ArrayList<Integer> soln ){
        if(soln.size()==1){
            return new StringBuilder("$"+soln.get(0)+"$");
        }
        int sum=0;
        StringBuilder str = new StringBuilder("$(");
        for(int t = 0;t<soln.size();t++){
            str.append(soln.get(t));
            sum+=soln.get(t);
            if(t!=soln.size()-1){
                str.append(" + ");
            }
        }
        str.append(") = "+sum+"$");
        return str;
    }

    public static void main(String args[]) throws IOException,FileNotFoundException{
        String filename = "E:/Java Internship/ExcelSheets/VESIT_030402_145_Assign1_Shubham.xlsx";     //Location where excel file is getting generated
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

        //Taking input for number of question you want to generate
        System.out.println("How many question you want to enter:-");
        Scanner sc=new Scanner(System.in);
        int mapsize,mapsizeafter;
        HashMap<String, Integer> map = new HashMap<String, Integer> ();
        int q = 200;
        for(int i =1;i<q+1;i++) {
            // Create row
            XSSFRow row = sheet1.createRow(i);
            row.createCell(0).setCellValue(i);  // sr no
            row.createCell(1).setCellValue("Text"); // q type
            row.createCell(2).setCellValue(1);   // ans type singlular in this case
            row.createCell(3).setCellValue("030402"); //topic number

            // Generate random terms to perform the operation
            ArrayList<Integer> soln1= new ArrayList<>();
            ArrayList<Integer> soln2= new ArrayList<>(); // solution assistant will be used in generating answer
            int numberOfTerms = randomNumGen(5,6);
            String[] termArr = new String[8]; // array to store the terms
            HashMap<Integer, Integer> termTracker = new HashMap<>(); // map to store the deg of polynomial generated
            int corDeg = randomNumGen(3,25); // degree for correct ans
//            HashMap<String,Integer> mp = new HashMap<>(); // a small map to ensure that term1 and term 2 are different
            String term = termGen(corDeg,soln1); // calling the termGen and getting the solution assistant initialised
//            mp.put(term,0);
            termArr[0]=term; // adding first term to the array
            while(true){
                soln2= new ArrayList<>();
                String str = termGen(corDeg,soln2);
                if(!str.equals(term)){
                    termArr[1]=str; // adding second term to array
                    break;
                }
            }
            termTracker.put(corDeg,0);
            for(int j1 = 2;j1<termArr.length;j1++){ // generating other terms with different degrees
                int deg = randomNumGen(3,25);
                if(termTracker.containsKey(deg)){
                    j1--; // ensuring no duplicate deg term
                }else{
                    termTracker.put(deg,0);
                    termArr[j1]= termGen(deg,new ArrayList<>()); // we wont need it so just passing a new one without using it anywhere below
                }
            } // adding other terms

            // generating the correct option ! easy just access the 1st 2 elements of the termArr:
            // the terms are in the format of $__$ now for option 1 we need a str = $____$ , $____$
            String Correct_ans = termArr[0]+" and "+termArr[1]+"<br>";

            // generating wrong options
            // !!! since the terms were created randomly in the first place associating any thing except the combination of correctOpt will suffice
            String wrong_ans = termArr[2]+" and "+termArr[3]+"<br>";
            String wrong_ans1 = termArr[4]+" and "+termArr[5]+"<br>";
            String wrong_ans2 = termArr[6]+" and "+termArr[7]+"<br>";

            // for the placement of terms in the question we need to rearrange the terms.
            StringBuilder questionpart2 = new StringBuilder();
            HashMap<Integer,Integer> indx = new HashMap<>(); // hashmap to mark the array jotted in the questionpart2 string
            for(int j3 = 0;j3<termArr.length;j3++){
                questionpart2.append(termArr[j3]);
                if(j3!=termArr.length-1)  questionpart2.append(" , ");

            }
            questionpart2.append(".");
//            random placement
//            questionpart2.append(".");
//            StringBuilder questionpart2 = new StringBuilder();
//            HashMap<Integer,Integer> indx = new HashMap<>(); // hashmap to mark the array jotted in the questionpart2 string
//            for(int j3 = 0;j3<termArr.length;j3++){
//                int idx = randomNumGen(0,termArr.length-1); // -1 to prevent ind outta bound
//                if(indx.containsKey(idx)){
//                    j3--;
//                }else{
//                    indx.put(idx,0);
//                    questionpart2.append(termArr[idx]);
//                    if(j3!=termArr.length-1)  questionpart2.append(" , "); // no commas at last position
//                }
//            }
//            questionpart2.append(".");

            //Generate question english
            String  Que = "Which two of the following terms are with identical degree? <br>";
            //Generate question marathi
            String Que1 = "#खाली दिलेल्या पैकी कोणत्या दोन पदांची कोटी समान आहे ?<br>";
            String Question = ""+Que+" "+Que1+"";

            row.createCell(4).setCellValue(Question);
            row.createCell(5).setCellValue(Correct_ans);
//				 row.createCell(6).setCellValue(" ");
//				 row.createCell(7).setCellValue(" ");
//				 row.createCell(8).setCellValue(" ");
            row.createCell(9).setCellValue(wrong_ans);
            row.createCell(10).setCellValue(wrong_ans1);
            row.createCell(11).setCellValue(wrong_ans2);
            row.createCell(12).setCellValue(60); // timealloted
            row.createCell(13).setCellValue(3);  // difficulty level
//				  row.createCell(14).setCellValue(" ");
            row.createCell(15).setCellValue("2022.shubham.jha@ves.ac.in");

            StringBuilder brac1 = bracGen(soln1);
            StringBuilder brac2 = bracGen(soln2);
            //Generate Solution
            String Solu ="Ans : "+ termArr[0]+" and "+termArr[1]+" By definition degree of a term is the power of the variable, if it is in one variable. But if the term contains more than one variable, then the power of the term is sum of the individual exponents (power) of all the variables in that term.<br>From the given terms, only pair of terms with same degree is "
                    +termArr[0]+" and "+termArr[1]+"<br>As degree of "+termArr[0]+" is "+brac1+" and that of "+termArr[1]+" is "+brac2+", and thus, is same for both terms. <br>"+
                    "Hence "+termArr[0]+" and  "+termArr[1]+" are the two terms with identical degree, is the answer.<br>";
            String Sol1 = "#उत्तर : "+termArr[0]+" and "+termArr[1]+"<br>पद जर एका चलातील असेल तर त्या चलाचा घातांक हाच त्या पदाची कोटी असते. पद जर एकापेक्षा अधिक चलातील असेल तर सर्व चलांच्या घातांकांची बेरीज ही त्या पदाची कोटी असते.<br> "
                    +"दिलेल्या पदांच्या जोडीमधील "+termArr[0]+" आणि "+termArr[1]+" या जोडीच्या पदांची कोटी समान आहे.<br>"+termArr[0]+" या पदाची कोटी $=$ "+brac1+" आणि  "+termArr[1]+" या पदाची कोटी "+brac2+".<br>"+
                    "म्हणून सारखी कोटी असलेली "+termArr[0]+" आणि  "+termArr[1]+" ही दोन पदे आहेत, हे उत्तर. <br>";
            String Solution = " "+Solu+" "+Sol1+" ";
            row.createCell(16).setCellValue(Solution);
//				  row.createCell(17).setCellValue(" ");
            row.createCell(18).setCellValue(145);

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