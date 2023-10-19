import java.util.ArrayList;

import jminusminus.CLEmitter;

import static jminusminus.CLConstants.*;

/**
 * This class programmatically generates the class file for the following Java application:
 * 
 * <pre>
 * public class IsPrime {
 *     // Entry point.
 *     public static void main(String[] args) {
 *         int n = Integer.parseInt(args[0]);
 *         boolean result = isPrime(n);
 *         if (result) {
 *             System.out.println(n + " is a prime number");
 *         } else {
 *             System.out.println(n + " is not a prime number");
 *         }
 *     }
 *
 *     // Returns true if n is prime, and false otherwise.
 *     private static boolean isPrime(int n) {
 *         if (n < 2) {
 *             return false;
 *         }
 *         for (int i = 2; i <= n / i; i++) {
 *             if (n % i == 0) {
 *                 return false;
 *             }
 *         }
 *         return true;
 *     }
 * }
 * </pre>
 */
public class GenIsPrime {
    public static void main(String[] args) {
        // Create a CLEmitter instance
        CLEmitter e = new CLEmitter(true);

        // Creates an ArrayList instance to store modifiers
        ArrayList<String> modifiers = new ArrayList<String>();

        // Public Class IsPrime
        modifiers.add("public");
//        // Below might need true boolean
        e.addClass(modifiers, "IsPrime", "java/lang/Object", null, true);

        // public static void main(String[] args){
        modifiers.clear();
        modifiers.add("public");
        modifiers.add("static");
// may be true
        e.addMethod(modifiers, "main", "([Ljava/lang/String;)V", null, true);


        // int n = Interger.parseInt(arg[0]);
        // ALOAD_0 gets the arrary reference on top of the stack and puts index zero on stack
        e.addNoArgInstruction(ALOAD_0);
        //e.addNoArgInstruction(ISTORE_0); // Stores n
        e.addNoArgInstruction(ICONST_0);
        // pops 0 from stack and puts arg[0] on stack
        e.addNoArgInstruction(AALOAD);
        // Used INVOKESTATIC because parseInt is a static method
        // "java/lang/Integer" is where it belongs
        // This is the descripter: "([Ljava/lang/String;)I" : takes a String and returns an Integer
        e.addMemberAccessInstruction(INVOKESTATIC, "java/lang/Integer", "parseInt",
                "([Ljava/lang/String;)I");
        e.addNoArgInstruction(ISTORE_1); // Stores n


        // int result = IsPrime(n);
        e.addNoArgInstruction(ILOAD_1); // Loads n
        // "(I)Z" is used because isPrime() takes an int and returns a boolean
        e.addMemberAccessInstruction(INVOKESTATIC, "IsPrime", "isPrime", "(I)Z");
        e.addNoArgInstruction(ISTORE_2); // Stores result
        // System.out.println(n + " is " + result);

// Need to create if else clause in this section below

        // Get System.out on stack
        e.addMemberAccessInstruction(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

        // Create an instance (say sb) of StringBuffer on stack for
        //      sb = new StringBuffer();
        // NEW is used to create objects
        e.addReferenceInstruction(NEW, "java/lang/StringBuffer");
        // DUP duplicates buffer onto stack
        e.addNoArgInstruction(DUP);
        // INVOKESPECIAL is used for constructors
        // INVOKEVIRTURL is for instance methods
        // the name of any constructor is "<init>"
        // "()V" is used because there is no argument and it returns nothing (V is for void)
        e.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/StringBuffer", "<init>", "()V");

        // sb.append(n);
        e.addNoArgInstruction(ILOAD_1);
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer", "append",
                "(I)Ljava/lang/StringBuffer;");

        e.addNoArgInstruction(ILOAD_2);
        e.addBranchInstruction(IFEQ, "not prime"); // if not prime, jump to lable

        // sb.append(" is a prime number")
        e.addNoArgInstruction(ILOAD_1); // load n
        e.addLDCInstruction(" is a prime number");
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
 //       e.addBranchInstruction(GOTO, "exit");


 //       e.addLabel("not prime");
        // sb.append(" is not a prime number");
 //       e.addLDCInstruction(" is not a prime number");
 //       e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer", "append",
 //               "(Ljava/lang/String;)Ljava/lang/StringBuffer;");

        // sb.append(result);
 //       e.addLabel("exit");
  //      e.addNoArgInstruction(ILOAD_2);
  //      e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer", "append",
   //             "(I)Ljava/lang/StringBuffer;");

        // System.out.println(sb.toString());
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer", "toString",
                "()Ljava/lang/String;");
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/io/PringStream", "println",
                "(Ljava/lang/String;)V");

        // return;
        e.addNoArgInstruction(RETURN);

/**       if (n < 2) {
 *             return false;
 *         }
 *         for (int i = 2; i <= n / i; i++) {
 *             if (n % i == 0) {
 *                 return false;
 *             }
 *         }
 *         return true;
 */

        // private static boolean isPrime(int n)
        modifiers.clear();
        modifiers.add("private");
        modifiers.add("static");
        // may need to be set to true
        e.addMethod(modifiers, "isPrime", "(I)Z", null, true);

        // if (n < 2) branch to "Loop"
        // puts n on stack
        e.addNoArgInstruction(ILOAD_0);
        // Puts a 2 on top of the stack
//        e.addLDCInstruction(2);
        e.addNoArgInstruction(ICONST_2);
        // if n >= 2 go to "loop" instruction
        e.addBranchInstruction(IF_ICMPGE, "Loop");

        // Base case: if n < 2 then return false
        // 0 == false
        e.addNoArgInstruction(ICONST_0);
        e.addNoArgInstruction(IRETURN);

        // Loop case: for (int i = 2; i <= n / i; i++)
        e.addLabel("Loop");
        // Loop initialization int i = 2;
        e.addNoArgInstruction(ICONST_2);
        e.addNoArgInstruction(ISTORE_1); // Stores i = 2
        e.addNoArgInstruction(ILOAD_0);  // Loads n
        e.addNoArgInstruction(ILOAD_1);  // Loads i

        // if n/i < i then exit loop
        e.addNoArgInstruction(IDIV);
        e.addBranchInstruction(IF_ICMPLT, "Exit");

        // if n % i != 0 go to "Increment"
        e.addNoArgInstruction(ILOAD_0);
        e.addNoArgInstruction(ILOAD_1);
        e.addNoArgInstruction(IREM);
        e.addNoArgInstruction(ICONST_0);
        e.addBranchInstruction(IF_ICMPNE, "Increment");

        // else return false
        // 0 == false
        e.addNoArgInstruction(0);
        e.addNoArgInstruction(IRETURN);

        // increments i and returns to start of Loop
        e.addLabel("Increment");
        e.addNoArgInstruction(ILOAD_1);
        e.addNoArgInstruction(ICONST_1);
        e.addNoArgInstruction(IADD);
        e.addNoArgInstruction(ISTORE_1);
        e.addBranchInstruction(GOTO, "LOOP");

        // if i > n then return true
        e.addLabel("Exit");
        // 1 == true
        e.addNoArgInstruction(1);
        e.addNoArgInstruction(IRETURN);

        // Write Factorial.class to file system
        e.write();
    }
}

