import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


public class Ass6 {
    final static String[] variables = {"a", "b", "c", "d", "f", "g", "h", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    static int min_coefficient=-9,max_coefficient=9,min_term =5,max_term =6,min_deg=4,max_deg=5;
    final static int variation_no=166,difficulty_level = 3, time_alloted = 60,ans_type=1,min_missing_term =1,max_missing_term=2;
    final static String topic_no = "030402",questiontype="Text";

    // Term class requires the Coefficient part, String var and int pwr part
   public static class Term{
        // k = coefficient, pwr = power, vp = variable part
        Integer k;
        String vp ; // x^{pwr}
        String var; // the variable of thr term.
        String fullterm; // k+vp
        Integer pwr;
        Term(int k,String x,int pwr){
            this.k = k;
            this.var = x;
            this.pwr = pwr;
            this.vp = makeVp(x,pwr);
            this.fullterm =  (k!=1)?k+this.vp:(pwr==0?k+this.vp:this.vp) ; // if k!= 1 we take the full term and if k is 1 we check if its the constant term or the variable term if constant we append the k and if not we append just the variable part

        }
        private static String makeVp(String x, int pwr){
            if(pwr == 0) return "";
            StringBuilder st = new StringBuilder();
            st.append(x);
            if(pwr>1){
                st.append("^{").append(pwr).append("}");
            }
            return st.toString();
        }
    }

    // Polynomial constructor only requires the terms Arraylist !
    public static class Polynomial{
        String str;
        String indexForm;
        ArrayList<Term> terms;
        ArrayList<Term> mts; // used in marathi soln and the Polynomial constructor to initialise
        String englishSolution;
        String marathiSolution;
        String correctAnswer, wrongAnswer, wrongAnswer1, wrongAnswer2;
        Polynomial(ArrayList<Term> terms) {
            for(int i=0;i<terms.size();i++){   //finding the terms with 0 coefficient and adding in mts
                if(terms.get(i).k==0){
                    mts.add(terms.get(i));
                }
            }
            this.terms = terms;
            str = makeUnorderedPoly(terms);
            indexForm = makePoly();
            makeOptions();
            makeSolution();
        }
        private void makeOptions(){
            Set<String> options = new HashSet<>();
            ArrayList<Integer> coeffs = new ArrayList<>();
            StringBuilder st = new StringBuilder();
            st.append("(");
            for(Term t :terms){       // initialises the coeffs and forms the correct ans!
                coeffs.add(t.k);
                if(st.length()>1) st.append(", "); // we have already appended "(" so size is 1 initially
                st.append(t.k);
            }
            st.append(")");
            correctAnswer = st.toString();
            options.add(correctAnswer);
            while(options.size()<4){
                wrongAnswer = arrangeCoeffs(coeffs,1); // flag 1 means remove 0, flag 0 means keep 0;
                wrongAnswer1 = arrangeCoeffs(coeffs,1);
                wrongAnswer2 = arrangeCoeffs(coeffs,0); // flag 1 means remove 0, flag 0 means keep 0;
                options.add(wrongAnswer);
                options.add(wrongAnswer1);
                options.add(wrongAnswer2);
            }
        }
        // arrangeCoeffs arranges the  coeffs and makes them into bracket string and return uses flag to identify when to remove 0 or not
        public static String arrangeCoeffs(ArrayList<Integer> coeffs, int flag) {
            ArrayList<Integer> modifiedCoeffs = new ArrayList<>(coeffs);
            if (flag == 1) {
                modifiedCoeffs.removeIf(obj -> obj == 0);
            }
            Collections.shuffle(modifiedCoeffs);
            StringBuilder res = new StringBuilder();
            res.append("(");
            for(Integer t : modifiedCoeffs){
                if(res.length()>1){ // why 1? well we have already added '(' ;
                    res.append(", ");
                }
                res.append(t);
            }
            res.append(")");
            return res.toString();
        }
        // function below is checked and returs the index form of the terms made use of enhanced for loop
        private String makePoly() {
            ArrayList<Term> tempTerms = new ArrayList<>(terms);
            StringBuilder st = new StringBuilder();
            for(Term t : tempTerms){
                if(st.length()>1&&t.k>=0){
                    st.append("+");
                }
                st.append(t.fullterm);
            }
            return st.toString();
        }

        // function below is checked made use of collections to write better code
        private String makeUnorderedPoly(ArrayList<Term> terms){
            ArrayList<Term> tempTerms = new ArrayList<>(terms);
            tempTerms.removeIf(obj->obj.k==0);
            Collections.shuffle(tempTerms);
            StringBuilder st = new StringBuilder();
            for(Term t : tempTerms){
                if(st.length()>1&&t.k>0){
                    st.append("+");
                }
                st.append(t.fullterm);
            }
            return st.toString();
        }
        // both of the function below are checked.
        private void makeSolution () {

            this.englishSolution = "Ans: $"+correctAnswer+"$<br>" +
                    "For coefficient form, initially, given polynomial is rewritten in standard form. " +
                    "If any term is missing, it is added with $0$ coefficient to get its index form, " +
                    "and then only coefficients are written in order from highest power to lowest power. " +
                    "Since the given polynomial is not in index form, we will write its index form as $"+indexForm+"$, " +
                    "and then its Coefficient form as $"+correctAnswer+"$.<br>";

            this.marathiSolution = "# उत्तर : $"+correctAnswer+"$<br>" +
                    "सहगुणक रुपातील बहुपदीसाठी प्रथम दिलेली बहुपदी चलाच्या घातांकाच्या उतरत्या क्रमाने प्रमाण रुपात मांडतात. " +
                    "एखाद्या घातांकाचे पद जर बहुपदीत नसेल तर त्याचा सहगुणक $0$ मानून ती बहुपदी घातांक रुपात मांडतात. " +
                    "आणि मग ती बहुपदी घातांकांच्या उतरत्या क्रमाने फक्त पदांच्या सहगुणकांच्या सहाय्याने लिहितात.<br>" +
                    "दिलेल्या बहुपदीमध्ये"+makeSolutionAssistant(mts,"marathi")+" त्यामुळे ती $0$ सहगुणकाच्या सहाय्याने लिहावी लागतील. " +
                    "त्यामुळे दिलेल्या बहुपदीचे घातांक रूप $"+indexForm+"$ असे होईल. " +
                    "म्हणून  तीचे सहगुणक रुप $"+correctAnswer+"$ असे मिळते हे उत्तर.<br> ";

        }
        private String makeSolutionAssistant(ArrayList<Term> mts,String lang){
            StringBuilder st = new StringBuilder();
            if(lang.equals("marathi")){
                for(int i=0;i<mts.size();i++){
                    st.append(" $").append(mts.get(i).vp).append("$");
                    if(i==mts.size()-2) st.append(" आणि  ");
                    else if(i!=mts.size()-1) st.append(",");
                }
                if(mts.size()==1){
                    st.append(" या घातांकातला पद नाही");
                }else {
                    st.append(" या घातांकाची पदे नाहीत");
                }

            }else{
                for(int i=0;i<mts.size();i++){
                    st.append(" $").append(mts.get(i).vp).append("$");
                    if(i==mts.size()-2) st.append(" and  ");
                    else if(i!=mts.size()-1) st.append(",");
                }
                if(mts.size()==1){
                    st.append(" is missing");
                }else {
                    st.append(" are missing");
                }
            }
            return st.toString();
        }

    }
    public static int randomNumGen(int min, int max){
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
    public static ArrayList<Term> termsGen(){
        ArrayList<Term> terms = new ArrayList<>();
        String x = variables[randomNumGen(0,variables.length-1)]; // initializing the variable
        int degPolynomial = randomNumGen(min_deg,max_deg);// max terms is 5 so terms can be 5-6
        int number_of_missing_terms = randomNumGen(min_missing_term,max_missing_term);
        Set<Integer> mt = new HashSet<>();
        while(mt.size()!=number_of_missing_terms){
            mt.add(randomNumGen(1,degPolynomial));
        }
        for(int i= degPolynomial;i>=0;i--){
            if(mt.contains(i)){
                terms.add(new Term(0,x,i));
            }else if(i==degPolynomial){
                terms.add(new Term(randomNumGen(1,max_coefficient),x,i));
            }else{
                terms.add(new Term(otherThanThis(0,min_coefficient,max_coefficient),x,i));
            }
        }
        for()
        return terms;
    }
    private static Polynomial polyGen(){
        ArrayList<Term> terms = termsGen();
        return new Polynomial(terms);
    }

    public static void main(String args[]) throws IOException, FileNotFoundException {
        String filename = "E:/Java Internship/ExcelSheets/VESIT_030402_"+variation_no+"_Assign6_Shubham.xlsx";     //Location where excel file is getting generated
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
        System.out.println("How many questions to be printed?");
        Scanner sc = new Scanner(System.in);
        int q = sc.nextInt();
        for(int i =1;i<q+1;i++) {
            System.out.println("Question no. "+(i));
            // Create row
            XSSFRow row = sheet1.createRow(i);
            row.createCell(0).setCellValue(i);  // sr no
            row.createCell(1).setCellValue(questiontype); // q type
            row.createCell(2).setCellValue(ans_type);   // ans type singular in this case
            row.createCell(3).setCellValue(topic_no); //topic number
            Polynomial p = polyGen();
            String Correct_ans = "$"+p.correctAnswer+"$<br>";
            // generating wrong options
            String wrong_ans = "$"+p.wrongAnswer+"$<br>";
            String wrong_ans1 = "$"+p.wrongAnswer1+"$<br>";
            String wrong_ans2 = "$"+p.wrongAnswer2+"$<br>";

            //Generate questions
            String  Que = "Coefficient form of the polynomial $"+p.str+"$ is _________. <br>";
            //Generate question marathi
            String Que1 = "# $"+p.str+"$ या बहुपदीचे सहगुणक रूप आहे __________.<br>";
            String Question = ""+Que+" "+Que1+"";
            row.createCell(4).setCellValue(Question);
            row.createCell(5).setCellValue(Correct_ans);
            row.createCell(9).setCellValue(wrong_ans);
            row.createCell(10).setCellValue(wrong_ans1);
            row.createCell(11).setCellValue(wrong_ans2);
            row.createCell(12).setCellValue(time_alloted); // timealloted
            row.createCell(13).setCellValue(difficulty_level);  // difficulty level
            row.createCell(15).setCellValue("2022.shubham.jha@ves.ac.in");

            //Generate Solution
            String Solution = " "+p.englishSolution+" "+p.marathiSolution+" ";
            row.createCell(16).setCellValue(Solution);
            row.createCell(18).setCellValue(variation_no);
            mapsize = map.size();
            map.put(p.str, i);
            mapsizeafter = map.size();

            //In Java, a map can consist of virtually any number of key-value pairs, but the keys must always be unique — non-repeating.
            if(mapsize == mapsizeafter) {
                System.out.println("duplicate Question"+i+". " + Question);
                i--;
            }
            System.out.println("Polynomial made :)");
            System.out.println("Question,options and answer:");
            System.out.println(Question);
            System.out.println(Correct_ans);
            System.out.println(wrong_ans);
            System.out.println(wrong_ans1);
            System.out.println(wrong_ans2);
            System.out.println(Solution);
            System.out.println("End of Solution");
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
