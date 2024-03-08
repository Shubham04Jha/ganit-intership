import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Ass4_2 {
    static int variation_no = 142,difficulty_level = 2, time_alloted = 60, anstype =1,maxTerms =5,min_coefficient=-12,max_coefficient=12;
    static int minDeg = 1,maxDeg = 5;
    static String topic_no = "030402",questiontype="Text";
    static String[][] variables = {{"a", "b"}, {"p", "q"}, {"x", "y"}, {"l", "m"}, {"u", "v"}, {"r", "s"}, {"b", "c"}, {"f", "g"}};
    static int[][] pwrCombination = {
            {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5},
            {1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4},
            {2, 0}, {2, 1}, {2, 2}, {2, 3},
            {3, 0}, {3, 1}, {3, 2},
            {4, 0}, {4, 1},
            {5, 0}
    };

    static class Term{
        String fullterm;
        int deg=0;
        int coefficient;
        int[] powers ;
        String[] var  ;
        Term(String[] var,int coeff,int[] pwrs){
            coefficient = coeff;
            powers=pwrs;
            this.var = var;
            StringBuilder fulltermBuilder = new StringBuilder();

            // Appending the coeff
            if (coefficient != 1&&coefficient!=-1) {
                fulltermBuilder.append(coefficient);
            }
            if(coefficient==-1){
                fulltermBuilder.append("-");
            }
            // Append variables and powers if the power is not 0

            for (int i = 0; i < var.length; i++) {
                if(pwrs[i]==0) continue;
                deg+=pwrs[i];
                fulltermBuilder.append(var[i]);
                // Encapsulate powers with {} only when the power is not 1!
                if(pwrs[i]!=1)
                    fulltermBuilder.append("^{").append(powers[i]).append("}");
            }
            // Set the fullterm
            fullterm = fulltermBuilder.toString();
        }
        @Override
        public String toString() {
            return fullterm;
        }
    }
    public static int[] genCoeff(int n, int startRange, int endRange) {
        if (n > (endRange - startRange + 1)) {
            throw new IllegalArgumentException("Cannot generate more unique integers than the available range");
        }

        int[] resultArray = new int[n];

        do {
            Set<Integer> uniqueNumbers = new HashSet<>();
            while (uniqueNumbers.size() < n) {
                int randomNumber = randomNumGen(startRange, endRange);
                uniqueNumbers.add(randomNumber);
            }

            int index = 0;
            for (int number : uniqueNumbers) {
                resultArray[index++] = number;
            }

            Arrays.sort(resultArray);
        }while(resultArray[0]==0);

        return resultArray;
    }
    private static int randomNumGen(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min+1)+min;
    }
    static int otherThanThis(int n,int min, int max){
        int n2;
        do{
            n2 = randomNumGen(min,max);
        }while(n==n2);
        return n2;
    }

    static class Polynomial_4_2{
        String comparingEquation;
        public  void makeComparingEquation(int[] arr){
            StringBuilder st = new StringBuilder();
            st.append("(").append(arr[0]);
            for(int i=1;i<arr.length;i++){
                st.append("<");
                st.append(arr[i]);
            }
            st.append(")");
            comparingEquation = st.toString();
        }
        String polynomial;
        String correctAnswer,wrongAnswer,wrongAnswer1,wrongAnswer2,wrongAnswer3;
        public void makeWrongOptions(int ca){
            wrongAnswer = Integer.toString((ca+1)%maxDeg);
            wrongAnswer1 = Integer.toString((ca+2)%maxDeg);
            wrongAnswer2 = Integer.toString((ca+3)%maxDeg);
        }

        Term ansTerm;

        Polynomial_4_2(){
            String[] var = variables[randomNumGen(0, variables.length - 1)];
            int[] uniqueIntArray = genCoeff(maxTerms, min_coefficient, max_coefficient);
            makeComparingEquation(uniqueIntArray);
            HashSet<Integer> hashPwr = new HashSet<>();
            ArrayList<Term> list = new ArrayList<>();
            int idx = randomNumGen(0,pwrCombination.length-1);
            hashPwr.add(idx);
            ansTerm = new Term(var,uniqueIntArray[0],pwrCombination[idx]);
            list.add(ansTerm);
            boolean flag =true;
            for(int i=1;i<maxTerms;i++){
                idx = randomNumGen(0,pwrCombination.length-1);
                if(hashPwr.contains(idx)){
                    i--;
                    continue;
                }
                if(flag&&randomNumGen(1,5)<=3){
                    flag =false;
                    list.add(new Term(var,otherThanThis(0,min_coefficient,max_coefficient), new int[]{0, 0}));
                }
                Term t = new Term(var,uniqueIntArray[i],pwrCombination[idx]);
                list.add(t);
                hashPwr.add(idx);
            }
            //shuffling and making the actual polynomial
            Collections.shuffle(list);
            StringBuilder st = new StringBuilder();
            st.append(list.get(0));
            for(int i=1;i<list.size();i++){
                if(list.get(i).coefficient==0) continue;
                if(list.get(i).coefficient>0){
                    st.append("+");
                }
                st.append(list.get(i));
            }
            polynomial = st.toString();
            correctAnswer = Integer.toString(ansTerm.deg);
            makeWrongOptions(ansTerm.deg);
            wrongAnswer3 = ansTerm.fullterm;
            makeSolution();
        }
        String solution;
        public void makeSolution(){
            int count=0;
            ArrayList<String> var = new ArrayList<>();
            ArrayList<Integer> powers = new ArrayList<>();
            for(int i=0;i<2;i++){
                if(ansTerm.powers[i]>0) {
                    count++;
                    var.add(ansTerm.var[i]);
                    powers.add(ansTerm.powers[i]);
                }
            }

            StringBuilder st = new StringBuilder();
            StringBuilder st2 = new StringBuilder();
            if(count ==1){
                st.append("Here the power of variable $"+var.get(0)+"$ is $"+ powers.get(0)+
                        "$<br> $\\therefore$ power of $"+ansTerm.fullterm+"$ is $"+ansTerm.deg+"$.");
                st2.append(" या पदामध्ये $"+var.get(0)+"$ हे $"+1+"$ चल आहेत" +
                        " यात $"+var.get(0)+"$ या चलाचा घातांक $"+powers.get(0)+"$ आहे.<br>" +
                        "$\\therefore "+ansTerm.fullterm+"$ या पदाची कोटी $"+ansTerm.deg+"$ आहे. <br>." +
                        "$\\therefore$ सगळ्यात लहान सहगुणक असणार्\u200Dया पदाची कोटी $= "+ansTerm.deg+"$ आहे, हे उत्तर.");
            }else if(count ==2){
                st.append("Here the power of variable $" + ansTerm.var[0] + "$ is $" + ansTerm.powers[0]+"$" +
                        " and that of variable $"+ansTerm.var[1]+"$ is $"+ansTerm.powers[1]+"$" +
                        "<br> $\\therefore$ power of $"+ansTerm.fullterm+"$ is $"+ansTerm.powers[0]+"+"+ansTerm.powers[1]+" = "+ansTerm.deg+"$.");
                st2.append(" या पदामध्ये $"+ansTerm.var[0]+"$ आणि $"+ansTerm.var[1]+"$ असे "+2+" चल आहेत" +
                        " यात $"+ansTerm.var[0]+"$ या चलाचा घातांक $"+ansTerm.powers[0]+"$ आहे तर $"+ansTerm.var[1]+"$ चा $"+ansTerm.powers[1]+"$ आहे." +
                        "<br> $\\therefore "+ansTerm.fullterm+"$ या पदाची कोटी $"+ansTerm.powers[0]+"+"+ansTerm.powers[1]+" = "+ansTerm.deg+"$ आहे. <br>"+
                        "$\\therefore$ सगळ्यात लहान सहगुणक असणार्\u200Dया पदाची  कोटी $= "+ansTerm.deg+"$ आहे, हे उत्तर. <br>");
            }else if(count >2||count<1) {
                throw new IllegalArgumentException("This was only made for max 2 variables ");
            }
            String Solu ="Ans : $"+ansTerm.deg+"$<br> " +
                    "Constant associated with the variable is called as coefficient of that term. Here smallest coefficient is $"+ansTerm.coefficient+
                    "$ as $"+comparingEquation+"$ and is associated with term $"+ansTerm.fullterm+"$.<br> Degree of the term is, sum of individual powers of all variables in the term. If a constant term is appearing without a variable, then it is not considered as a coefficient.<br>"
                    + st+"<br>" +
                    "$\\therefore$ degree of the term with smallest coefficient is $"+ansTerm.deg+"$ is the answer.<br>";
            String Uttar = "# उत्तर : $"+ansTerm.deg+"$ <br>" +
                    "चला सोबत असणारा स्थिरांक हा त्या चलाचा सहगुणक असतो. पण जर नुसता स्थिरांक असेल तर, त्याच्या बरोबर चल नसल्याने त्याला सहगुणक म्हणता येत नाही.<br>पद जर एका चलातील असेल तर त्या चलाचा घातांक हाच त्या पदाची कोटी असते." +
                    " पद जर एकापेक्षा अधिक चलातील असेल तर सर्व चलांच्या घातांकांची बेरीज ही त्या पदाची कोटी असते.<br>" +
                    "दिलेल्या बहुपदीमध्ये $"+ansTerm.fullterm+"$ या पदाचा सहगुणक $"+ansTerm.coefficient+"$ हा सर्वात लहान आहे." +
                    " कारण  $"+comparingEquation+"$.<br>" +
                    st2;
            solution = Solu+Uttar;
        }
    }
    public static void main(String args[]) throws IOException, FileNotFoundException {
        String filename = "E:/Java Internship/ExcelSheets/VESIT_030402_142_Assign4_Shubham.xlsx";     //Location where excel file is getting generated
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
        System.out.println(" assignment 4 : How many question you want to enter:-");
        Scanner sc=new Scanner(System.in);
        int mapsize,mapsizeafter;
        HashMap<String, Integer> map = new HashMap<String, Integer> ();
        int q = 200;
//        q=sc.nextInt();
        for(int i =1;i<q+1;i++) {
            // Create row
            XSSFRow row = sheet1.createRow(i);
            row.createCell(0).setCellValue(i);  // sr no
            row.createCell(1).setCellValue(questiontype); // q type
            row.createCell(2).setCellValue(anstype);   // ans type singlular in this case
            row.createCell(3).setCellValue(topic_no); //topic number
            Polynomial_4_2 p = new Polynomial_4_2();
            //Generate question english
            String  Que = "Find the degree of the term with smallest coefficient in the given polynomial. $"+p.polynomial+"$.<br>";
            //Generate question marathi
            String Que1 = "#$"+p.polynomial+"$ या बहुपदी मधील सगळ्यात लहान सहगुणक असणाऱ्या पदाची कोटी सांगा.<br>";
            String Question = ""+Que+" "+Que1+"";
            String correct_ans = "$"+p.correctAnswer+"$<br>",wrong_ans = "$"+p.wrongAnswer+"$<br>" ,wrong_ans1= "$"+p.wrongAnswer1+"$<br>",wrong_ans2 = "$"+p.wrongAnswer2+"$<br>",wrong_ans3 = "$"+p.wrongAnswer3+"$<br>";
            row.createCell(4).setCellValue(Question);
            row.createCell(5).setCellValue(correct_ans);

            row.createCell(9).setCellValue(wrong_ans);
            row.createCell(10).setCellValue(wrong_ans1);
            row.createCell(11).setCellValue(wrong_ans2);
            row.createCell(12).setCellValue(time_alloted); // timealloted
            row.createCell(13).setCellValue(difficulty_level);  // difficulty level
            row.createCell(15).setCellValue("2022.shubham.jha@ves.ac.in");

            //Generate Solution
            row.createCell(16).setCellValue(p.solution);
            row.createCell(18).setCellValue(variation_no);

            mapsize = map.size();
            map.put(p.polynomial, i);
            mapsizeafter = map.size();

            //In Java, a map can consist of virtually any number of key-value pairs, but the keys must always be unique — non-repeating.
            if(mapsize == mapsizeafter) {
                System.out.println("duplicate Question"+i+". " + Question);
                i--;
            }
            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.println("Polynomial is : "+p.polynomial);
            System.out.println("Question no: "+i);
            System.out.println("Question: "+Question);
            System.out.println("correct answer: "+correct_ans);
            System.out.println("wrong 1: "+wrong_ans);
            System.out.println("wrong 2: "+wrong_ans1);
            System.out.println("wrong 3: "+wrong_ans2);
            System.out.println("wrong 4: "+wrong_ans3);
            System.out.println("Solution: "+p.solution);
            System.out.println("-------------------------------------------------------------------------------------------");

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


