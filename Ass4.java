//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Random;
//
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//class Polynomial1{
//    String polynomial,ansTerm;
//    int ansDeg,minCoeff;
//    String comparingEqn;
//    ArrayList<Character> variables;
//    ArrayList<Integer> powerOfvariables;
//    String ansLine;
//    Polynomial(){};
//}
//public class Ass4 {
//    static int variation_no = 142,difficulty_level = 2, time_alloted = 60, anstype =1;
//    static String topic_no = "030402",questiontype="Text";
//    static String[][] variables = {{"a","b","c"},{"p","q","r"},{"x","y","z"},{"l","m","n"},{"u","v","w"},{"r","s","t"},{"b","c","d"},{"f","g","h"}};
//    static int minDeg = 1,maxDeg =5,minimum_coefficient=-12,maximum_coefficient = 12;
//
//    private static StringBuilder equationOfPowers(ArrayList<Integer> powers){// works only for two terms!
//        StringBuilder st = new StringBuilder();
//        if(powers.get(1)<0){
//            st.append(powers.get(0));
//            st.append(powers.get(1));
//        }
//        else{
//            st.append(powers.get(0)+" + "+powers.get(1));
//        }
//        return st;
//    }
//
//    private static StringBuilder termGen(Polynomial p,ArrayList<Integer> degs,ArrayList<String> variables,int coefficient){
//        int sumOfDegs=0;
//        for(int i=0;i<degs.size()-1;i++){
//            sumOfDegs+=degs.get(i);
//        }
//        StringBuilder st = new StringBuilder();
//        st.append(coefficient);
//
//        for(int i =0;i<variables.size()-1;i++){
//            if(i==variables.size()-1) st.append(variables.get(i))
//            st.append(variables.get(i)+"{"+degs.get(i)+"}");
//            if(degs.get(i)==1){
//                int t = st.length();
//                st.delete(t-3,t);
//            }
//        }
//
//        if(p.minCoeff>coefficient){
//            StringBuilder st2 = new StringBuilder();
//            p.ansDeg = sumOfDegs;
//            p.minCoeff = coefficient;
//            p.ansTerm = st.toString();
//            if(variables.size()==1){
//                st2.append("Here the power of variable $" + variables.get(0) + "$ is $" + degs.get(0)+
//                        "$ and thus the total power is $"+degs.get(0)+"$"+", Thus the degree of term $"+st+"$ is $"+sumOfDegs+"$.");
//            }
//            if(variables.size()==2){
//                st2.append("Here the power of variable $" + variables.get(0) + "$ is $" + degs.get(0)+"$" +
//                        " and that of variable $"+variables.get(1)+"$ is $"+degs.get(1)+"$" +
//                        " and their total power is $"+equationOfPowers(degs)+" = "+sumOfDegs+"$, "+" " +
//                        "Thus the degree of term $"+st+"$ is $"+sumOfDegs+"$.");
//            }
//            st2.append(".");
//            p.ansLine=st2.toString();
//        }
//        return st;
//    }
//    private static StringBuilder termGen(int deg,int coefficientLocal,String x,String y){
//        StringBuilder st = new StringBuilder(coefficientLocal);
//        if(1==randomNumGen(1,2)||deg==1){
//            if(deg== 1)
//                st.append(x);
//            else{
//                st.append(x+"^{"+deg+"}");
//            }
//        }else{
//            int temp=randomNumGen(1,deg-1);
//            deg = deg-temp;
//            if(temp==1){
//                st.append(x);
//            }
//            else{
//                st.append(x+"^{"+temp+"}");
//            }
//            if(deg == 1 )
//                st.append(y);
//            else{
//                st.append(y+"^{"+deg+"}");
//            }
//        }
//        return st;
//    }
//    private static int randomNumGen(int min, int max){
//        Random random = new Random();
//        return random.nextInt(max - min+1)+min;
//    }
//    private static Polynomial polyGen(){
//        Polynomial p = new Polynomial();
//        p.minCoeff = maximum_coefficient+1;
//        int numberOFTerms = randomNumGen(4,5);
//        int rowIdxForVariables = randomNumGen(0,variables.length-1);
//        int colIdxForVariables = randomNumGen(0,variables[rowIdxForVariables].length-2);
//        //generating variables
//        ArrayList<String> varUsed = new ArrayList<>();
//        varUsed.add(variables[rowIdxForVariables][colIdxForVariables]);
//        varUsed.add(variables[rowIdxForVariables][colIdxForVariables+1]);
//        ArrayList<Integer> coefficients = new ArrayList<>();
//        //generating the coefficients
//        ArrayList<Integer> degOFTerms = new ArrayList<>();
//        for(int i = 0;i< numberOFTerms;i++){
//            degOFTerms.add(randomNumGen(minDeg,maxDeg));
//            degOFTerms.add(randomNumGen(minDeg,maxDeg));
//        }
//        HashMap<Integer,Integer> map = new HashMap<>();
//        for(int i=0;i<numberOFTerms;i++){
//            int mpsizeB4 = map.size();
//            int coeff = randomNumGen(minimum_coefficient,maximum_coefficient);
//            coefficients.add(coeff);
//            map.put(coeff,i);
//            if(map.containsKey(coeff)){
//                coefficients.remove(i);
//                i--;
//            }
//        }
//
//
//        return p;
//    }
//    // variation no 142
//    // topic number 030402
//    //DOD = 2, time alloted = 60, ans type 1;
//
//    public static void main(String args[]) throws IOException,FileNotFoundException {
//        String filename = "E:/Java Internship/ExcelSheets/VESIT_030402_142_Assign4_Shubham.xlsx";     //Location where excel file is getting generated
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Instruction");      //Generating first sheet as Instruction
//
//        XSSFSheet sheet1 = workbook.createSheet("Questions");       //Generating second sheet as Questions
//
//        //Adding first row in second sheet(Questions)
//        String[] header = {"Sr. No", "Question Type", "Answer Type", "Topic Number", "Question (Text Only)", "Correct Answer 1", "Correct Answer 2",
//                "Correct Answer 3", "Correct Answer 4", "Wrong Answer 1", "Wrong Answer 2", "Wrong Answer 3", "Time in seconds", "Difficulty Level",
//                "Question (Image/ Audio/ Video)", "Contributor's Registered mailId", "Solution (Text Only)", "Solution (Image/ Audio/ Video)", "Variation Number"};
//        XSSFRow rowhead = sheet1.createRow((short) 0);
//
//        //Set height and width to the column and row
//        sheet1.setColumnWidth(4, 35 * 250);
//        sheet1.setColumnWidth(16, 45 * 250);
//
//        //Adding header to the second sheet
//        for (int head = 0; head < header.length; head++) {
//            rowhead.createCell(head).setCellValue(header[head]);
//
//        }
//        int mapsize, mapsizeafter;
//        HashMap<String, Integer> map = new HashMap<String, Integer>();
//        int q = 200;
//        for (int i = 1; i < q + 1; i++) {
//            // Create row
//            XSSFRow row = sheet1.createRow(i);
//            row.createCell(0).setCellValue(i);  // sr no
//            row.createCell(1).setCellValue(questiontype); // q type
//            row.createCell(2).setCellValue(anstype);   // ans type singular in this case
//            row.createCell(3).setCellValue(topic_no); //topic number
//            // Generate random terms to perform the operation
//
//            String question = "";
//            String SolutionAns = "Ans : $"+correctDeg+"$<br> " +
//                    "Constant associated with the variable is called as coefficient of that term. Here smallest coefficient is "+smallestCoeff+
//                    " as $("+equationInsideBrac+")$ and is associated with term $"+ansterm+"$." +
//                    " Degree of the term is, sum of individual powers of all variables in the term." +
//                    polyNomkaANs+"<br>" +
//                "Hence degree of the term with smallest coefficient is $"+correctDeg+"$ is the answer.<br>";
//            String Uttar = "# उत्तर : $"+correctDeg+"$ <br>" +
//                    "चलासोबत असणारा स्थिरांक हा त्या चलाचा सहगुणक असतो. <br>पद जर एका चलातील असेल तर त्या चलाचा घातांक हाच त्या पदाची कोटी असते." +
//                    " पद जर एकापेक्षा अधिक चलातील असेल तर सर्व चलांच्या घातांकांची बेरीज ही त्या पदाची कोटी असते.<br>" +
//                    "दिलेल्या बहुपदीमध्ये $"+ansterm+"$ या पदाचा सहगुणक $"+smallestCoeff+"$ हा सर्वात लहान आहे." +
//                    " कारण  $("+equationInsideBrac+")$." +
//                    " या पदामध्ये $"+firstVar+"$ आणि $"+secondVar+"$ असे $"+variablekasize+"$ चल आहेत." +
//                    " यात $"+firstVar+"$ या चलाचा घातांक $"+powerOfFirstVar+"$ आहे तर $"+secondVar+"$ चा $"+powerOfSecondVar+"$ आहे." +
//                    " त्यामुळे या पदाची कोटी $"+sumOfPower+"$ <br>  म्हणून सगळ्यात लहान सहगुणक असणाऱ्या पदाची कोटी $= "+correctDeg+"$ आहे, हे उत्तर. <br>";
//            String solutionText = SolutionAns+Uttar;
//        }
//    }
//}