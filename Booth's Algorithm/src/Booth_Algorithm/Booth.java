package Booth_Algorithm;
import java.util.Scanner;

/**
 *
 * @author alehandro
 */

public class Booth {
    
    public static String addFunc(int b1, int b2, int sum){
        String resualt = "";
        
        //sum amount of equasions
        int remainder = 0;
        for(int i=0;i<sum;i++){
            resualt +=((b1 % 10 + b2 % 10 + remainder) % 2);
            remainder = ((b1 % 10 + b2 % 10 + remainder) / 2);
            b1 = b1 / 10;
            b2 = b2 / 10;
        }
        
        return new StringBuilder(resualt).reverse().toString(); //reverse
    }
    
    public static int[] valueCheck(int q, int sum, int[] array, String negativeSecond, String positiveSecond){
        if(q == 0 && array[sum * 2 - 1] == 1){     //If q=0 and the last array bit is 1 then we add the negativeSecond
                
            //Taking the number from the array
            String number = "";
            for(int j=0;j<sum;j++){
                number += array[j];
            }
            number = addFunc(Integer.parseInt(number), Integer.parseInt(negativeSecond), sum);

            //Putting the number back to the array
            for(int j=0;j<sum;j++){
                array[j] = Integer.parseInt(number.substring(j, j+1));
            }
        }else if(q == 1 && array[sum * 2 - 1] == 0){      //If q=1 and the last array bit is 0 then we add the positiveSecond

            //Taking the number from the array
            String number = "";
            for(int j=0;j<sum;j++){
                number += array[j];
            }
            number = addFunc(Integer.parseInt(number), Integer.parseInt(positiveSecond), sum);

            //Putting the number back to the array
            for(int j=0;j<sum;j++){
                array[j] = Integer.parseInt(number.substring(j, j+1));
            }
        }
        
        return array;
    } 
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        //global variable init
        int sum;    //The amount of numbers
        String firstNumber;
        String secondNumber;
        
        while(true){      //exception error (1)
            //Asking for the First. (In our case it is 0101)
            System.out.print("Give me the first number :");
            firstNumber = input.nextLine();

            //Asking for the Second. (In our case it is 0010)
            System.out.print("Give me the second number :");
            secondNumber = input.nextLine();

            //exception check error (1)
            //if the number are not the same we need to try again!
            if(firstNumber.length() == secondNumber.length()){  
               sum = firstNumber.length();
               break;     
            }else{
                System.out.println("The numbers must be the same size!! \n");
            }
        }
        
        //starting the table.. (__init__ method)
        int array[] = new int[sum * 2];
        int c = 0;  //this number is the counter
        for(int i=0;i<array.length;i++){
            if(i < array.length / 2){
                array[i] = 0;
            }else{
                array[i] = Integer.parseInt(firstNumber.substring(c, c+1));
                c++;
            }
        }
        
        //finding the (-) value of the second number 
        //which is the 2nd base +1
        String positiveSecond = secondNumber;
        
        String negativeSecondTemp = "";
        for(int i=0;i<secondNumber.length();i++){
            if(secondNumber.substring(i, i+1).equals("1")){
                negativeSecondTemp += "0";
            }else{
                negativeSecondTemp += "1";
            }
        }
        
        //Adding to the new number +1
        String negativeSecond = addFunc(Integer.parseInt(negativeSecondTemp), 1, sum);
        
        //Now we start solving the booth's algorithm!
        int q = 0;  //Q is the (Q-1 bit
        for(int i=0;i<sum;i++){
            
            //calling valueChech() and storing it to the array
            //We put all this code in a new static method because we will call it later again..
            array = valueCheck(q, sum, array, negativeSecond, positiveSecond);
            
            //Update the last B (In the tutorial it was the Q-1)
            q = array[sum * 2 - 1];
            
            //srl the hole table:
            int [] arrayTemp = array.clone();
            array[0] = array[sum * 2 - 1];
            
            for(int j=1;j<arrayTemp.length;j++){
               array[j] = arrayTemp[j-1];
            }
            
        }
        
        //last step is to check the values one more time!!
        array = valueCheck(q, sum, array, negativeSecond, positiveSecond);
        
        System.out.print("The result of Booths algorithm is :");
        for(int i=0;i<array.length;i++){
            System.out.print(array[i]);
        }
        System.out.println();
        
        
    }
    
}
