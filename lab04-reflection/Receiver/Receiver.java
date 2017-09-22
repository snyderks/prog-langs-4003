// Lab 4 - Reflection
// Completed by Kristian Snyder

package Receiver;

import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

class GetClass extends ClassLoader implements Runnable {
   String classname;
   Thread runner = null;
   Receiver rec = null;
   Method [] methlst;
   Method meth = null;
   Object obj = null;
   Class <?> cls = null;
   Hashtable <String,Class> classes = null;
   Hashtable <String,Method> methods = null;
   Hashtable <String,Object> objects = null;

   public GetClass (Receiver rec) { 
      this.rec = rec; 
      classes = new Hashtable <String,Class> ();
      methods = new Hashtable <String,Method> ();
      objects = new Hashtable <String,Object> ();
   }

   public void start () {
      if (runner == null) runner = new Thread(this);
      runner.start();
      rec.status.setText("Waiting for a class to arrive");
   }

   public void run () {
      byte [] classbytes = null;
      try {
         ServerSocket server = new ServerSocket(8670);
         while (true) {
            try {
               Socket socket = server.accept();
               InputStream ins = socket.getInputStream();
               ObjectInputStream in = new ObjectInputStream(ins);
               PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

               classbytes = (byte [])in.readObject();
	       try {
		  cls = defineClass(null, classbytes, 0, classbytes.length);
		  classes.put(cls.getName(),cls);
		  rec.status.setText("New class "+cls.getName()+" has arrived");
	       } catch (LinkageError e) {
		  // We have received this class already
		  String str = e.toString();
		  String clsname = 
		     str.substring(str.lastIndexOf(':')+3,str.length()-1);
		  clsname = clsname.replace('/','.');
		  cls = classes.get(clsname);
		  rec.status.setText("Using old class "+cls.getName());
	       }
               Constructor <?> ct = cls.getConstructor();
               obj = ct.newInstance();
               if (classes.get(cls.getName()) == null) {
		  objects.put(classname, obj);
	       }
            } catch (IllegalAccessException e) {
               rec.status.setText("Class "+classname+" not accessible");
            } catch (InstantiationException e) {
               rec.status.setText("Class "+classname+" instantiation failed");
            } catch (NoSuchMethodException e) {
               rec.status.setText("No default constructor in class "+classname);
            } catch (ClassFormatError e) {
               rec.status.setText("Class "+classname+" is not a valid class");
            } catch (Exception e) { 
               rec.status.setText(e.toString()); 
            }
         }
      } catch (Exception f) {
         rec.status.setText(f.toString());
      }
   }
}

public class Receiver extends JFrame implements ActionListener {
   JButton b;
   JTextField status = null;
   JTextField height = null;
   JTextField width = null;
   GetClass gc = null;
   JLabel label;

   public Receiver () {
      setLayout(new BorderLayout());
      JPanel p = new JPanel();
      p.setLayout(new FlowLayout(FlowLayout.LEFT));
      p.add(label = new JLabel("Receiver"));
      p.add(new JLabel("         "));
      p.add(b = new JButton("Start the Applet"));
      add("North",p);
      add("South",status = new JTextField());
      // Initialize height and width boxes for the user
      p.add(new JLabel("Width"));
      p.add(width = new JTextField(5));
      p.add(new JLabel("Height"));
      p.add(height = new JTextField(5));
      status.setEditable(false);
      b.addActionListener(this);
      label.setForeground(Color.blue);
      label.setFont(new Font("TimesRoman",Font.BOLD,18));
      (gc = new GetClass(this)).start();
   }

   public void actionPerformed (ActionEvent evt) {
      try {
         if (evt.getSource() == b && 
             width.getText().length() > 0 && 
             height.getText().length() > 0) {
            Class <?> frame = gc.cls.getSuperclass();
            Method size = frame.getMethod("setSize", int.class, int.class);
            Method visible = frame.getMethod("setVisible", boolean.class); 
            size.invoke(gc.obj, new Object [] { 
                Integer.parseInt(width.getText()), 
                Integer.parseInt(height.getText()) 
            });
            visible.invoke(gc.obj, true);
         }
      } catch (Exception e) {
         status.setText(e.toString());
      }
   }
}
