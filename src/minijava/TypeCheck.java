package minijava;

import mytypes.*;
import syntaxtree.Node;
import visitor.GJBuildSymbolTable;
import visitor.GJClassVisitor;
import visitor.GJTypeCheckVisitor;

import java.io.FileInputStream;

public class TypeCheck {
    public static MyGoal myGoal=new MyGoal();
    public static void main(String [] args) {
      try {
         //Node root = new MiniJavaParser(new FileInputStream("D:\\5teacher lab\\mylab\\TypeCheck\\src\\Factorial.java")).Goal();
    	 Node root = new MiniJavaParser(new FileInputStream("testcases/extend_test.java")).Goal(); 
    	 System.out.println("Program parsed successfully");

         root.accept(new GJClassVisitor<Object>(),myGoal);
         root.accept(new GJBuildSymbolTable(),myGoal);
         for(MyClass value:myGoal.classMap.values()){
             System.out.println("class:"+value.name);
             for(MyVar var:value.varMap.values()){
                 System.out.println("    var:"+var.name+" "+var.varType.name);
             }
             for(MyFunc func:value.funcMap.values()){
                 System.out.println("  func:"+func.returnType.name+" "+func.name);
                 for(MySymbol type:func.argList){
                     System.out.println("    arg:"+type.name);
                 }
                 for(MyVar var:func.varMap.values()){
                     System.out.println("    var:"+var.name+" "+var.varType.name);
                 }
             }
         }
         root.accept(new GJTypeCheckVisitor(),myGoal);
         System.out.println("Type checking passed.");
         //root.accept(new ClassExtendVisitor());
         //root.accept(new FunctionVisitor());
      }
      catch (Exception e) {
         System.out.println(e.toString());
      }
   }
}
