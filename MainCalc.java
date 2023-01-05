package calcDemo;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainCalc {
    public static void main(String[] args) {

        while(true) {
            Scanner scanner = new Scanner(System.in);
            String expression = scanner.nextLine();
            System.out.println(calc(expression));
        }
    }


    public static String calc(String input){
        String strOperand1 = null;
        String strOperand2 = null;
        int count = 0;
        while(true) {
            String result = null;
            boolean a1 = true;
            boolean a2 = true;
            char[] expressionArr = input.toCharArray();
            StringBuilder operand1 = new StringBuilder();
            StringBuilder operand2 = new StringBuilder();
            int number1 = 0;
            int number2 = 0;
            char operator = 0;
            for (int i = 0; i < expressionArr.length; i++) {
                operand1.append(expressionArr[i]);
                if (expressionArr[i] == '+' |
                        expressionArr[i] == '-' |
                        expressionArr[i] == '*' |
                        expressionArr[i] == '/' ){
                    count ++;
                    operand1.deleteCharAt(operand1.length() - 1);
                    operator = expressionArr[i];
                    i += 1;
                    while (i < expressionArr.length) {
                        if (expressionArr[i] == '+' |
                                expressionArr[i] == '-' |
                                expressionArr[i] == '*' |
                                expressionArr[i] == '/')
                            count++;
                        operand2.append(expressionArr[i]);
                        i++;
                        
                    }
 strOperand1 = operand1.toString().trim();
 strOperand2 = operand2.toString().trim();
                    break;
                }



            }
            try {

                if(count == 0) throw new IllegalArgumentException("String isn't math operation");
                if(count > 1 | strOperand1.indexOf(' ') != -1 | strOperand2.indexOf(' ') != -1 ) throw new IllegalArgumentException("Operation does not satisfy the task - two operands and one operator");
                number1 = Integer.parseInt(strOperand1);


            } catch (NumberFormatException e) {
                try {
                    number1 = RomanNumbers.convertRomanToArabic(strOperand1);
                    a1 = false;
                } catch (IllegalArgumentException b) {
                    throw new IllegalArgumentException("invalid number format");

                }
            }

            try {
                number2 = Integer.parseInt(strOperand2);
            } catch (NumberFormatException e) {
                try {
                    a2 = false;
                    number2 = RomanNumbers.convertRomanToArabic(strOperand2);
                } catch (IllegalArgumentException b) {
                    throw new IllegalArgumentException("invalid number format");


                }
            }

            if (a1 & a2) {
                if(number1 > 10 |
                   number1 < 0 |
                   number2 > 10|
                   number2 < 0) throw new IllegalArgumentException("one from arguments or two > 10 or < 0");
                switch (operator) {
                    case '+' -> result = String.valueOf(number1 + number2);
                    case '-' -> result = String.valueOf(number1 - number2);
                    case '/' -> result = String.valueOf(number1 / number2);
                    case '*' -> result = String.valueOf(number1 * number2);
                }
            } else if (!a1 & !a2) {
                switch (operator) {
                    case '+' -> result = RomanNumbers.convertArabicToRome(number1 + number2);
                    case '-' -> result = RomanNumbers.convertArabicToRome(number1 - number2);
                    case '/' -> result = RomanNumbers.convertArabicToRome(number1 / number2);
                    case '*' -> result = RomanNumbers.convertArabicToRome(number1 * number2);
                }

            } else {
                throw new ArithmeticException("must not use 2 different formats");
            }


        return result;}
    }
    
    enum RomanNumbers{
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumbers(int value) {
            this.value = value;
        }
        private static List<RomanNumbers> getList(){
            List<RomanNumbers> list = new java.util.ArrayList<>(List.of(RomanNumbers.values()));
            list.sort(Comparator.comparing(RomanNumbers::getValue).reversed());
            return list;
        }

        public int getValue() {
            return value;
        }
        public static int convertRomanToArabic(String number){
            int rever = 0, count = 0, result = 0, count5 = 0, count50 = 0, count500 = 0;
            int [] resultArr = new int[number.length()];
            char[]arrNumb = number.toUpperCase().toCharArray();
            for(int i = 0; i < arrNumb.length; i++){
                RomanNumbers num = RomanNumbers.valueOf(String.valueOf(arrNumb[i]));
                resultArr[i] = num.getValue();
            }
            for(int a = 0; a < resultArr.length; a++) {
                if(resultArr[a] == 5)
                    count5++;
                    if(resultArr[a] == 50)
                        count50++;
                        if(resultArr[a] == 500)
                            count500++;

                if (resultArr[a] == rever) {
                    count++;
                } else {
                    count = 0;
                    if (resultArr[a] == 1 |
                            resultArr[a] == 10 |
                            resultArr[a] == 100 |
                            resultArr[a] == 1000) {
                        rever = resultArr[a];
                        count++;
                    }
                }
                if (a < resultArr.length-1) {
                    if (resultArr[a] < resultArr[a + 1] )
                        if(resultArr[a] != 1 &
                            resultArr[a] != 10 &
                            resultArr[a] != 100 &
                            resultArr[a] != 1000) try {
                        throw new Exception("just numbers(1,10,100,1000) can reduce next number");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                if(a < resultArr.length-1) {
                    if (count >= 2 & rever < resultArr[a + 1])
                        try {
                            throw new Exception("wrong character order");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
                if(count == 4) try {
                    throw new Exception("numbers(1,10,100,1000) can't be more 3 times");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(count5 == 2 | count50 == 2 | count500 == 2) try {
                    throw new Exception("numbers(5,50,500) can't be more 1 times");
                }
             catch (Exception e) {
                e.printStackTrace();
            }
            }
            for(int i = 0; i < resultArr.length; i++){
                if (i < resultArr.length-1) {
                    if (resultArr[i] < resultArr[i + 1]) {
                        result += resultArr[i + 1] - resultArr[i];
                        i += 1;
                    }
                    else result += resultArr[i];
                }
                else result += resultArr[i];
            }
            return result;

        }
        public static String convertArabicToRome(int number){
            if(number <= 0 || number >= 4000)
                throw new IllegalArgumentException("number is low than 0 or high 3999");

            List<RomanNumbers> list = getList();
            StringBuilder result = new StringBuilder();
            int i = 0;
            while(number > 0){
if(number >= list.get(i).getValue()){
    number -= list.get(i).getValue();
    result.append(list.get(i).name());
    i = 0;
}
else
                i++;
            }
            return String.valueOf(result);
        }
        }
}
