package currency;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Currency {
    
    public static void main(String[] args) {
        if(args.length == 1){
            try {
                BufferedReader input = new BufferedReader(new FileReader(args[0]));
                int cases = Integer.parseInt(input.readLine());
                String line;
                int amount, cont, acum = 0;
                float average = 0;
                for (int i = 0; i < cases; i++) {
                    cont = 0;
                    acum = 0;
                    average = 0;
                    line = input.readLine();
                    String [] aux = line.split(" ");
                    /*Save amount per line*/
                    amount = Integer.parseInt(aux[0]);
                    /*Size for denomination array coins per line*/
                    int [] denomination = new int[Integer.parseInt(aux[1])];
                    for (int j = 2; j < aux.length; j++) {
                        /*Save the corresponding denomination coin*/
                        denomination[cont] = Integer.parseInt(aux[j]);
                        cont++;
                    }
                    int max_amount = amount + max(denomination);
                    int [] minimun = min_coins(denomination, max_amount);
                    int min_val = 0;
                    int maximun = -1;
                    for (int j = 1; j <= amount; j++) {
                        min_val = minimun[j];
                        for (int k = j; k <= max_amount; k++) {
                            int sub = k - j;
                            int val = search(minimun, sub);
                            if(minimun[k] + val < min_val){
                                min_val = minimun[k] + val;
                            }
                        }
                        acum+=min_val;
                        if(min_val > maximun)
                            maximun = min_val;
                    }
                    average = (float)acum/(float)amount;
                    DecimalFormat df = new DecimalFormat("###.##");
                    System.out.println(df.format(average) + " " + maximun);
                }
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Currency.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Currency.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            System.out.println("You should specify an input file");
        }
    }
    /*Get the min coins [1-N]*/
    public static int [] min_coins(int [] denomination, int amount){
        int [] coins = new int[amount + 1];      
        int aux;
        /*The first element is filled with 0 and the rest with oo*/
        for (int i = 0; i <= amount; i++) {
            if(i == 0)
                coins[i] = 0;
            else
                coins[i] = Integer.MAX_VALUE;
                
        }
        /*For each number in the range [1-N] compare all the denomination coins */
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < denomination.length; j++) {
                if(denomination[j] <= i){
                    aux = coins[i - denomination[j]];
                    if(aux != Integer.MAX_VALUE && aux + 1 < coins[i]){
                        //Save the actual min
                        coins[i] = aux + 1;
                    }
                }
            }
        }
        
        return coins;
    }
    /*Get the max value from an array*/
    public static int max(int [] array){
        int max_number = array[0];
        for (int i = 1; i < array.length; i++) {
            if(array[i] > max_number){
                max_number = array[i];
            }
        }
        
        return max_number;
    }
    /*Get the number of coins from an amount*/
    public static int search(int [] array, int value){
        for (int i = 1; i <= array.length; i++) {
            if(value == i)
                return array[i];
        }
        
        return 0;
    }
    
}
