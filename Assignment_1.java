import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Assignment_1 {
        public static void main(String args[]) throws IOException, FileNotFoundException {
            String filename = "E:/Java Internship/ExcelSheets/VESIT_030402_145_Assign1_Shubham.xlsx";     //Location where excel file is getting generated
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
            System.out.println("How many question you want to enter:-");
            Scanner sc=new Scanner(System.in);
            int mapsize,mapsizeafter;
            HashMap<String, Integer> map = new HashMap<String, Integer> ();
            int q = sc.nextInt();
            for(int i =1;i<q+1;i++) {
                // Create row
                XSSFRow row = sheet1.createRow(i);
                row.createCell(0).setCellValue(i); // sr.no
                row.createCell(1).setCellValue("Text");
                row.createCell(2).setCellValue(1);  //
                row.createCell(3).setCellValue("110205");

                // Generate random number to perform the operation
                Random random = new Random();
                int min = 1;
                int max = 20;
                int a = (int)(Math.random()*(max-min+1)+min);
                int min1 = 20;
                int max1 = 50;
                int a_mul = a*2;
                int n = (int)(Math.random()*(max1-min1+1)+min1);
                int min2 = 5;
                int max2 = 20;
                int n_diff = n-1;
                int min3 = 20;
                int max3 = 40;
                int n1 = (int)(Math.random()*(max3-min3+1)+min3);

                int n1_diff = n1-1;
                int d = (int)(Math.random()*(max2-min2+1)+min2);
                int tn = a+(n-1)*d;

                //Generate Correct answer
                int sn = (n1)*(((2*a)+((n1-1)*d)))/2;
                String Correct_ans = String.valueOf(sn);

                //Generate wrong options
                int sn1 =(n1/2)*((a)+((n1-1)*d));
                String wrong_ans = String.valueOf(sn1);
                int sn2 = (n1/2)*((2*a)+((n1)*d));
                String wrong_ans1 = String.valueOf(sn2);
                int sn3 = (n1/2)*((2*a)+((n1+1)*d));
                String wrong_ans2 = String.valueOf(sn3);

                //Generate question english
                String  Que = "If for a given Arithmetic Progression $a ="+a+"$ and $t_{"+n+"} ="+tn+"$ then $S_{"+n1+"} =. . .?$<br>";
                //Generate question marathi
                String Que1 = "#जर एका अंकगणिती श्रेढीमध्ये $a="+a+"$ आणि $ t_{"+n+"}="+tn+" $, तर $S_{"+n1+"} = . . .?$ ";
                String Question = ""+Que+" "+Que1+"";

                row.createCell(4).setCellValue(Question);
                row.createCell(5).setCellValue(Correct_ans);
//				 row.createCell(6).setCellValue(" ");
//				 row.createCell(7).setCellValue(" ");
//				 row.createCell(8).setCellValue(" ");
                row.createCell(9).setCellValue(wrong_ans);
                row.createCell(10).setCellValue(wrong_ans1);
                row.createCell(11).setCellValue(wrong_ans2);
                row.createCell(12).setCellValue(150);
                row.createCell(13).setCellValue(3);
//				  row.createCell(14).setCellValue(" ");
                row.createCell(15).setCellValue("ssm.vlab@coep.ac.in");

                //Generate Solution
                String Solu = "As per given data $ a= "+a+",$ $t_{"+n+"}="+tn+" \\Rightarrow n= "+n+",$ and $S_{"+n1+"}$ is to be found.<br>"
                        +"Formula for $t_{n}=a+(n-1)\\times d$ and from given data<br>we get $t_{"+n+"}="+tn+"="+a+" +("+n+"-1) \\times d$ <br>$&emsp;&emsp;\\Rightarrow "+a+" + "+n_diff+"d ="+tn+"$<br>"
                        +"Solving this for $d$ we get $d= "+d+" $<br>for $S_{"+n1+"} \\Rightarrow n="+n1+"$<br>$\\therefore$ by substituting values of $a, d$ and $n$ in $S_{n}= \\dfrac{n}{2}[2a+(n-1) \\times d ]$<br>"
                        +"we get $S_{"+n1+"}=\\dfrac{"+n1+"}{2} [2\\times ("+a+")+("+n1+"-1)\\times "+d+"] $<br>$&emsp;&emsp;&emsp;&emsp; =\\dfrac{"+n1+"}{2}[ "+a_mul+"+ "+n1_diff+"\\times "+d+"]$ <br>$&emsp;&emsp;&emsp;&emsp;= "+sn+"$<br>$\\therefore S_{"+n1+"}="+sn+"$ is the answer.<br>";
                String Sol1 = "#दिलेल्या माहिती नुसार $ a= "+a+", t_{"+n+"}="+tn+" \\Rightarrow n= "+n+",$ आणि $S_{"+n1+"}$ ची किंमत शोधायची आहे.<br>"
                        +"सूत्रानुसार $t_{n}=a+(n-1)\\times d$ आणि दिलेल्या माहिती नुसार<br> $ t _{"+n+"}="+tn+"="+a+"+("+n+"-1) \\times d $<br>$&emsp;\\Rightarrow "+a+" + "+n_diff+"d ="+tn+"$<br>"
                        +"हे समीकरण $d$ साठी सोडवून आपल्याला $d ="+d+"$ असे मिळते<br>$S_{"+n1+"}$ साठी $\\Rightarrow n="+n1+"$<br>$\\therefore S_{n}= \\dfrac{n}{2}[2a+(n-1) \\times d]$ "
                        +"या सूत्रात $a, d, n$ च्या किमती ठेऊन $\\Rightarrow S_{"+n1+"}= \\dfrac{"+n1+"}{2} [2\\times("+a+")+("+n1+"-1)\\times "+d+"] $<br>$ &emsp;&emsp;=\\dfrac{"+n1+"}{2}[ "+a_mul+"+ "+n1_diff+"\\times "+d+"]$ <br>$ &emsp;&emsp;="+sn+"$<br> $ \\therefore S_{"+n1+"}="+sn+"$ हे उत्तर.<br>" ;
                String Solution = " "+Solu+" "+Sol1+" ";
                row.createCell(16).setCellValue(Solution);
//				  row.createCell(17).setCellValue(" ");
                row.createCell(18).setCellValue(102);

                mapsize = map.size();
                map.put(Question, i);
                mapsizeafter = map.size();

                //In Java, a map can consist of virtually any number of key-value pairs, but the keys must always be unique — non-repeating.
                if(mapsize == mapsizeafter) {
                    System.out.println("duplicate Question"+i+". " + Question);
                    i--;
                }

                if(sn==sn1||sn==sn2||sn==sn3||sn1==sn2||sn1==sn3||sn2==sn3) {
                    System.out.println("duplicate"+ i);
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

