/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

/**
 *	Amount in Words for Persian
 *
 *  @author Bahman Movaqar
 *  @author Omid Pourhadi
 *  @version $Id: AmtInWords_FA.java,v 1.3 2006/07/30 00:54:35 bmovaqar Exp $
 *
 */
public class AmtInWords_FA implements AmtInWords {
    /**
     * 	AmtInWords_FA
     */
    public AmtInWords_FA() {
        super();
    }	//	AmtInWords_FA
    
    /** Thousands plus				*/
    private static final String[]	majorNames	= {
        "",
        "\u0647\u0632\u0627\u0631", //Hezar
        "\u0645\u06cc\u0644\u06cc\u0648\u0646", //million
        "\u0645\u06cc\u0644\u06cc\u0627\u0631\u062f", //milyard
        "\u062a\u0631\u06cc\u0644\u06cc\u0648\u0646", //trillion
        "\u06a9\u0648\u0622\u062f\u0631\u06cc\u0644\u06cc\u0648\u0646", //Koaderillion
        "\u0647\u0632\u0627\u0631 \u06a9\u0648\u0622\u062f\u0631\u06cc\u0644\u06cc\u0648\u0646" //Hezar Koaderillion
    };
    
    /** One hundred to nine hundreds */
    private static final String[]      hundredsNames = {
        "",
        "\u06cc\u06a9\u0635\u062f", //yekSad
        "\u062f\u0648\u06cc\u0633\u062a", //divist
        "\u0633\u06cc\u0635\u062f", //sisad
        "\u0686\u0647\u0627\u0631\u0635\u062f", //4sad
        "\u067e\u0627\u0646\u0635\u062f", //pansad
        "\u0634\u0634\u0635\u062f", //sheshsad
        "\u0647\u0641\u062a\u0635\u062f", //haftsad
        "\u0647\u0634\u062a\u0635\u062f", //hashtsad
        "\u0646\u0647\u0635\u062f" //nohsad
    };
    /** Ten to Ninety				*/
    private static final String[]	tensNames	= {
        "",
        "\u062f\u0647", //dah
        "\u0628\u06cc\u0633\u062a", //bist
        "\u0633\u06cc",//si
        "\u0686\u0647\u0644", //chehel
        "\u067e\u0646\u062c\u0627\u0647", //panjah
        "\u0634\u0635\u062a", //shast
        "\u0647\u0641\u062a\u0627\u062f", //haftad
        "\u0647\u0634\u062a\u0627\u062f", //hashtad
        "\u0646\u0648\u062f" //navad
    };
    
    /** numbers to 19				*/
    private static final String[]	numNames	= {
        "",
        "\u06cc\u06a9", //yek
        "\u062f\u0648",//do
        "\u0633\u0647",//se
        "\u0686\u0647\u0627\u0631",//chahar
        "\u067e\u0646\u062c",//panj
        "\u0634\u0634",//shesh
        "\u0647\u0641\u062a",//haft
        "\u0647\u0634\u062a",//hasht
        "\u0646\u0647",//noh
        "\u062f\u0647",//dah
        "\u06cc\u0627\u0632\u062f\u0647",//yazdah
        "\u062f\u0648\u0627\u0632\u062f\u0647",//davazdah
        "\u0633\u06cc\u0632\u062f\u0647",//sizdah
        "\u0686\u0647\u0627\u0631\u062f\u0647",//chahardah
        "\u067e\u0627\u0646\u0632\u062f\u0647",//panzdah
        "\u0634\u0627\u0646\u0632\u062f\u0647",//shanzdah
        "\u0647\u0641\u062f\u0647",//hefdah
        "\u0647\u062c\u062f\u0647",//hejdah
        "\u0646\u0648\u0632\u062f\u0647"//nozdah
    };
    
    /**
     * 	Convert Less Than One Thousand
     *	@param number
     *	@return amt
     */
    private String convertLessThanOneThousand(int number) {
        String soFar;
        //	Below 20
        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
            soFar = hundredsNames[number] + ("".equals(soFar) || hundredsNames[number].equals("") ? soFar : " \u0648 " + soFar);
        } else {
            soFar = numNames[number % 10];
            number /= 10;
            soFar = tensNames[number % 10] + (tensNames[number % 10].equals("") ? soFar : " \u0648 " + soFar);
            number /= 10;
            soFar = hundredsNames[number % 10] + (hundredsNames[number % 10].equals("") ? soFar : " \u0648 " + soFar);
        }
        return soFar;
    }	//	convertLessThanOneThousand
    
    /**
     * 	Convert
     *	@param number
     *	@return amt
     */
    private String convert(long number) {
        /* special case */
        if (number == 0) {
            return "\u0635\u0641\u0631";//sefr
        }
        String prefix = "";
        if (number < 0) {
            number = -number;
            prefix = "\u0645\u0646\u0641\u06cc";//manfi
        }
        String soFar = "";
        int place = 0;
        do
        {
            long n = number % 1000;
            if (n != 0) {
                String s = convertLessThanOneThousand((int)n);
                if (!soFar.equals(""))
                    soFar = s + " " + majorNames[place] + 
                            (s.equals("") || majorNames[place].equals("")  ? soFar : " \u0648 " + soFar);
                else
                    soFar = s + " " + majorNames[place];
            }
            place++;
            number /= 1000;
        }
        while (number > 0);
        return (prefix + soFar).trim();
    }	//	convert
    
    
    /**************************************************************************
     * 	Get Amount in Words
     * 	@param amount numeric amount (352.80)
     * 	@return amount in words (three*five*two 80/100)
     * 	@throws Exception
     */
    public String getAmtInWords(String amount) throws Exception {
        if (amount == null)
            return amount;
        //
        StringBuffer sb = new StringBuffer();
        int pos = amount.lastIndexOf('.');
        int pos2 = amount.lastIndexOf(',');
        if (pos2 > pos)
            pos = pos2;
        String oldamt = amount;
        amount = amount.replaceAll(",", "");
        int newpos = amount.lastIndexOf('.');
        long dollars = Long.parseLong(amount.substring(0, newpos));
        sb.append(convert(dollars));
        for (int i = 0; i < oldamt.length(); i++) {
            if (pos == i) //	we are done
            {
                String cents = oldamt.substring(i + 1);
                sb.append(' ').append(cents).append("/100");
                break;
            }
        }
        return sb.toString();
    }	//	getAmtInWords
    
    /**
     * 	Test Print
     *	@param amt amount
     */
    private void print(String amt) {
        try {
            System.out.println(amt + " = " + getAmtInWords(amt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }	//	print
    
    /**
     * 	Test
     *	@param args ignored
     */
    public static void main(String[] args) {
        AmtInWords_FA aiw = new AmtInWords_FA();
        //	aiw.print (".23");	Error
        aiw.print("0.23");
        aiw.print("1.23");
        aiw.print("12.345");
        aiw.print("123.45");
        // more test cases
        aiw.print("103.00");
        aiw.print("100.00");
        aiw.print("523.45");
        aiw.print("1000.00");
        //
        aiw.print("1234.56");
        aiw.print("12345.78");
        aiw.print("123457.89");
        aiw.print("1,234,578.90");
    }	//	main
    
}	//	AmtInWords_FA