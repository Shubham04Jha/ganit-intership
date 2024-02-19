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
public class Ass6 {
    final static String[] variables = {"a", "b", "c", "d", "f", "g", "h", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    static int min_coefficient=-9,max_coefficient=9,min_term =4,max_term =5;
    final static int variation_no=166,difficulty_level = 3, time_alloted = 60,ans_type=1,min_missing_term =1,max_missing_term=2;
    final static String topic_no = "030402",questiontype="Text";
    static class Term{
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
    static class Polynomial{
        String str;
        String indexForm;
        ArrayList<Term> terms;
        ArrayList<Term> mts;
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
            str = makeRandomPoly(terms);

            this.makeSolution();
        }
        private String makeRandomPoly(ArrayList<Term> terms){
            StringBuilder st = new StringBuilder();
            HashMap<Integer,Integer> mp = new HashMap<>();
            for (Term mt : mts) {//  adding the 0 coefficient term's power before hand
                mp.put(mt.pwr, 0);
            }

            while(mp.size()<terms.size()){
                int idx = randomNumGen(0,terms.size()-1);
                if(!mp.containsKey(terms.get(idx).pwr)){
                    if(mp.size()>mts.size()&&terms.get(idx).k>0){// this means the current term is second term and + or - sign can be there acc to the coeff as map already contained the number of missing terms
                        st.append("+");
                    }
                    st.append(terms.get(idx).fullterm);
                    mp.put(terms.get(idx).pwr,0); // increases mp size only when mp do not contain the same power
                }
            }

            return st.toString();
        }
        // both of the function below are checked.
        private void makeSolution () {
            // note the term in the ans solution by vml is changed to polynomial and lastly i hv added the "which is the ans part"
            this.englishSolution = "Ans: $(7, 0, -5, 0, 27)$<br>" +
                    "For coefficient form, initially, given polynomial is rewritten in standard form. " +
                    "If any term is missing, it is added with $0$ coefficient to get its index form, " +
                    "and then only coefficients are written in order from highest power to lowest power. " +
                    "Since the given polynomial is not in index form, we will write its index form as $"+indexForm+"$, " +
                    "and then its Coefficient form as $(7, 0 -5, 0, 27)$.<br>";

            this.marathiSolution = "# उत्तर : $(7, 0, -5, 0, 27)$<br>" +
                    "सहगुणक रुपातील बहुपदीसाठी प्रथम दिलेली बहुपदी चलाच्या घातांकाच्या उतरत्या क्रमाने प्रमाण रुपात मांडतात. " +
                    "एखाद्या घातांकाचे पद जर बहुपदीत नसेल तर त्याचा सहगुणक $0$ मानून ती बहुपदी घातांक रुपात मांडतात. " +
                    "आणि मग ती बहुपदी घातांकांच्या उतरत्या क्रमाने फक्त पदांच्या सहगुणकांच्या सहाय्याने लिहितात.<br>" +
                    "दिलेल्या बहुपदीमध्ये"+makeSolutionAssistant(mts,"marathi")+" त्यामुळे ती $0$ सहगुणकाच्या सहाय्याने लिहावी लागतील. " +
                    "त्यामुळे दिलेल्या बहुपदीचे घातांक रूप $"+indexForm+"$ असे होईल. " +
                    "म्हणून  तीचे सहगुणक रुप $(7, 0, -5, 0, 27)$ असे मिळते हे उत्तर.<br> ";

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

}
