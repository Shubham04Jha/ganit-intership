import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Ass5 {
    final static int variation_no = 165,difficulty_level = 3, time_alloted = 60, anstype =1,minTerms=4,maxTerms =5,min_coefficient=-9,max_coefficient=9;
    final static int minDeg = 1,maxDeg = 5;
    final static String topic_no = "030402",questiontype="Text";
    final static String[] variables = {"a", "b", "c", "d", "f", "g", "h", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};


    static class Term{
        // k = coefficient, pwr = power, vp = variable part
        Integer k;
        String vp ; // x^{pwr}
        String var; // theres a segment in the ans where the variable is imp too so will just add it
        String fullterm; // k+vp
        Integer pwr;
        Term(int k,String x,int pwr){
            this.k = k;
            this.var = x;
            this.pwr = pwr;
            this.vp = makeVp(x,pwr);
            this.fullterm = k+this.vp;
        }
        private static String makeVp(String x, int pwr){
            StringBuilder st = new StringBuilder();
            st.append(x);
            if(pwr>1){
                st.append("^{").append(pwr).append("}");
            }
            return st.toString();
        }
    }
    static class Polynomial{
        // str = polynomial String , mt = missing term,
        String str;
        Term mt ;
        Term[] terms;
        String englishSolution;
        String marathiSolution;
        String correctAnswer,wrongAnswer,wrongAnswer1,wrongAnswer2;
        Polynomial(ArrayList<Term> terms){

        }
        public void makeOptions(){

        }
        private void makeSolutioin(){
            // note the term in the ans solution by vml is changed to polynomial and lastly i hv added the "which is the ans part"
             this.englishSolution = "Ans: $"+this.correctAnswer+"$<br>" +
                    "In index form, all terms in the polynomial are arranged in descending order, from highest to lowest index. " +
                    "If any power of $"+this.mt.var+"$ is missing, it is included in the polynomial with a coefficient of $0$. " +
                    "In this case, the term corresponding to $"+this.mt.vp+"$ is missing, so it is included in index form with a coefficient of $0$.<br>" +
                    "So, the index form of $"+this.str+"$ is $"+this.correctAnswer+"$, which is the answer.<br>";
             this.marathiSolution ="#उत्तर: $"+this.correctAnswer+"$<br>" +
                    "कोणतीही बहुपदी घातांक रूपात मांडताना चलाच्या सर्वात मोठ्या घातांकापासून सुरवात करून " +
                    "एक घातांकापर्यंतची सर्व पदे उतरत्या क्रमाने मांडतात आणि शेवटी स्थिरांक लिहतात. " +
                    "जर एखाद्या घातांकाचे पद दिलेल्या बहुपदी मध्ये नसेल तर ते पद $0$ सहगुणकाच्या सहाय्याने दाखवतात.<br>" +
                    "दिलेल्या बहुपदीमध्ये चलाच्या $"+this.mt.vp+"$ या घातांकाचे पद नाही. " +
                    "त्यामुळे ते $0$ सहगुणकाच्या सहाय्याने लिहावे लागेल.<br>" +
                    "म्हणून दिलेल्या बहुपदीचे घातांक रूप $"+this.correctAnswer+"$ असे येईल, हे उत्तर.<br>";

        }
    }
    private static int randomNumGen(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min+1)+min;
    }
    private static Term termGen(int deg){

        return new Term();
    }
    public static ArrayList<Term> termsGen(){
        String x = variables[randomNumGen(0,variables.length-1)]; // initializing the variable
        int degPolynomial = randomNumGen(4,5);// max terms is 5 so terms can be 5-6


    }

    private Polynomial polyGen(){
        ArrayList<Term> terms = termsGen();
        return new Polynomial(terms);
    }
}
