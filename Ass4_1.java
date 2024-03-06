//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.*;
//
//class Term{
//    String fullterm;
//    int deg;
//    int coefficient;
//    ArrayList<Integer> powers = new ArrayList<>() ;
//    ArrayList<String> var = new ArrayList<>() ;
//
//}
//class Polynomial{
//    String polynomial;
//    String comparingEqn;
//    Term ansTerm = new Term();
//    ArrayList<Integer> remDeg = new ArrayList<>();
//    String englishSolution;
//    String marathiSolution;
//}
//public class Ass4_1 {
//    static int variation_no = 142,difficulty_level = 2, time_alloted = 60, anstype =1,maxTerms =5,min_coefficient=-12,max_coefficient=12;
//    static int minDeg = 1,maxDeg = 5;
//    static String topic_no = "030402",questiontype="Text";
//    static String[][] variables = {{"a", "b"}, {"p", "q"}, {"x", "y"}, {"l", "m"}, {"u", "v"}, {"r", "s"}, {"b", "c"}, {"f", "g"}};
//    private static int randomNumGen(int min, int max){
//        Random random = new Random();
//        return random.nextInt(max - min+1)+min;
//    }
//    static int otherThanThis(int n,int min, int max){
//        int n2;
//        do{
//            n2 = randomNumGen(min,max);
//        }while(n==n2);
//        return n2;
//    }
//    private static StringBuilder halfequationOfPowers(ArrayList<Integer> powers) {
//        StringBuilder st = new StringBuilder();
//        for (int i = 0; i < powers.size(); i++) {
//            st.append(powers.get(i)+"+");
//            if(i==powers.size()-1) st.deleteCharAt(st.length()-1);
//        }
//        return st;
//    }
//
//    private static void helperOfTermGen(StringBuilder term,ArrayList<Integer> powers,int tempDeg){
//        if(tempDeg>1) {
//            term.append(tempDeg);
//            term.append("}");
//            powers.add(tempDeg);
//        }
//        else if(tempDeg == 1){
//
//            term.deleteCharAt(term.length()-1); // deleting {
//            term.deleteCharAt(term.length()-1); // deleting ^
//            powers.add(tempDeg);
//        }
//    }
//    private static String coefficientsToEquation(ArrayList<Integer> arr){
//        //solution will have bracket i just have to see the inside part
//        StringBuilder st = new StringBuilder();
//        Collections.sort(arr);
//        for(int i=0;i<arr.size();i++){
//            st.append(arr.get(i));
//            if(i<arr.size()-1) st.append("<");
//        }
//        return st.toString();
//    }
//    private static Term termGen(String[] vars,int deg){
//        Term t = new Term();
//        int a = randomNumGen(0,3);
//
//        //Selecting the variables
//        if(a<2&&deg>1){ // if only 1 is the deg than only one variable will come
//            t.var.add(vars[0]);
//            t.var.add(vars[1]);
//        }else if(a==2){
//            t.var.add(vars[0]);
//        }else if(a==3){
//            t.var.add(vars[1]);
//        }
//        // selecting the coefficient
//        t.coefficient = otherThanThis(0,min_coefficient,max_coefficient);
//        t.deg = deg;
//        StringBuilder st = new StringBuilder();
//        if(t.coefficient!=0) st.append(t.coefficient);
//        int tempDeg;
//        //generating the terms and adding the powers in the powers
//        for(int i=1;i<=t.var.size();i++){
//            st.append(t.var.get(i-1));
//            st.append("^{");
//            tempDeg=deg;
//            if(i==t.var.size()){
//                helperOfTermGen(st,t.powers,tempDeg);
//            }
//            else if(i==t.var.size()-1){
//                tempDeg = randomNumGen(1,deg-1);
//                helperOfTermGen(st,t.powers,tempDeg);
//            }
//            deg-=tempDeg;
//        }
//        // adding the full term in the term
//        t.fullterm = st.toString();
//        return t;
//    }
//    private static Term[] termsGen(Polynomial p){
//        Term[] terms= new Term[randomNumGen(4,maxTerms)];
//        int rIdxOfVariables = randomNumGen(0,variables.length-1);
//        ArrayList<Integer> coefficients = new ArrayList<>();
//        HashMap<Integer,Integer> coeffCheck = new HashMap<>();
//        for(int i =0;i<terms.length;i++){
//            Term t = termGen(variables[rIdxOfVariables],randomNumGen(minDeg,maxDeg));
//            terms[i]=t;
//            if(coeffCheck.containsKey(t.coefficient)){
//                i--;
//            }
//            else{
//                coeffCheck.put(t.coefficient, 0);
//                coefficients.add(t.coefficient);
//                if (t.coefficient<max_coefficient+1||t.coefficient < p.ansTerm.coefficient) {
//                    p.ansTerm = t;
//                }
//
//            }
//        }
//        p.comparingEqn = coefficientsToEquation(coefficients);
//        return terms;
//    }
//    private static Polynomial Polygen(){
//        Polynomial p = new Polynomial();
//        Term[] terms = termsGen(p);
//        //Building the polynomial String
//        StringBuilder st = new StringBuilder();
//        for(int i=0;i<terms.length;i++){
//            st.append(terms[i].fullterm);
//            if(i<terms.length-1&&terms[i+1].coefficient>0){// append + only when the next term has negative coefficient and its not the last term of the polynomial
//                st.append("+");
//            }
//        }
//        p.polynomial = st.toString();
//        st = new StringBuilder();
//        StringBuilder st2 = new StringBuilder();
//        if(p.ansTerm.var.size()==1){
//            st.append("Here the power of variable $" + p.ansTerm.var.get(0) + "$ is $" + p.ansTerm.powers.get(0)+
//                    "$ and thus the total power is $"+p.ansTerm.deg+"$"+", Thus the degree of term $"+p.ansTerm+"$ is $"+p.ansTerm.deg+"$.");
//            st2.append(" या पदामध्ये $"+p.ansTerm.var.get(0)+" हे $"+p.ansTerm.var.size()+"$ चल आहेत" +
//                    " यात $"+p.ansTerm.var.get(0)+"$ या चलाचा घातांक $"+p.ansTerm.powers.get(0)+"$ आहे." +
//                    " त्यामुळे या पदाची कोटी $"+p.ansTerm.deg+"$ आहे. <br>  म्हणून सगळ्यात लहान सहगुणक असणाऱ्या पदाची कोटी $= "+p.ansTerm.deg+"$ आहे, हे उत्तर. <br>");
//        }else if(p.ansTerm.var.size()==2){
//            st.append("Here the power of variable $" + p.ansTerm.var.get(0) + "$ is $" + p.ansTerm.powers.get(0)+"$" +
//                    " and that of variable $"+p.ansTerm.var.get(0)+"$ is $"+p.ansTerm.powers.get(0)+"$" +
//                    " and their total power is $"+halfequationOfPowers(p.ansTerm.powers)+" = "+p.ansTerm.deg+"$, "+
//                    "Thus the degree of term $"+st+"$ is $"+p.ansTerm.deg+"$.");
//            st2.append(" या पदामध्ये $"+p.ansTerm.var.get(0)+"$ आणि $"+p.ansTerm.var.get(1)+"$ असे $"+p.ansTerm.var.size()+"$ चल आहेत" +
//                    " यात $"+p.ansTerm.var.get(0)+"$ या चलाचा घातांक $"+p.ansTerm.powers.get(0)+"$ आहे तर $"+p.ansTerm.var.get(1)+"$ चा $"+p.ansTerm.powers.get(1)+"$ आहे." +
//                    " त्यामुळे या पदाची कोटी $"+halfequationOfPowers(p.ansTerm.powers)+" = "+p.ansTerm.deg+"$ आहे. <br>  म्हणून सगळ्यात लहान सहगुणक असणाऱ्या पदाची कोटी $= "+p.ansTerm.deg+"$ आहे, हे उत्तर. <br>");
//        }
//        p.englishSolution = st.toString();
//        p.marathiSolution = st2.toString();
//        return p;
//    }
//
//    public static void main(String args[]) throws IOException, FileNotFoundException {
//        String filename = "E:/Java Internship/ExcelSheets/VESIT_030402_142_Assign4_Shubham.xlsx";     //Location where excel file is getting generated
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Instruction");      //Generating first sheet as Instruction
//
//        XSSFSheet sheet1 = workbook.createSheet("Questions");       //Generating second sheet as Questions
//
//        //Adding first row in second sheet(Questions)
//        String[] header = {"Sr. No","Question Type","Answer Type","Topic Number","Question (Text Only)","Correct Answer 1","Correct Answer 2",
//                "Correct Answer 3","Correct Answer 4","Wrong Answer 1","Wrong Answer 2","Wrong Answer 3","Time in seconds","Difficulty Level",
//                "Question (Image/ Audio/ Video)","Contributor's Registered mailId","Solution (Text Only)","Solution (Image/ Audio/ Video)","Variation Number"};
//        XSSFRow rowhead = sheet1.createRow((short)0);
//
//        //Set height and width to the column and row
//        sheet1.setColumnWidth(4, 35*250);
//        sheet1.setColumnWidth(16, 45*250);
//
//        //Adding header to the second sheet
//        for(int head=0; head<header.length; head++) {
//            rowhead.createCell(head).setCellValue(header[head]);
//        }
//
//        //Taking input for number of question you want to generate
//        System.out.println("How many question you want to enter:-");
//        Scanner sc=new Scanner(System.in);
//        int mapsize,mapsizeafter;
//        HashMap<String, Integer> map = new HashMap<String, Integer> ();
//        int q = 200;
//        for(int i =1;i<q+1;i++) {
//            // Create row
//            XSSFRow row = sheet1.createRow(i);
//            row.createCell(0).setCellValue(i);  // sr no
//            row.createCell(1).setCellValue(questiontype); // q type
//            row.createCell(2).setCellValue(anstype);   // ans type singlular in this case
//            row.createCell(3).setCellValue(topic_no); //topic number
//
//            // Generate random terms to perform the operation
//           Polynomial p = Polygen();
//
//           //for correct ans just add the deg of the ansTerm in the polynomial class
//            String Correct_ans = p.ansTerm.deg+"<br>";
//
//            // generating wrong options, i am using the smallest coefficient for the wrong ans 1
//            String wrong_ans = (p.ansTerm.deg!=p.ansTerm.coefficient?p.ansTerm.coefficient:p.ansTerm.coefficient+1)+"<br>";//say if wrong_ans and wrong_ans 1 both are same to Correct_ans hence precaustion is taken
//            // will be using any other terms' degree for this wrong ans
//             String wrong_ans1 ;
//             if(p.ansTerm.powers.size()!=0)
//                 wrong_ans1=(p.ansTerm.powers.get(0))+"<br>"; // but what if both wrongans1 and wrong ans 2 are same? henc the sub power of the term
//            else System.out.println(i+"th question Polynomial is: +"+p.polynomial);
//            // will use the ansterm here
//            String wrong_ans2 = (p.ansTerm)+"<br>";
//            //Generate question english
//            String  Que = "For the given polynomial $"+p.polynomial+"$ find the degree of the term with the smallest coefficient.<br>";
//            //Generate question marathi
//            String Que1 = "#दिलेल्या बहुपदी$"+p.polynomial+"$ साठी सर्वात लहान गुणांकासह पदाची पदवी शोधा."+"<br>";
//            String Question = ""+Que+" "+Que1+"";
//
//            row.createCell(4).setCellValue(Question);
//            row.createCell(5).setCellValue(Correct_ans);
//
//            row.createCell(9).setCellValue(wrong_ans);
//            row.createCell(10).setCellValue(wrong_ans1);
//            row.createCell(11).setCellValue(wrong_ans2);
//            row.createCell(12).setCellValue(time_alloted); // timealloted
//            row.createCell(13).setCellValue(difficulty_level);  // difficulty level
//            row.createCell(15).setCellValue("2022.shubham.jha@ves.ac.in");
//
//            //Generate Solution
//            String Solu ="Ans : $"+p.ansTerm.deg+"$<br> " +
//                    "Constant associated with the variable is called as coefficient of that term. Here smallest coefficient is "+p.ansTerm.coefficient+
//                    " as $("+p.comparingEqn+")$ and is associated with term $"+p.ansTerm+"$." +
//                    " Degree of the term is, sum of individual powers of all variables in the term." +
//                    p.englishSolution+"<br>" +
//                "Hence degree of the term with smallest coefficient is $"+p.ansTerm.deg+"$ is the answer.<br>";
//            String Uttar = "# उत्तर : $"+p.ansTerm.deg+"$ <br>" +
//                    "चलासोबत असणारा स्थिरांक हा त्या चलाचा सहगुणक असतो. <br>पद जर एका चलातील असेल तर त्या चलाचा घातांक हाच त्या पदाची कोटी असते." +
//                    " पद जर एकापेक्षा अधिक चलातील असेल तर सर्व चलांच्या घातांकांची बेरीज ही त्या पदाची कोटी असते.<br>" +
//                    "दिलेल्या बहुपदीमध्ये $"+p.ansTerm+"$ या पदाचा सहगुणक $"+p.ansTerm.coefficient+"$ हा सर्वात लहान आहे." +
//                    " कारण  $("+p.comparingEqn+")$." +
//                    p.marathiSolution;
//            String Solution = " "+Solu+" "+Uttar+" ";
//            row.createCell(16).setCellValue(Solution);
//            row.createCell(18).setCellValue(variation_no);
//
//            mapsize = map.size();
//            map.put(p.polynomial, i);
//            mapsizeafter = map.size();
//
//            //In Java, a map can consist of virtually any number of key-value pairs, but the keys must always be unique — non-repeating.
//            if(mapsize == mapsizeafter) {
//                System.out.println("duplicate Question"+i+". " + Question);
//                i--;
//            }
//
//        }
//
//
//        int rowTotal = sheet1.getLastRowNum();
////			  System.out.println(rowTotal);
//        XSSFRow row = sheet1.createRow((short)rowTotal+1);
//        row.createCell(0).setCellValue("****");
//
//        //Writing data to the file
//        FileOutputStream fileout = new FileOutputStream(filename);
//        workbook.write(fileout);
//        fileout.close();
//
//        System.out.println("file created");
//
//    }
//
//
//
//}
//
