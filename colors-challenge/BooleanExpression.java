public class BooleanExpression {
   public void eval () throws Up {}

   public void assert_ (boolean e) throws Up { if (!e) throw new Up(); }

   public boolean distinct (Variable a[]) {
      int count=0;
      Variable b;
      try { for (count=0 ; ; count++) b = a[count]; } 
      catch (ArrayIndexOutOfBoundsException e) {}
      for (int i=0 ; i < count-1 ; i++) {
         for (int j=i+1 ; j < count ; j++) {
            if (a[i].val().equals(a[j].val())) return false; 
         }
      }
      return true;
   }

   public boolean leftOf (Variable a, Variable b, Variable c[]) {
      return ((c[0].val().equals(a.val())) &&
	      ((c[1].val().equals(b.val())) ||
	       (c[2].val().equals(b.val())) ||
	       (c[3].val().equals(b.val())) ||
	       (c[4].val().equals(b.val())))) ||
	     ((c[1].val().equals(a.val())) &&
	      ((c[2].val().equals(b.val())) ||
	       (c[3].val().equals(b.val())) ||
	       (c[4].val().equals(b.val())))) ||
	     ((c[2].val().equals(a.val())) &&
	      ((c[3].val().equals(b.val())) ||
	       (c[4].val().equals(b.val())))) ||
	     ((c[3].val().equals(a.val())) &&
	      (c[4].val().equals(b.val())));
   }

   public boolean neighborOf (String a, String b, Variable c[]) {
      return ((c[0].val().equals(b)) &&
	      (c[1].val().equals(a))) ||
	     ((c[1].val().equals(b)) &&
	      ((c[0].val().equals(a)) ||
	       (c[2].val().equals(a)))) ||
	     ((c[2].val().equals(b)) &&
	      ((c[1].val().equals(a)) ||
	       (c[3].val().equals(a)))) ||
	     ((c[3].val().equals(b)) &&
	      ((c[2].val().equals(a)) ||
	       (c[4].val().equals(a)))) ||
	     ((c[4].val().equals(b)) &&
	      (c[3].val().equals(a)));
   }
}
