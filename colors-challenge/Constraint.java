public class Constraint implements Variable {
   Variable variable;
   BooleanExpression expression;

   public String val () { return null; }

   public Constraint (BooleanExpression exp, Variable var) { 
      expression = exp; 
      variable = var; 
   }

   public void checkit () throws Up, Out {
      expression.eval();
      try { variable.checkit(); } catch (Up up) {}
   }
}
