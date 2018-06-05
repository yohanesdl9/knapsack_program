/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kripto_knapsack;

import java.util.Scanner;

/**
 *
 * @author Yohanes Dwi Listio
 */
public class Kripto_Knapsack {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Knapsack k = new Knapsack();
        int[] private_key = new int[8];
        System.out.print("Masukkan teks : ");
        k.setMessage(input.nextLine());
        do {
            System.out.print("Masukkan deret private key : ");
            for (int i = 0; i < private_key.length; i++){
                private_key[i] = input.nextInt();
            }
        } while (k.setPrivateKey(private_key));
        do {
            System.out.print("Masukkan P : ");
        } while (k.setP(input.nextInt()));
        do {
            System.out.print("Masukkan A : ");
        } while (k.setA(input.nextInt()));
        k.Process_Knapsack();
    }
    
}
