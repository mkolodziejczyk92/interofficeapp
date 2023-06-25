package io.mkolodziejczyk92.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberToWordsConverter {


    public static String convertNumberToWords(long number) {

        String[] units  = { "", "jeden ", "dwa ", "trzy ", "cztery ",
                "pięć ", "sześć ", "siedem ", "osiem ", "dziewięć ", };

        String[] teens  = { "", "jedenaście ", "dwanaście ", "trzynaście ",
                "czternaście ", "piętnaście ", "szesnaście ", "siedemnaście ",
                "osiemnaście ", "dziewiętnaście ", };

        String[] tens  = { "", "dziesięć ", "dwadzieścia ",
                "trzydzieści ", "czterdzieści ", "pięćdziesiąt ",
                "sześćdziesiąt ", "siedemdziesiąt ", "osiemdziesiąt ",
                "dziewięćdziesiąt ", };

        String[] hundreds  = { "", "sto ", "dwieście ", "trzysta ", "czterysta ",
                "pięćset ", "sześćset ", "siedemset ", "osiemset ",
                "dziewięćset ", };

        String[][] groups  = { { "", "", "" },
                { "tysiąc ", "tysiące ", "tysięcy " },
                { "milion ", "miliony ", "milionów " },
                { "miliard ", "miliardy ", "miliardów " },
                { "bilion ", "biliony ", "bilionów " },
                { "biliard ", "biliardy ", "biliardów " },
                { "trylion ", "tryliony ", "trylionów " }, };

        long unitsDigit, teensDigit, tensDigit, hundredsDigit, groupsDigit = 0, suffixDigit ;
        String word = "";
        String sign = "";

        if (number < 0) {
            sign = "minus ";
            number = -number;
        }
        if (number == 0) {
            sign = "zero";
        }

        while (number != 0) {
            hundredsDigit = number % 1000 / 100;
            tensDigit = number % 100 / 10;
            unitsDigit = number % 10;

            if (tensDigit == 1 & unitsDigit > 0) {
                teensDigit = unitsDigit;
                tensDigit = 0;
                unitsDigit = 0;
            } else {
                teensDigit = 0;
            }

            if (unitsDigit == 1 & hundredsDigit + tensDigit + teensDigit == 0) {
                suffixDigit = 0;

                if (hundredsDigit + tensDigit == 0 && groupsDigit > 0) {
                    groupsDigit = 0;
                    word = groups[(int) groupsDigit][(int) suffixDigit] + word;
                }
            } else if (unitsDigit == 2) {
                suffixDigit = 1;
            } else if (unitsDigit == 3) {
                suffixDigit = 1;
            } else if (unitsDigit == 4) {
                suffixDigit = 1;
            } else {
                suffixDigit = 2;
            }

            if (hundredsDigit + tensDigit + teensDigit + unitsDigit > 0) {
                word = hundreds[(int) hundredsDigit] + tens[(int) tensDigit] + teens[(int) teensDigit]
                        + units[(int) unitsDigit] + groups[(int) groupsDigit][(int) suffixDigit] + word;
            }

            number = number / 1000;
            groupsDigit = groupsDigit + 1;
        }

        word = sign + word;
        return word;
    }

    public static long getWholePartFromDouble(double number){
        return (long) number;
    }

    public static long getFractionalPartFromDouble(double number){
        long wholePart = (long) number;
        return  (long) ((number - wholePart) * 100);
    }


}