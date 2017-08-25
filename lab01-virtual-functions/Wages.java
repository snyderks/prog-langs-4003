// Lab 01
// Completed by Kristian Snyder

import java.io.*;
import java.util.*;

abstract class Employee {
   String name;
   
   Employee() {}
   Employee (String nm) { name = nm; }
   abstract double computePay();
   void   display () {}
   void   setHours(double hrs) {}
   void   setSales(double sales) {}
   void   setSalary(double salary) { System.out.println("NO!"); }
}

class WageEmployee extends Employee {
   double rate;
   double hours;

   WageEmployee(String nm) { super(nm); }
   WageEmployee(String nm, double r) { super(nm); rate = r; }
   void setRate(double r)    { rate  = r; }
   void setHours(double hrs) { hours = hrs; }
   double computePay()       { return rate*hours; }
}

class Programmer extends WageEmployee {
   Programmer (String nm, double w) { super(nm, w); }
   void display() {
      System.out.println("Name: "+name+"\tHours: "+hours
                          +"\tRate: "+rate);
   }
}

class SalesPerson extends WageEmployee {
   double commission;
   double SalesMade;

   SalesPerson (String nm, double c) {
      super(nm);
      commission = c; 
   }
   
   void setCommission(double comm) { commission = comm; }
   
   void setSales(double sales) { SalesMade = sales; }
	
   double computePay() { return commission*SalesMade; }
	
   void display() {
      System.out.println("Name: "+name+"\tCommission: "+commission
                          +"\tSales: "+SalesMade);
   }
}

class Manager extends Employee {
   double monthlysalary;

   Manager () { super(""); }
	
   Manager(String nm, double w)  { super(nm); monthlysalary = w; }
	
   void setSalary(double salary) { monthlysalary = salary; }
	
   double computePay()           { return monthlysalary; }
	
   void display() {
      System.out.println("Name: "+name+"\tMonthly Salary: "+
                          monthlysalary);
   }
}

interface ManagerInterface {
   double managerComputePay();
   void managerDisplay();
}

class SalesManager extends SalesPerson implements ManagerInterface {
   double monthlysalary;
   
   SalesManager(String nm, double w) { super (nm, w); }
   
   public double managerComputePay() {
      return monthlysalary;
   }
	
   double computePay() {
      System.out.println("SalesManager: " + name + " " +
			 (super.computePay()+managerComputePay()));
      return super.computePay() + managerComputePay();
   }

   void setSalary (double s) { monthlysalary = s; }
	
   public void managerDisplay() {
      System.out.println("Name: "+name+"\tMonthly Salary: "+monthlysalary);
   }

   void display() {
      super.display();
      managerDisplay();
   }
}

// EmployeeList contains a list of Employees
// and various ways to manage that list and
// retrieve information about the employees
// within from it.
class EmployeeList {
    List<Employee> emps;
  
    EmployeeList() {
        emps = new ArrayList<Employee>();
    }
  
    // find searches the list of employees to find
    // one with a matching name as the input. If one
    // exists, it will be returned, otherwise null
    // is returned.
    public Employee find(String nm) {
        // Search through the list of employees
        for (Employee e : emps) {
          if (e.name == nm) {
            return e;
          }
        }
    
        // Didn't find one. Return null.
        return null;
    }
    
    // enqueue adds an Employee to the list.
    public void enqueue(Employee e) {
        emps.add(e);
    }
  
    // setHours looks in the list of employees
    // and, if any name matches nm, sets that
    // employee's hours to hrs.
    public void setHours(String nm, double hrs) {
        // find the matching employee
        Employee emp = find(nm);
        if (emp != null) {
            emp.setHours(hrs);    
        }
    }
  
    // setSalary looks in the list of employees
    // and, if any name matches nm, sets that
    // employee's salary.
    public void setSalary(String nm, double salary) {
        // find the matching employee
        Employee emp = find(nm);
        if (emp != null) {
            emp.setSalary(salary);    
        }
    }
  
    // setSales looks in the list of employees
    // and, if any name matches nm, sets that
    // employee's sales to sales.
    public void setSales(String nm, double sales) {
        // find the matching employee
        Employee emp = find(nm);
        if (emp != null) {
            emp.setSales(sales);    
        }
    }
    
    // payroll calculates the total payroll of all
    // employees in the list.
    double payroll() {
        double sum = 0;
        
        // Run through all the employees
        for (Employee e : emps) {
            // Get what the employee's pay is
            sum += e.computePay();
        }
        
        return sum;
    }
    
    // display displays each employee in the list.
    void display() {
        for (Employee e : emps) {
            e.display();
        }
    }
}

public class Wages {
   public static void main(String argv[]) {
      EmployeeList emp = new EmployeeList();
      emp.enqueue(new SalesManager("Gee", 1000));
      emp.enqueue(new SalesManager("Gal", 1000));
      emp.enqueue(new SalesManager("Gem", 1000));
      emp.enqueue(new SalesPerson("John", 0.03));
      emp.enqueue(new SalesPerson("Joan", 0.04));
      emp.enqueue(new SalesPerson("Jack", 0.02));
      emp.enqueue(new Manager("Fred", 10000));
      emp.enqueue(new Manager("Frank", 5000));
      emp.enqueue(new Manager("Florence", 3000));
      emp.enqueue(new Programmer("Linda", 7));
      emp.enqueue(new Programmer("Larry", 5));
      emp.enqueue(new Programmer("Lewis", 3));
		
      emp.setHours("Linda", 35);
      emp.setHours("Larry", 23);
      emp.setHours("Lewis", 3);
      emp.setSales("John", 12000);
      emp.setSales("Joan", 10000);
      emp.setSales("Jack", 5000);
      emp.setSales("Gee", 4000);
      emp.setSales("Gal", 3000);
      emp.setSales("Gem", 2000);
      emp.setSalary("Gee", 1000);
      emp.setSalary("Gal", 2000);
      emp.setSalary("Gem", 3000);
      emp.display();
      System.out.println("Payroll: "+emp.payroll());
   }
}