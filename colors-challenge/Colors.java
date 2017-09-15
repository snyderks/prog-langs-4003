import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;

// Color coutries of Europe
//   Color countries of Europe so no two bordering countries have 
//   the same color.
class ColorPuzzle extends Puzzle implements Variable {
   ColorFrame problem;
   Variable portugal, spain, germany, austria, italy, switzerland,
      france, belgium, luxemburg, holland;

   public ColorPuzzle (ColorFrame problem) {  this.problem = problem; }
	
   public void checkit () throws Up, Out {
      assert_(!portugal.val().equals(spain.val()));
      assert_(!spain.val().equals(france.val()));
      assert_(!france.val().equals(italy.val()));
      assert_(!france.val().equals(switzerland.val()));
      assert_(!france.val().equals(belgium.val()));
      assert_(!france.val().equals(germany.val()));
      assert_(!france.val().equals(luxemburg.val()));
      assert_(!belgium.val().equals(holland.val()));
      assert_(!belgium.val().equals(germany.val()));
      assert_(!belgium.val().equals(luxemburg.val()));
      assert_(!holland.val().equals(germany.val()));
      assert_(!germany.val().equals(switzerland.val()));
      assert_(!germany.val().equals(austria.val()));
      assert_(!germany.val().equals(luxemburg.val()));
      assert_(!italy.val().equals(switzerland.val()));
      assert_(!italy.val().equals(austria.val()));
      assert_(!switzerland.val().equals(austria.val()));
      // If all assertions are true, print the colors
      problem.out.append(
          " Portugal = "    +portugal.val()    +"\n"+
          " Spain = "       +spain.val()       +"\n"+
          " France = "      +france.val()      +"\n"+
          " Belgium = "     +belgium.val()     +"\n"+
          " Holland = "     +holland.val()     +"\n"+
          " Germany = "     +germany.val()     +"\n"+
          " Luxemburg = "   +luxemburg.val()   +"\n"+
          " Italy = "       +italy.val()       +"\n"+
          " Switzerland = " +switzerland.val() +"\n"+
          " Austria = "     +austria.val()     +"\n");
      throw new Out();
   }

   public void solve () {
      portugal    = new Choose("red green blue yellow", this);
      spain       = new Choose("red green blue yellow", portugal);
      france      = new Choose("red green blue yellow", spain);
      belgium     = new Choose("red green blue yellow", france);
      holland     = new Choose("red green blue yellow", belgium);
      germany     = new Choose("red green blue yellow", holland);
      luxemburg   = new Choose("red green blue yellow", germany);
      italy       = new Choose("red green blue yellow", luxemburg);
      switzerland = new Choose("red green blue yellow", italy);
      austria     = new Choose("red green blue yellow", switzerland);
      try { austria.checkit(); } 
      catch (Out out) { 
         problem.out.append(" Done\n"); 
      }
      catch (Up up) { 
         problem.out.append(" No solution found\n"); 
      }
   }
}

class ColorFrame extends JFrame implements ActionListener {
   JTextArea out;
   JButton button;
	
   public ColorFrame () {
      setLayout(new BorderLayout());
      setBackground(new Color(255,255,224));
      add("Center", new JScrollPane(out = new JTextArea()));
      JPanel p = new JPanel();
      p.setLayout(new FlowLayout());
      p.add(button = new JButton("Press me"));
      p.setBackground(new Color(255,255,223));
      add("South", p);
      button.addActionListener(this);
      out.setFont(new Font("TimesRoman", Font.PLAIN, 18));
   }
	
   public void doit () {
      ColorPuzzle puzzle = new ColorPuzzle(this);
      puzzle.solve();
   }
	
   public void actionPerformed (ActionEvent evt) {  doit();  }
}

public class Colors extends Applet implements ActionListener {
	JButton go;
	
	public void init () {
		setLayout(new BorderLayout());
		add("Center",go = new JButton("Applet"));
		go.addActionListener(this);
	}
	
	public void actionPerformed (ActionEvent evt) {
		ColorFrame cf = new ColorFrame();
		cf.setSize(600,600);
		cf.setVisible(true);
	}
}

		  
	
