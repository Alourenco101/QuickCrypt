package com.socialgamingfun.quickcrypt;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Encrypt {

    //private data members
    private String A1;
    private int odd, even;
    private ArrayList<Character> A1ToChar;
    private ArrayList<Character> RFT1;
    private ArrayList<Character> RFT2;
    private ArrayList<Character> RFT3;
    private ArrayList<Character> A1RFT;
    private char[] oddAlpha ={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' ','-','0','1','2',
            '3','4','5','6','7','8','9', '@','#','$','&','*','+','=','(',')','_','"',':',';','/','?','!','%','.','[',']','{','}','<','>',',','^','`','~','\''};
    private char[] oddCopy =oddAlpha.clone();
    private char[] evenAlpha ={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' ','-','0','1','2','3',
            '4','5','6','7','8','9', '@','#','$','&','*','+','=','(',')','_','"',':',';','/','?','!','%','.','[',']','{','}','<','>',',','^','`','~','\''};
    private char[] evenCopy=evenAlpha.clone();
    private int x=0,y=1,z =2;
    public String text;



    /**
     * Constructor
     * @param string
     * @param oddNumber
     * @param evenNumber
     */
    public Encrypt(String string, int oddNumber, int evenNumber)
    {
        A1=string;
        odd=oddNumber;
        even=evenNumber;
        A1ToChar = new ArrayList<Character>();
        for (int i =0; i<A1.length(); i++)
        {
            A1ToChar.add(A1.toCharArray()[i]);
        }
        RFT1 = new ArrayList<Character>();;
        RFT2 = new ArrayList<Character>();
        RFT3 = new ArrayList<Character>();
        A1RFT = new ArrayList<Character>();

    }

    /**
     * Takes char ArrayList and puts it into a Rail Transposition System forming 3 new ArrayList
     * Then combines the 3 ArrayList into one new ArrayList
     * Lastly, checks new ArrayList for any ' ' (space) characters
     */
    public void encrypt()
    {

        //Rail Transportation System
        for(int i=0;i<A1.length();i++)
        {
            while (x!=-1)
            {
                if(x<A1.length()) {
                    RFT1.add(A1ToChar.get(x));
                    if (x + 4 < A1.length()) {
                        x = x + 4;
                    } else
                        x = -1;
                }
                else
                    x = -1;
            }

            while (y!=-1)
            {
                if(y<A1.length()) {
                    RFT2.add(A1ToChar.get(y));
                    if (y + 2 < A1.length()) {
                        y = y + 2;
                    } else
                        y = -1;
                }
                else
                    y = -1;
            }


            while (z!=-1)
            {
                if(z<A1.length()) {
                    RFT3.add(A1ToChar.get(z));
                    if (z + 4 < A1.length()) {
                        z = z + 4;
                    } else
                        z = -1;
                }
                else
                    z = -1;
            }
        }

        //Combines all RFTs ArraLists into one ArrayList Called 'A1RFT'
        A1RFT.addAll(RFT1);
        A1RFT.addAll(RFT2);
        A1RFT.addAll(RFT3);

        /*
        //Checks for any ' ' in the new ArrayList and replaces it with '-' character
        for (int i=0; i<A1RFT.size(); i++)
        {
            if (A1RFT.get(i)==' ')
            {
                A1RFT.set(i,'-');
            }
        }
        */
    }


    /**
     * Takes an array filled with the alphabet (a-z) and shifts its odd positions by the a number provided by user
     * In addition, this method performs the substitution of those odd positions
     */
    public void oddShift()
    {

        //Shifts Elements of the oddAlpha array by the number provided
        for (int j=1; j<=odd; j++)
        {
            char temp = oddAlpha[oddAlpha.length-1];

            for (int i = oddAlpha.length-2; i>=0; --i)
            {
                oddAlpha[i+1]=oddAlpha[i];
            }
            oddAlpha[0]= temp;
        }

        //Substitution

        for (int i=0; i<A1RFT.size(); i++)
        {
            for (int j = 0; j<oddAlpha.length; j++)
            {
                char temp = A1RFT.get(i);
                if (temp==oddAlpha[j])
                {
                    A1RFT.set(i,oddCopy[j]);
                    j=oddAlpha.length;
                }
            }
            i=i+1;
        }

    }

    /**
     * Takes an array filled with the alphabet (a-z) and shifts its even positions by the a number provided by user
     * In addition, this method performs the substitution of those even positions
     * Lastly, it returns the encrypted message to the user
     */
    public void evenShift()
    {

        //Shifts Elements of the evenAlpha array by the number provided
        for (int j=1; j<=even; j++)
        {
            char temp = evenAlpha[evenAlpha.length-1];

            for (int i = evenAlpha.length-2; i>=0; --i)
            {
                evenAlpha[i+1]=evenAlpha[i];
            }
            evenAlpha[0]= temp;
        }
        //Subsuition

        for (int i=1; i<A1RFT.size(); i++)
        {
            for (int j = 0; j<evenAlpha.length; j++)
            {
                char temp = A1RFT.get(i);
                if (temp==evenAlpha[j])
                {
                    A1RFT.set(i,evenCopy[j]);
                    j=evenAlpha.length;
                }
            }
            i=i+1;
        }
        StringBuilder builder = new StringBuilder();
        for (char value : A1RFT) {
            builder.append(value);
        }
        text = builder.toString();
    }
}