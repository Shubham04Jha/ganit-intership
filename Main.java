//import java.util.*;
//
//public class Main {
//    static int variation_no = 142,difficulty_level = 2, time_alloted = 60, anstype =1,maxTerms =6,min_coefficient=-12,max_coefficient=12;
//    static int minDeg = 1,maxDeg = 5;
//    static String topic_no = "030402",questiontype="Text";
//    static String[][] variables = {{"a", "b"}, {"p", "q"}, {"x", "y"}, {"l", "m"}, {"u", "v"}, {"r", "s"}, {"b", "c"}, {"f", "g"}};
//    static int[][] pwrCombination = {
//            {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5},
//            {1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4},
//            {2, 0}, {2, 1}, {2, 2}, {2, 3},
//            {3, 0}, {3, 1}, {3, 2},
//            {4, 0}, {4, 1},
//            {5, 0}
//    };
//
//    static class Term{
//        String fullterm;
//        int deg=0;
//        int coefficient;
//        int[] powers ;
//        String[] var  ;
//        Term(String[] var,int coeff,int[] pwrs){
//            coefficient = coeff;
//            powers=pwrs;
//            this.var = var;
//            StringBuilder fulltermBuilder = new StringBuilder();
//
//            // Appending the coeff
//            if (coefficient != 1) {
//                fulltermBuilder.append(coefficient);
//            }
//            // Append variables and powers if the power is not 0
//
//            for (int i = 0; i < var.length; i++) {
//                if(pwrs[i]==0) continue;
//                deg+=pwrs[i];
//                fulltermBuilder.append(var[i]);
//                // Encapsulate powers with {} only when the power is not 1!
//                if(pwrs[i]!=1)
//                    fulltermBuilder.append("^{").append(powers[i]).append("}");
//            }
//            // Set the fullterm
//            fullterm = fulltermBuilder.toString();
//        }
//        @Override
//        public String toString() {
//            return fullterm;
//        }
//    }
//    public static int[] genCoeff(int n, int startRange, int endRange) {
//        if (n > (endRange - startRange + 1)) {
//            throw new IllegalArgumentException("Cannot generate more unique integers than the available range");
//        }
//
//        int[] resultArray = new int[n];
//        Set<Integer> uniqueNumbers = new HashSet<>();
//
//        do {
//            while (uniqueNumbers.size() < n) {
//                int randomNumber = randomNumGen(startRange, endRange);
//                uniqueNumbers.add(randomNumber);
//            }
//
//            int index = 0;
//            for (int number : uniqueNumbers) {
//                resultArray[index++] = number;
//            }
//
//            Arrays.sort(resultArray);
//        }while(resultArray[0]==0);
//
//        return resultArray;
//    }
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
//
//    static class Polynomial_4_2{
//        String comparingEquation;
//        public  void makeComparingEquation(int[] arr){
//            StringBuilder st = new StringBuilder();
//            st.append("(").append(arr[0]);
//            for(int i=1;i<arr.length;i++){
//                st.append("<");
//                st.append(arr[i]);
//            }
//            st.append(")");
//            comparingEquation = st.toString();
//        }
//        String polynomial;
//        String correctAnswer,wrongAnswer,wrongAnswer1,wrongAnswer2,wrongAnswer3;
//        public void makeWrongOptions(int ca){
//            wrongAnswer = Integer.toString((ca+1)%maxDeg);
//            wrongAnswer1 = Integer.toString((ca+2)%maxDeg);
//            wrongAnswer2 = Integer.toString((ca+3)%maxDeg);
//        }
//
//        Term ansTerm;
//
//        Polynomial_4_2(){
//            String[] var = variables[randomNumGen(0, variables.length - 1)];
//            int[] uniqueIntArray = genCoeff(maxTerms, min_coefficient, max_coefficient);
//            makeComparingEquation(uniqueIntArray);
//            HashSet<Integer> hashPwr = new HashSet<>();
//            ArrayList<Term> list = new ArrayList<>();
//            int idx = randomNumGen(0,pwrCombination.length-1);
//            hashPwr.add(idx);
//            ansTerm = new Term(var,uniqueIntArray[0],pwrCombination[idx]);
//            list.add(ansTerm);
//            boolean flag =true;
//            for(int i=1;i<=5;i++){
//                idx = randomNumGen(0,pwrCombination.length-1);
//                if(hashPwr.contains(idx)){
//                    i--;
//                    continue;
//                }
//                if(flag&&randomNumGen(1,5)<=3){
//                    flag =false;
//                    list.add(new Term(var,otherThanThis(0,-9,9), new int[]{0, 0}));
//                }
//                Term t = new Term(var,uniqueIntArray[i],pwrCombination[idx]);
//                list.add(t);
//                hashPwr.add(idx);
//            }
//            //shuffling
//            Collections.shuffle(list);
//            StringBuilder st = new StringBuilder();
//            st.append(list.get(0));
//            for(int i=1;i<list.size();i++){
//                if(list.get(i).coefficient==0) continue;
//                if(list.get(i).coefficient>0){
//                    st.append("+");
//                }
//                st.append(list.get(i));
//            }
//            polynomial = st.toString();
//            correctAnswer = Integer.toString(ansTerm.deg);
//            makeWrongOptions(ansTerm.deg);
//            wrongAnswer3 = ansTerm.fullterm;
//            makeSolution();
//        }
//        String solution;
//        public void makeSolution(){
//            int count=0;
//            ArrayList<String> var = new ArrayList<>();
//            ArrayList<Integer> powers = new ArrayList<>();
//            for(int i=0;i<2;i++){
//                if(ansTerm.powers[i]>0) {
//                    count++;
//                    var.add(ansTerm.var[i]);
//                    powers.add(ansTerm.powers[i]);
//                }
//            }
//
//            StringBuilder st = new StringBuilder();
//            StringBuilder st2 = new StringBuilder();
//            if(count ==1){
//                st.append("Here the power of variable $"+var.get(0)+"$ is $"+ powers.get(0)+
//                        "$<br> $\\therefore$ power of $"+ansTerm.fullterm+"$ is $"+ansTerm.deg+"$.");
//                st2.append(" या पदामध्ये $"+var.get(0)+"$ हे $"+1+"$ चल आहेत" +
//                        " यात $"+var.get(0)+"$ या चलाचा घातांक $"+powers.get(0)+"$ आहे.<br>" +
//                        "$\\therefore "+ansTerm.fullterm+"$ या पदाची कोटी $"+ansTerm.deg+"$ आहे. <br>." +
//                        "$\\therefore$ सगळ्यात लहान सहगुणक असणार्\u200Dया पदाची कोटी $= "+ansTerm.deg+"$ आहे, हे उत्तर.");
//            }else if(count ==2){
//                st.append("Here the power of variable $" + ansTerm.var[0] + "$ is $" + ansTerm.powers[0]+"$" +
//                        " and that of variable $"+ansTerm.var[1]+"$ is $"+ansTerm.powers[1]+"$" +
//                        "<br> $\\therefore$ power of $"+ansTerm.fullterm+"$ is $"+ansTerm.powers[0]+ansTerm.powers[1]+" = "+ansTerm.deg+"$.");
//                st2.append(" या पदामध्ये $"+ansTerm.var[0]+"$ आणि $"+ansTerm.var[1]+"$ असे "+2+" चल आहेत" +
//                        " यात $"+ansTerm.var[0]+"$ या चलाचा घातांक $"+ansTerm.powers[0]+"$ आहे तर $"+ansTerm.var[1]+"$ चा $"+ansTerm.powers[1]+"$ आहे." +
//                        "<br> $\\therefore "+ansTerm.fullterm+"$ या पदाची कोटी $"+ansTerm.powers[0]+ansTerm.powers[1]+" = "+ansTerm.deg+"$ आहे. <br>"+
//                        "$\\therefore$ सगळ्यात लहान सहगुणक असणार्\u200Dया पदाची  कोटी $= "+ansTerm.deg+"$ आहे, हे उत्तर. <br>");
//            }else if(count >2||count<1) {
//                throw new IllegalArgumentException("This was only made for max 2 variables ");
//            }
//            String Solu ="Ans : $"+ansTerm.deg+"$<br> " +
//                    "Constant associated with the variable is called as coefficient of that term. Here smallest coefficient is $"+ansTerm.coefficient+
//                    "$ as $"+comparingEquation+"$ and is associated with term $"+ansTerm.fullterm+"$.<br> Degree of the term is, sum of individual powers of all variables in the term. If a constant term is appearing without a variable, then it is not considered as a coefficient.<br>"
//                    + st+"<br>" +
//                    "$\\therefore$ degree of the term with smallest coefficient is $"+ansTerm.deg+"$ is the answer.<br>";
//            String Uttar = "# उत्तर : $"+ansTerm.deg+"$ <br>" +
//                    "चला सोबत असणारा स्थिरांक हा त्या चलाचा सहगुणक असतो. पण जर नुसता स्थिरांक असेल तर, त्याच्या बरोबर चल नसल्याने त्याला सहगुणक म्हणता येत नाही.<br>पद जर एका चलातील असेल तर त्या चलाचा घातांक हाच त्या पदाची कोटी असते." +
//                    " पद जर एकापेक्षा अधिक चलातील असेल तर सर्व चलांच्या घातांकांची बेरीज ही त्या पदाची कोटी असते.<br>" +
//                    "दिलेल्या बहुपदीमध्ये $"+ansTerm.fullterm+"$ या पदाचा सहगुणक $"+ansTerm.coefficient+"$ हा सर्वात लहान आहे." +
//                    " कारण  $"+comparingEquation+"$.<br>" +
//                    st2;
//            solution = Solu+Uttar;
//        }
//    }
//    public static void main(String[] args) {
//        Polynomial_4_2 polynomial_4_2 = new Polynomial_4_2();
//
//        // Accessing and printing all the fields in Polynomial_4_2
//        System.out.println("Polynomial: $" + polynomial_4_2.polynomial+"$<br>");
//        System.out.println("Correct Answer: $" + polynomial_4_2.correctAnswer+"$<br>");
//        System.out.println("Wrong Answer : $" + polynomial_4_2.wrongAnswer+"$<br>");
//        System.out.println("Wrong Answer 1: $" + polynomial_4_2.wrongAnswer1+"$<br>");
//        System.out.println("Wrong Answer 2: $" + polynomial_4_2.wrongAnswer2+"$<br>");
//        System.out.println("Wrong Answer 3: $" + polynomial_4_2.wrongAnswer3+"$<br>");
//        System.out.println("Solution: " + polynomial_4_2.solution);
//        System.out.println("AnsTerm: $" + polynomial_4_2.ansTerm+"$<br>");
//    }
//
//
//
//}
