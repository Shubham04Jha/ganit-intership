import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Ass5 {
    static int variation_no = 165,difficulty_level = 3, time_alloted = 60, anstype =1,minTerms=4,maxTerms =5,min_coefficient=-9,max_coefficient=9;
    static int minDeg = 1,maxDeg = 5;
    static String topic_no = "030402",questiontype="Text";
    static String[] variables = {"a", "b", "c", "d", "f", "g", "h", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};


    static class Term{
        String fullterm;
        Integer deg;
        Integer coefficient;
        ArrayList<Integer> powers = new ArrayList<>() ;
        ArrayList<String> var = new ArrayList<>() ;

    }
    static class Polynomial{
        String polynomial;
        Term ansTerm = new Term();
        Term[] terms;
        String englishSolution;
        String marathiSolution;
        String correctAnswer,wrongAnswer,wrongAnswer1,wrongAnswer2;
        Polynomial(ArrayList<Term> terms){

        }
        public void makeOptions(){

        }
    }
    private static int randomNumGen(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min+1)+min;
    }
    private static Term termGen(int deg){

    }
    public static ArrayList<Term>[] termsGen(){
        String x = variables[randomNumGen(0,variables.length-1)]; // initializing the variable
        int degPolynomial = randomNumGen(4,5);// max terms is 5 so terms can be 5-6


    }

    private Polynomial polyGen(){
        Term[] terms = termsGen();
        return new Polynomial();
    }
}
