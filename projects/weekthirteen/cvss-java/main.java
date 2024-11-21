// Defines a program that randomly generates a CVSS score

import java.util.Math;

/*
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
    Scope Changed 7.52 × [𝐼𝑆𝐶𝐵𝑎𝑠𝑒 − 0.029] − 3.25 × [𝐼𝑆𝐶𝐵𝑎𝑠𝑒 − 0.02]15

Where,

    𝐼𝑆𝐶𝐵𝑎𝑠𝑒 = 1 − [(1 − 𝐼𝑚𝑝𝑎𝑐𝑡𝐶𝑜𝑛𝑓) × (1 − 𝐼𝑚𝑝𝑎𝑐𝑡𝐼𝑛𝑡𝑒𝑔) × (1 − 𝐼𝑚𝑝𝑎𝑐𝑡𝐴𝑣𝑎𝑖𝑙)]

 And the Exploitability subscore is,

    8.22 × 𝐴𝑡𝑡𝑎𝑐𝑘𝑉𝑒𝑐𝑡𝑜𝑟 × 𝐴𝑡𝑡𝑎𝑐𝑘𝐶𝑜𝑚𝑝𝑙𝑒𝑥𝑖𝑡𝑦 × 𝑃𝑟𝑖𝑣𝑖𝑙𝑒𝑔𝑒𝑅𝑒𝑞𝑢𝑖𝑟𝑒𝑑 × 𝑈𝑠𝑒𝑟𝐼𝑛𝑡𝑒𝑟𝑎𝑐𝑡𝑖𝑜𝑛

*/

public class Main {
  public static void main(String[] args) {
    System.out.println("Randomized representation of a CVSS scoring.");
    // Generate if S is True or False
    // Print
    // Generate Impact scores
    // Print
    // Calculate SC
    // Generate AV, AC, PR, and UI
    // Print
    // Calculate Expoitability subscore
    // Calculate base score
    // Print 
  }
}
