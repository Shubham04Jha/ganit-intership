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
public class Ass3 {
    static String[] parameterVariables = {"x", "y", "z","t","r"};
    static String[][] functionNames2DArray = {
            {"a", "b", "c", "d","f", "g", "h"},
            {"f", "g", "h","u", "v"},
            { "p", "q","u", "v","w"},
            {"m", "n","p", "q"}
    };

    // variation no 146
    // topic number 030402
    //DOD = 3, time alloted = 60, ans type 1;
    static int variation_no = 148,difficulty_level = 3, time_alloted = 60, anstype =1;
    static String topic_no = "030402",questiontype="Text";
    private static int randomNumGen(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min+1)+min;
    }

    static String polyGen(String varNum, int rindx,int cindx){
        return "$"+functionNames2DArray[rindx][cindx]+"("+varNum.charAt(1)+")"+"$";
    }
//
    static String varGen(int idx){
        return "$"+parameterVariables[idx]+"$";
    }

    static int otherThanThis(int n,int min, int max){
        int n2;
        do{
            n2 = randomNumGen(min,max);
        }while(n==n2);
        return n2;
    }

    public static void main(String[] args) throws IOException {
        String filename = "E:/Java Internship/ExcelSheets/VESIT_030402_148_Assign3_Shubham.xlsx";     //Location where excel file is getting generated
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


            String varNum = varGen(randomNumGen(0,parameterVariables.length-1)) ;
            int ridx = randomNumGen(0,functionNames2DArray.length-1);
            int cidx = randomNumGen(0,functionNames2DArray[ridx].length-1-1);
//
//            String poly1= polyGen2(varNum,idx);
//            idx = otherThanThis(idx,0,variables.length);
//            String poly2 = polyGen2(varNum,idx);
            String poly1= polyGen(varNum,ridx,cidx);
            String poly2 = polyGen(varNum,ridx,randomNumGen(cidx+1,functionNames2DArray[ridx].length-1));
            String Correct_ans ="Nothing can be said<br># सांगता येत नाही<br>";
            // generating wrong options
            String wrong_ans = "True <br># बरोबर<br>";
            String wrong_ans1 = "False <br>#चूक<br>";
            String wrong_ans2 = "All of the above<br>#दिलेले सर्व बरोबर <br>";

            //Generate question english
            String  Que = "For given value of "+varNum+", if value of algebraic expression "+poly1+" is greater than the value of algebraic expression "+poly2+", then degree of algebraic expression "+poly1+" is greater than degree of algebraic expression "+poly2+" <br>";
            //Generate question marathi
            String Que1 = "#"+varNum+ " या चलाच्या दिलेल्या किमतीसाठी जर "+poly1+" या बैजिक राशीचे मूल्य "+poly2+" या बैजिक राशीपेक्षा जास्त असेल तर  "+poly1+" या बैजिक राशीची कोटी "+poly2+" या बैजिक राशीपेक्षा जास्त असते. <br>";
            String Question = Que+" "+Que1;

            row.createCell(4).setCellValue(Question);

            row.createCell(5).setCellValue(Correct_ans);

            row.createCell(9).setCellValue(wrong_ans);
            row.createCell(10).setCellValue(wrong_ans1);
            row.createCell(11).setCellValue(wrong_ans2);

            row.createCell(12).setCellValue(time_alloted); // timealloted
            row.createCell(13).setCellValue(difficulty_level);  // difficulty level
            row.createCell(15).setCellValue("2022.shubham.jha@ves.ac.in");


            //Generate Solution
            String Solu ="Ans: Nothing can be said<br>"+
                    "For the given value of variable "+varNum+", value of a polynomial depends on "+
                    "value of  coefficients and powers of the variables in the polynomial."+
                    " It does not depend solely on degree of given polynomial."+
                    " So we can not compare values of polynomials "+poly1+" and "+poly2+" based on the degree."+
                    " Two types of polynomials can have same or different values. So \"Nothing can be said\" is the correct answer.<br>";
            String Sol1 = "#उत्तर : सांगता येत नाही <br> "+
                    varNum+" या चलाच्या दिलेल्या किमतीसाठी बैजिक राशीचे मूल्य त्यातील स्थिरांकांवर आणि चलाच्या घातांकांवर अवलंबून असते."+
                    " ते त्या राशीच्या फक्त कोटीवर अवलंबून नसते. त्यामुळे बैजिक राशी "+poly1+" आणि "+poly2+" यांचे मूल्य आपण कोटीच्या आधारावर ठरवू शकत नाही."+
                    " या दोन्ही राशींचे मूल्य समान किंवा वेगवेगळे, काहीही असू शकते. त्याबद्दल आपल्याला काहीच निश्चित सांगता येणार नाही.<br>"+
                    " म्हणून \"सांगता येत नाही\" हा योग्य पर्याय आहे.<br>";

            String Solution = " "+Solu+" "+Sol1+" ";
            row.createCell(16).setCellValue(Solution);
            row.createCell(18).setCellValue(variation_no);

            mapsize = map.size();
            map.put(varNum+poly1+poly2, i);
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
