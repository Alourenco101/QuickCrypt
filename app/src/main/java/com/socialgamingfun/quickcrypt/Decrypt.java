package com.socialgamingfun.quickcrypt;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Decrypt{

    //private data members
    private String A1;
    private int odd, even;
    private ArrayList<Character> A1ToChar;
    private ArrayList<Character> RFT1;
    private ArrayList<Character> RFT2;
    private ArrayList<Character> RFT3;
    private ArrayList<Character> A1RFT;
    private char[] oddAlpha ={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' ','-', '0','1','2',
            '3','4','5','6','7','8','9', '@','#','$','&','*','+','=','(',')','_','"',':',';','/','?','!','%','.','[',']','{','}','<','>',',','^','`','~','\''};
    private char[] oddCopy =oddAlpha.clone();
    private char[] evenAlpha ={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' ','-','0','1','2','3',
            '4','5','6','7','8','9', '@','#','$','&','*','+','=','(',')','_','"',':',';','/','?','!','%','.','[',']','{','}','<','>',',','^','`','~','\''};
    private char[] evenCopy=evenAlpha.clone();
    private int x=0,y=0,z =0, v=0, q=0, w=1, g=2;
    public String text;

    /**
     * Constructor
     * @param string
     * @param oddNumber
     * @param evenNumber
     */
    public Decrypt(String string, int oddNumber, int evenNumber)
    {
        A1=string;
        odd=oddNumber;
        even=evenNumber;
        A1ToChar = new ArrayList<Character>();
        for (int i =0; i<A1.length(); i++)
        {
            A1ToChar.add(A1.toCharArray()[i]);
        }
        RFT1 = new ArrayList<Character>(A1.length());
        RFT2 = new ArrayList<Character>(A1.length());
        RFT3 = new ArrayList<Character>(A1.length());
        A1RFT = new ArrayList<Character>(A1.length());

    }

    /**
     * Takes char ArrayList with substituted values and puts it into a Rail Transposition System forming 3 new ArrayList
     * Then combines the 3 ArrayList into one new ArrayList
     * Next, it checks new ArrayList for any ' ' (space) characters and print out the decrypted message
     * Lastly, printing original message to the user
     */
    public void decrypt()
    {

        //Rail Transportation System
        while (q<A1.length())
        {
            RFT1.add(A1ToChar.get(x));
            RFT1.add(' ');
            RFT1.add(' ');
            RFT1.add(' ');
            x++;
            q=q+4;
        }

        while(w<A1.length())
        {
            RFT2.add(A1ToChar.get(x));
            RFT2.add(' ');
            x++;
            w=w+2;
        }

        while (g<A1.length())
        {
            RFT3.add(A1ToChar.get(x));
            RFT3.add(' ');
            RFT3.add(' ');
            RFT3.add(' ');
            x++;
            g=g+4;
        }

        //Putting values into A1RFT from the 3 Rail fence transposition arraylist, forming original message

        for (int i=0; i<A1ToChar.size();i++)
        {
            if(v<RFT1.size())
            {
                A1RFT.add(RFT1.get(v));
                v=v+4;
            }

            if(y<RFT2.size())
            {
                A1RFT.add(RFT2.get(y));
                y=y+2;
            }

            if(z<RFT3.size())
            {
                A1RFT.add(RFT3.get(z));
                z=z+4;
            }

            if(y<RFT2.size())
            {
                A1RFT.add(RFT2.get(y));
                y=y+2;
            }
        }

        /*
        //Checks for any '-' in the new ArrayList and replaces it with ' ' character
        for (int i=0; i<A1RFT.size(); i++)
        {
            if (A1RFT.get(i)=='-')
            {
                A1RFT.set(i,' ');
            }
        }
        */

        //Used to print out arraylist into a string
        StringBuilder builder = new StringBuilder();
        for (char value : A1RFT) {
            builder.append(value);
        }
        text = builder.toString();


    }


    /**
     * Takes an array filled with the alphabet (a-z) and shifts its odd positions by the a number provided by user
     * In addition, this method performs the substitution of those odd positions
     */
    public void oddShiftDecrypt()
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

        for (int i=0; i<A1ToChar.size(); i++)
        {
            for (int j = 0; j<oddAlpha.length; j++)
            {
                char temp = A1ToChar.get(i);
                if (temp==oddCopy[j])
                {
                    A1ToChar.set(i,oddAlpha[j]);
                    j=oddAlpha.length;
                }
            }
            i=i+1;
        }

    }

    /**
     * Takes an array filled with the alphabet (a-z) and shifts its even positions by the a number provided by user
     * In addition, this method performs the substitution of those even positions
     */
    public void evenShiftDecrypt()
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

        for (int i=1; i<A1ToChar.size(); i++)
        {
            for (int j = 0; j<evenAlpha.length; j++)
            {
                char temp = A1ToChar.get(i);
                if (temp==evenCopy[j])
                {
                    A1ToChar.set(i,evenAlpha[j]);
                    j=evenAlpha.length;
                }
            }
            i=i+1;
        }
    }



}