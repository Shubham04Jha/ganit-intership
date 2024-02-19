//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Random;
//import java.util.Scanner;
//
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class Ass5 {
//    final static int variation_no = 165,difficulty_level = 3, time_alloted = 60, anstype =1,minTerms=4,maxTerms =5,min_coefficient=-9,max_coefficient=9;
//    final static int minDeg = 1,maxDeg = 5;
//    final static String topic_no = "030402",questiontype="Text";
//    final static String[] variables = {"a", "b", "c", "d", "f", "g", "h", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
//
//
//     static class Term{
//        // k = coefficient, pwr = power, vp = variable part
//        Integer k;
//        String vp ; // x^{pwr}
//        String var; // theres a segment in the ans where the variable is imp too so will just add it
//        String fullterm; // k+vp
//        Integer pwr;
//        Term(int k,String x,int pwr){
//            this.k = k;
//            this.var = x;
//            this.pwr = pwr;
//            this.vp = makeVp(x,pwr);
//            this.fullterm =  (k!=1)?k+this.vp:(pwr==0?k+this.vp:this.vp) ; // if k!= 1 we take the full term and if k is 1 we check if its the constant term or the variable term if constant we append the k and if not we append just the variable part
//
//        }
//        private static String makeVp(String x, int pwr){
//            if(pwr == 0) return "";
//            StringBuilder st = new StringBuilder();
//            st.append(x);
//            if(pwr>1){
//                st.append("^{").append(pwr).append("}");
//            }
//            return st.toString();
//        }
//    }
//     static class Polynomial {
//        // str = polynomial String , mt = missing term,
//        String str;
//        Term mt;
//        ArrayList<Term> terms;
//        String englishSolution;
//        String marathiSolution;
//        String correctAnswer, wrongAnswer, wrongAnswer1, wrongAnswer2;
//        Polynomial(ArrayList<Term> terms) {
//            for(int i=0;i<terms.size();i++){   //finding and initialising missing term
//                if(terms.get(i).k==0){
//                    this.mt = terms.get(i);
//                }
//            }
//            this.terms = terms;
//            this.str = makeRandomPoly(terms);
//            this.correctAnswer = makePoly();
//            HashMap<String,Integer> mp2 = new HashMap<>();
//            mp2.put(this.str,0);
//            mp2.put(this.correctAnswer,0);
//            while(mp2.size()<5) {
//                //  make wrongAnswer
//                String str1 = makeRandomPoly(terms);
//                this.wrongAnswer = str1;
//                mp2.put(str1,0);
//                String str2 = makeRandomPoly2(terms);
//                this.wrongAnswer1 = str2;
//                mp2.put(str2,0);
//                String str3 = makeRandomPoly2(terms);
//                this.wrongAnswer2 = str3;
//                mp2.put(str3,0);
//                }
//            this.makeSolution();
//            }
//        private String makeRandomPoly2(ArrayList<Term> terms){
//            StringBuilder st = new StringBuilder();
//            HashMap<Integer,Integer> mp = new HashMap<>();
//            while(mp.size()<terms.size()){ // less than is necessary cuz if it reaches the size all the terms are being made!
//                int idx = randomNumGen(0,terms.size()-1);
//                if(!mp.containsKey(terms.get(idx).pwr)){
//                    if(mp.size()>1&&terms.get(idx).k>=0){// this means the current term is second term and + or - sign can be there acc to the coeff
//                        st.append("+");
//                    }
//                    st.append(terms.get(idx).fullterm);
//                    mp.put(terms.get(idx).pwr,0); // increases mp size only when mp do not contain the same power
//                }
//
//            }
//            return st.toString();
//        }
//        private String makeRandomPoly(ArrayList<Term> terms){
//            StringBuilder st = new StringBuilder();
//            HashMap<Integer,Integer> mp = new HashMap<>();
//            mp.put(mt.pwr,0);//  adding the 0 coefficient term's power before hand
//            while(mp.size()<terms.size()){
//                int idx = randomNumGen(0,terms.size()-1);
//                if(!mp.containsKey(terms.get(idx).pwr)){
//                    if(mp.size()>1&&terms.get(idx).k>0){// this means the current term is second term and + or - sign can be there acc to the coeff
//                            st.append("+");
//                    }
//                    st.append(terms.get(idx).fullterm);
//                    mp.put(terms.get(idx).pwr,0); // increases mp size only when mp do not contain the same power
//                }
//            }
//
//            return st.toString();
//        }
//        private String makePoly() {
//            StringBuilder st = new StringBuilder();
//            for (int i = 0; i < terms.size(); i++) {
//                    if(i>0&&terms.get(i).k>=0){
//                        st.append("+");
//                    }
//                    st.append(terms.get(i).fullterm);
//            }
//            return st.toString();
//        }
//        private void makeSolution () {
//                // note the term in the ans solution by vml is changed to polynomial and lastly i hv added the "which is the ans part"
//                this.englishSolution = "Ans: $" + this.correctAnswer + "$<br>" +
//                        "In index form, all terms in the polynomial are arranged in descending order, from highest to lowest index, followed by the constant at the end.<br>" +
//                        "If any power of $" + this.mt.var + "$ is missing, it is included in the polynomial with a coefficient as $0$.<br>" +
//                        "In this case, the term corresponding to $" + this.mt.vp + "$ is missing, so it is included in index form with a coefficient of $0$.<br>" +
//                        "$\\therefore$ the index form of given polynomial is $" + this.correctAnswer + "$ is the answer.<br>";
//                this.marathiSolution = "#उत्तर: $" + this.correctAnswer + "$<br>" +
//                        "कोणतीही बहुपदी घातांक रूपात मांडताना चलाच्या सर्वात मोठ्या घातांकापासून सुरवात करून " +
//                        "एक घातांका पर्यंतची सर्व पदे उतरत्या क्रमाने मांडतात आणि शेवटी स्थिरांक लिहतात.<br>" +
//                        "जर एखाद्या घातांकाचे पद दिलेल्या बहुपदी मध्ये नसेल तर ते पद $0$ सहगुणकाच्या सहाय्याने दाखवतात.<br>" +
//                        "दिलेल्या बहुपदीमध्ये चलाच्या $" + this.mt.vp + "$ या घातांकाचे पद नाही. " +
//                        "त्यामुळे ते $0$ सहगुणकाच्या सहाय्याने लिहावे लागेल.<br>" +
//                        "$\\therefore$ दिलेल्या बहुपदीचे घातांक रूप $" + this.correctAnswer + "$ असे येईल, हे उत्तर.<br>";
//
//            }
//
//    }
//    public static int randomNumGen(int min, int max){
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
//    private static Term termGen(int coefficient,String x,int deg){
//        return new Term(coefficient,x,deg);
//    }
//    public static ArrayList<Term> termsGen(){
//        ArrayList<Term> terms = new ArrayList<>();
//        String x = variables[randomNumGen(0,variables.length-1)]; // initializing the variable
//        int degPolynomial = randomNumGen(4,5);// max terms is 5 so terms can be 5-6
//        int mtPwr = randomNumGen(1,degPolynomial-1);
//        for(int i= degPolynomial;i>=0;i--){
//            if(i==mtPwr){
//                terms.add(termGen(0,x,i));
//            }else if(i==degPolynomial){
//                terms.add(termGen(randomNumGen(1,9),x,i));
//            }else{
//                terms.add(termGen(otherThanThis(0,-9,9),x,i));
//            }
//        }
//        return terms;
//    }
//
//    private static Polynomial polyGen(){
//        ArrayList<Term> terms = termsGen();
//        return new Polynomial(terms);
//    }
//
//    public static void main(String args[]) throws IOException,FileNotFoundException{
//        String filename = "E:/Java Internship/ExcelSheets/VESIT_030402_"+variation_no+"_Assign5_Shubham.xlsx";     //Location where excel file is getting generated
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
//
//        }
//        int mapsize,mapsizeafter;
//        HashMap<String, Integer> map = new HashMap<String, Integer> ();
//        System.out.println("5th assign: How many questions to be printed?");
//        Scanner sc = new Scanner(System.in);
//        int q = sc.nextInt();
//        for(int i =1;i<q+1;i++) {
//            System.out.println("Question no. "+(i));
//            // Create row
//            XSSFRow row = sheet1.createRow(i);
//            row.createCell(0).setCellValue(i);  // sr no
//            row.createCell(1).setCellValue(questiontype); // q type
//            row.createCell(2).setCellValue(anstype);   // ans type singular in this case
//            row.createCell(3).setCellValue(topic_no); //topic number
//            Polynomial p = polyGen();
//            String Correct_ans = "$"+p.correctAnswer+"$<br>";
//            // generating wrong options
//            String wrong_ans = "$"+p.wrongAnswer+"$<br>";
//            String wrong_ans1 = "$"+p.wrongAnswer1+"$<br>";
//            String wrong_ans2 = "$"+p.wrongAnswer2+"$<br>";
//
//            //Generate questions
//            String  Que = "Index form of the polynomial $"+p.str+"$ is ____________ . <br>";
//            //Generate question marathi
//            String Que1 = "# $"+p.str+"$ या बहुपदीचे घातांक रूप आहे  ____________ . <br>";
//            String Question = ""+Que+" "+Que1+"";
//            row.createCell(4).setCellValue(Question);
//            row.createCell(5).setCellValue(Correct_ans);
//            row.createCell(9).setCellValue(wrong_ans);
//            row.createCell(10).setCellValue(wrong_ans1);
//            row.createCell(11).setCellValue(wrong_ans2);
//            row.createCell(12).setCellValue(time_alloted); // timealloted
//            row.createCell(13).setCellValue(difficulty_level);  // difficulty level
//            row.createCell(15).setCellValue("2022.shubham.jha@ves.ac.in");
//
//            //Generate Solution
//            String Solution = " "+p.englishSolution+" "+p.marathiSolution+" ";
//            row.createCell(16).setCellValue(Solution);
//            row.createCell(18).setCellValue(variation_no);
//            mapsize = map.size();
//            map.put(p.str, i);
//            mapsizeafter = map.size();
//
//            //In Java, a map can consist of virtually any number of key-value pairs, but the keys must always be unique — non-repeating.
//            if(mapsize == mapsizeafter) {
//                System.out.println("duplicate Question"+i+". " + Question);
//                i--;
//            }
//            System.out.println("Polynomial made :)");
//            System.out.println("Question,options and answer:");
//            System.out.println(Question);
//            System.out.println(Correct_ans);
//            System.out.println(wrong_ans);
//            System.out.println(wrong_ans1);
//            System.out.println(wrong_ans2);
//            System.out.println(Solution);
//            System.out.println("End of Solution");
//        }
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
//    //testing the push from main branch
//    //testing merge conflicts with the master branch.
//
//}
