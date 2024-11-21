// Defines a program that randomly generates a CVSS score

/*
using the calculator here: https://nvd.nist.gov/vuln-metrics/cvss/v3-calculator?vector=AV:N/AC:L/PR:N/UI:N/S:U/C:N/I:N/A:N&version=3.1
Base variables:
(Expoitability)
AV Attack Vector
AC Attack Complexity
PR Privileges Required
UI User Interaction
S Scope
(Impact)
C Confidentiality Impact
I Integrity Impact
A Availability Impact

In order to calculate base score, without temporal or environmental

 If (Impact subscore <= 0)     0 else,
    Scope Unchanged4                 𝑅𝑜𝑢𝑛𝑑𝑢𝑝(𝑀𝑖𝑛𝑖𝑚𝑢𝑚[(𝐼𝑚𝑝𝑎𝑐𝑡 + 𝐸𝑥𝑝𝑙𝑜𝑖𝑡𝑎𝑏𝑖𝑙𝑖𝑡𝑦), 10])
    Scope Changed                      𝑅𝑜𝑢𝑛𝑑𝑢𝑝(𝑀𝑖𝑛𝑖𝑚𝑢𝑚[1.08 × (𝐼𝑚𝑝𝑎𝑐𝑡 + 𝐸𝑥𝑝𝑙𝑜𝑖𝑡𝑎𝑏𝑖𝑙𝑖𝑡𝑦), 10])

and the Impact subscore (ISC) is defined as,

    Scope Unchanged 6.42 × 𝐼𝑆𝐶Base
    Scope Changed 7.52 × [𝐼𝑆𝐶𝐵𝑎𝑠𝑒 − 0.029] − 3.25 × [𝐼𝑆𝐶𝐵𝑎𝑠𝑒 − 0.02]^15

Where,

    𝐼𝑆𝐶𝐵𝑎𝑠𝑒 = 1 − [(1 − 𝐼𝑚𝑝𝑎𝑐𝑡𝐶𝑜𝑛𝑓) × (1 − 𝐼𝑚𝑝𝑎𝑐𝑡𝐼𝑛𝑡𝑒𝑔) × (1 − 𝐼𝑚𝑝𝑎𝑐𝑡𝐴𝑣𝑎𝑖𝑙)]

 And the Exploitability subscore is,

    8.22 × 𝐴𝑡𝑡𝑎𝑐𝑘𝑉𝑒𝑐𝑡𝑜𝑟 × 𝐴𝑡𝑡𝑎𝑐𝑘𝐶𝑜𝑚𝑝𝑙𝑒𝑥𝑖𝑡𝑦 × 𝑃𝑟𝑖𝑣𝑖𝑙𝑒𝑔𝑒𝑅𝑒𝑞𝑢𝑖𝑟𝑒𝑑 × 𝑈𝑠𝑒𝑟𝐼𝑛𝑡𝑒𝑟𝑎𝑐𝑡𝑖𝑜𝑛

*/

public class Main {
    public static boolean scopeGen(){
        if (Math.random() < 0.5) {
            return false;
        }
        return true;
    }

    public static float truncateFloat(float value, int decimals) {
        float result = value;
        result = result * (float)Math.pow(10, decimals); 
        result = (float)Math.floor(result); 
        result = result / (float)Math.pow(10, decimals); 
        return result;
    }

    public static float randomFloat() {
        // initialize value
        float value = (float)Math.random() * (float)10.0;
        // truncate value to 3 places after the decimal
        value = truncateFloat(value, 3);
        return value;
    }

    public static void printRating(float score){
        if (score >= 9.0){
            System.out.println("This is critical severity; the highest level of concern.");
        } else if (score >= 7.0) {
            System.out.println("This is high severity; the second highest level of concern.");
        } else if (score >= 4.0) {
            System.out.println("This is medium severity; it's the middling level of concern.");
        } else if (score >= 0.1) {
            System.out.println("This is low severity; there is only a small level of concern.");
        } else {
            System.out.println("This is a severity of zero. There is no vulnerability/concern.");
        }     
    }

    public static float subscoreCalc(boolean scope, float baseISC) {
        if (scope == true) {
            return truncateFloat((float)6.42 * baseISC, 3);
        }
        float value = (float)7.52 * (baseISC - (float)0.029) - (float)3.25 * (float)Math.pow((baseISC - (float)0.02), 15);
        return truncateFloat(value, 3);
    }

    public static void main(String[] args) {
        System.out.println("Randomized representation of a CVSS scoring.");
        // Generate if S is True or False
        boolean S = scopeGen();
        // Print Scope
        System.out.print("The vulnerable component and the impacted component are the same: ");
        System.out.println(S);
        // Generate Impact scores
        float C = randomFloat();
        float I = randomFloat();
        float A = randomFloat();
        // Print Impact Scores
        System.out.printf("The loss of confidentiality from the impacted component: %.3f\n", C);
        printRating(C);
        System.out.printf("The loss of integrity from the impacted component: %.3f\n", I);
        printRating(I);
        System.out.printf("The loss of availability from the impacted component: %.3f\n", A);
        printRating(A);
        // Calculate ISC subscore
        float ISC = (float)1.0 - (((float)1.0 - C) * ((float)1.0 - I) * ((float)1.0 - A));
        float subscoreISC = subscoreCalc(S, ISC);
        // Generate AV, AC, PR, and UI
        float AV = randomFloat();
        float AC = randomFloat();
        float PR = randomFloat();
        float UI = randomFloat();
        // Print Exploitability scores
        System.out.printf("Attack Vector, the necessary proximity for the attack(higher value is a farther distance): %.3f\n", AV);
        printRating(AV);
        System.out.printf("Attack Complexity: %.3f\n", AC);
        printRating(AC);
        System.out.printf("Privileges Required to perform the attack(higher value is fewer privileges): %.3f\n", PR);
        printRating(PR);
        System.out.printf("User Interaction, how much a target must do/interact with(higher value is less interaction): %.3f\n", UI);
        printRating(UI);
        // Calculate Expoitability subscore
        float exploit = (float)8.22 * AV * AC * PR * UI;
        // Calculate and print base score
        /*
         If (Impact subscore <= 0)     0 else,
    Scope Unchanged4                 𝑅𝑜𝑢𝑛𝑑𝑢𝑝(𝑀𝑖𝑛𝑖𝑚𝑢𝑚[(𝐼𝑚𝑝𝑎𝑐𝑡 + 𝐸𝑥𝑝𝑙𝑜𝑖𝑡𝑎𝑏𝑖𝑙𝑖𝑡𝑦), 10])
    Scope Changed                      𝑅𝑜𝑢𝑛𝑑𝑢𝑝(𝑀𝑖𝑛𝑖𝑚𝑢𝑚[1.08 × (𝐼𝑚𝑝𝑎𝑐𝑡 + 𝐸𝑥𝑝𝑙𝑜𝑖𝑡𝑎𝑏𝑖𝑙𝑖𝑡𝑦), 10])
        */
        if (subscoreISC <= 0) {
            System.out.println("Impact is less than or equal to zero, therefore the score is zero.\nNo source of concern.");
        } else {
            if (S == true) {
                float baseScore = truncateFloat(Math.min(subscoreISC + exploit, 10), 1);
                System.out.printf("\nBase score: %.1f\n", baseScore);
                printRating(baseScore);
            } else {
                float baseScore = truncateFloat(Math.min((float)1.08 * (subscoreISC + exploit), 10), 1);
                System.out.printf("\nBase score: %.1f\n", baseScore);
                printRating(baseScore);
            }
        }
  }
}
