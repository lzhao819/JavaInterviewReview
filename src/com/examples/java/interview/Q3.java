package com.examples.java.interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class  Q3{
    public static void main (String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int target = Integer.parseInt(br.readLine());
        int[] coins = {100,50,20,10,5,1};
        int[] res = combination(target, coins);
        for(int i=0;i<res.length-1;i++){
            System.out.print(res[i]);
            System.out.print(' ');
        }
        System.out.print(res[res.length-1]);

    }
    public static int[] combination(int num, int[] array){
        int[] res = new int[array.length];
        for(int i=0;i<array.length;i++){
            res[i] = num/array[i];
            num-=res[i]*array[i];
        }
        return res;
    }
}
