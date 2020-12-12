package com.examples.java.interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Q4 {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String length = br.readLine();
        String s = br.readLine();
        int count=0;
        for(int i=0;i<s.length()-6;i++){
            String start = s.substring(i,i+2);
            if(start.equals("ATG")){
                for(int j=i+3;j<s.length()-3;j++){
                    String end = s.substring(j,j+2);
                    if(end.equals("TAA") ||end.equals("TAG")||end.equals("TGA") ){
                        count++;
                    }
                }
            }
        }
        System.out.println(count);

    }
}
