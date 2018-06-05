/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kripto_knapsack;

import java.util.ArrayList;

/**
 *
 * @author Yohanes Dwi Listio
 */
public class SubsetSum {
    public boolean[][] dp;

    public void display(ArrayList<Integer> v) {
        System.out.println(v);
    }
    /* A recursive function to print all subsets with the help of dp[][]. 
    Vector p[] stores current subset. */
    public ArrayList<Integer> printSubsetsRec(int arr[], int i, int sum,
            ArrayList<Integer> p) {
        // If we reached end and sum is non-zero. We print
        // p[] only if arr[0] is equal to sun OR dp[0][sum] is true.
        if (i == 0 && sum != 0 && dp[0][sum]) {
            p.add(arr[i]);
            display(p);
            return p;
        }

        // If sum becomes 0
        if (i == 0 && sum == 0) {
            display(p);
            return p;
        }
        // If given sum can be achieved after ignoring current element.
        if (dp[i - 1][sum]) {
            // Create a new vector to store path
            ArrayList<Integer> b = new ArrayList<>();
            b.addAll(p);
            return printSubsetsRec(arr, i - 1, sum, b);
        }
        // If given sum can be achieved after considering current element.
        if (sum >= arr[i] && dp[i - 1][sum - arr[i]]) {
            p.add(arr[i]);
            return printSubsetsRec(arr, i - 1, sum - arr[i], p);
        } else {
            return p;
        }
    }

    // Prints all subsets of arr[0..n-1] with sum 0.
    public ArrayList<Integer> getSubsets(int arr[], int n, int sum) {
        if (n == 0 || sum < 0) {
            return null;
        }
        // Sum 0 can always be achieved with 0 elements
        dp = new boolean[n][sum + 1];
        for (int i = 0; i < n; ++i) {
            dp[i][0] = true;
        }
        // Sum arr[0] can be achieved with single element
        if (arr[0] <= sum) {
            dp[0][arr[0]] = true;
        }
        // Fill rest of the entries in dp[][]
        for (int i = 1; i < n; ++i) {
            for (int j = 0; j < sum + 1; ++j) {
                dp[i][j] = (arr[i] <= j) ? (dp[i - 1][j] || dp[i - 1][j - arr[i]]) : dp[i - 1][j];
            }
        }
        if (dp[n - 1][sum] == false) {
            System.out.println("There are no subsets with sum " + sum);
            return null;
        }
        // Now recursively traverse dp[][] to find all paths from dp[n-1][sum]
        ArrayList<Integer> p = new ArrayList<>();
        return printSubsetsRec(arr, n - 1, sum, p);
    }
}
