/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kripto_knapsack;

import java.math.BigInteger;

/**
 *
 * @author Yohanes Dwi Listio
 */
public class Knapsack extends SubsetSum {

    private String message;
    private char[] txt, decryptTXT;
    private int[] private_key, public_key;
    private int[] cipher, plaintext;
    private int a, p, invers_a;

    public void setMessage(String message) {
        this.message = message;
        txt = this.message.toCharArray();
    }

    public void Process_Knapsack() {
        enkripsiKnapsack();
        display(cipher);
        dekripsiKnapsack();
        display(plaintext);
        System.out.println(String.valueOf(decryptTXT));
    }

    public boolean setPrivateKey(int[] private_key) {
        if (private_key[1] < private_key[0]) {
            System.out.println("Deret private key harus merupakan deret super-increasing. Silahkan coba lagi.");
            return true;
        } else {
            for (int i = 2; i < private_key.length; i++) {
                if (jumlah(private_key, (i - 1)) > private_key[i]) {
                    System.out.println("Deret private key harus merupakan deret super-increasing. Silahkan coba lagi.");
                    return true;
                }
            }
        }
        this.private_key = private_key;
        return false;
    }

    public boolean setP(int p) {
        if (p < jumlah(private_key, private_key.length)) {
            System.out.println("Nilai P terlalu kecil. Silahkan coba lagi.");
            return true;
        } else {
            this.p = p;
            return false;
        }
    }

    public boolean setA(int a) {
        BigInteger P = BigInteger.valueOf(p);
        if (P.gcd(BigInteger.valueOf(a)).equals(BigInteger.ONE)) {
            this.a = a;
            return false;
        } else {
            System.out.println("A harus bilangan coprima dengan P");
            return true;
        }
    }

    private int jumlah(int[] array, int limit) {
        int jumlah = 0;
        for (int i = 0; i < limit; i++) {
            jumlah += array[i];
        }
        return jumlah;
    }

    private void generatePublicKey() {
        public_key = new int[private_key.length];
        for (int i = 0; i < public_key.length; i++) {
            public_key[i] = private_key[i] * a % p;
        }
        System.out.println("Generated public key : ");
        for (int i = 0; i < public_key.length; i++) {
            System.out.println(private_key[i] + " * " + a + " mod " + p + " = " + public_key[i]);
        }
    }

    private void enkripsiKnapsack() {
        generatePublicKey();
        cipher = new int[txt.length];
        for (int i = 0; i < cipher.length; i++) {
            char[] biner = desimalToBiner((int) txt[i]).toCharArray();
            cipher[i] = Korespondensi(biner, public_key);
        }
    }

    private void dekripsiKnapsack() {
        plaintext = new int[cipher.length];
        decryptTXT = new char[cipher.length];
        inversA();
        for (int i = 0; i < plaintext.length; i++) {
            plaintext[i] = cipher[i] * invers_a % p;
            System.out.println(cipher[i] + " * " + invers_a + " mod " + p + " = " + plaintext[i]);
        }
        for (int i = 0; i < plaintext.length; i++) {
            System.out.print(plaintext[i] + " --> ");
            int[] subsets = getSubset(plaintext[i]);
            plaintext[i] = binerToDesimal(getBiner(subsets));
        }
        for (int i = 0; i < plaintext.length; i++) {
            decryptTXT[i] = (char) plaintext[i];
        }
    }
    
    private int[] getSubset(int sum) {
        Object[] subset_sum = getSubsets(private_key, private_key.length, sum).toArray();
        int[] subset = new int[subset_sum.length];
        for (int i = 0; i < subset.length; i++) {
            subset[i] = Integer.parseInt(subset_sum[i].toString());
        }
        return subset;
    }

    private String desimalToBiner(int desimal) {
        String depan = "";
        String biner = "";
        do {
            int sisa = desimal % 2;
            desimal /= 2;
            biner = (sisa == 0 ? "0" : "1") + biner;
        } while (desimal != 0);
        if (biner.length() < 8) {
            for (int i = 1; i <= 8 - biner.length(); i++) {
                depan += "0";
            }
        }
        return depan.concat(biner);
    }

    private int Korespondensi(char[] biner, int[] keys) {
        int jumlah = 0;
        for (int i = 0; i < biner.length; i++) {
            if (biner[i] == '1') {
                jumlah += keys[i];
            }
        }
        return jumlah;
    }

    private void display(int[] array) {
        for (int j : array) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    private void inversA() {
        int i = 1;
        while (true) {
            int temp = (1 + (i * p)) / a;
            if ((1 + (i * p)) % a == 0) {
                invers_a = temp;
                System.out.println("m = " + i);
                break;
            } else {
                i++;
            }
            if (i >= a) {
                System.out.println("Private key tidak ditemukan");
            }
        }
    }

    public int binerToDesimal(char[] biner) {
        int jumlah = 0;
        for (int i = 0; i < biner.length; i++) {
            if (biner[i] == '1') {
                jumlah += (int) Math.pow(2, biner.length - i - 1);
            }
        }
        return jumlah;
    }

    private char[] getBiner(int[] subsets) {
        char[] biner = new char[8];
        for (int j = 0; j < biner.length; j++) {
            biner[j] = BinarySearch(subsets, private_key[j]);
        }
        return biner;
    }

    private boolean cekPrima(int n) {
        boolean prima = true;
        if (n == 0 || n == 1) {
            prima = false;
        } else {
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) {
                    prima = false;
                    break;
                }
            }
        }
        return prima;
    }

    private char BinarySearch(int[] array, int x) {
        int k = 0;
        int i = 0;
        int j = array.length - 1;
        boolean found = false;
        while (!found && i <= j) {
            k = (i + j) / 2;
            if (array[k] == x) {
                found = true;
            } else {
                if (array[k] > x) {
                    i = k + 1;
                } else {
                    j = k - 1;
                }
            }
        }
        if (found) {
            return '1';
        } else {
            return '0';
        }
    }
}
