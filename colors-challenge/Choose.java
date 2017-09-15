import java.util.*;

public class Choose implements Variable {
   Variable next_for;
   String [] strs;
	int n;
   String value;
   
   public Choose(String s, Variable next_for) {  
      this.next_for = next_for;
      StringTokenizer t = new StringTokenizer(s," ");
      n = t.countTokens();
      strs = new String[n];
      for (int i=0 ; i < n ; i++) strs[i] = t.nextToken();
   }

   public String val () { return value; }

   public void checkit () throws Up, Out {
      for (int i=0 ; i < n ; i++) {
         value = strs[i];
         try {  next_for.checkit();  } catch (Up up) { }
      }
      throw new Up();
   }
}
