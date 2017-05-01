package minijava;

import mytypes.*;
import syntaxtree.Node;
import visitor.GJBuildSymbolTable2;
import visitor.GJClassVisitor;
import visitor.GJMiniJava2Piglet;

import java.io.FileInputStream;

/**
 * Created by Vc on 2017/4/15.
 */
public class MiniJava2Piglet {
    public static MyGoal myGoal=new MyGoal();
    public static void main(String [] args) {
        try {
            //Node root = new MiniJavaParser(new FileInputStream("D:\\5teacher lab\\mylab\\TypeCheck\\src\\Factorial.java")).Goal();
            Node root = new MiniJavaParser(new FileInputStream("testcases/extend_test.java")).Goal();
            //System.out.println("Program parsed successfully");

            root.accept(new GJClassVisitor<Object>(),myGoal);
            root.accept(new GJBuildSymbolTable2(),myGoal);
            root.accept(new GJMiniJava2Piglet(),myGoal);
            //GJMiniJava2Piglet.BuildClass(class0,new MyFunc("",1,new MySymbol(),new MySymbol()));
            //assign every variable of father classes to this class.

            /*
            for(MyClass thisClass:myGoal.classMap.values()){
                MyClass fatherClass=(MyClass)thisClass.upper;
                while(fatherClass!=null){
                    for(Map.Entry<String, MyVar>entry : fatherClass.varMap.entrySet()){
                        //copy the variables from father class to child class
                        MyVar var=new MyVar(entry.getValue());
                        var.bias=thisClass.varBias;
                        thisClass.varBias++;
                        thisClass.varMap.put(entry.getKey(),var);
                    }
                    for(Map.Entry<String, MyFunc>entry : fatherClass.funcMap.entrySet()){
                        //copy the functions from father class to child class
                        MyFunc func=new MyFunc(entry.getValue());
                        func.bias=thisClass.funcBias;
                        thisClass.funcBias++;
                        thisClass.funcMap.put(entry.getKey(),func);
                    }
                    fatherClass=(MyClass)fatherClass.upper;
                }

            }


            for(MyClass value:myGoal.classMap.values()){
                System.out.println("class:"+value.name);
                for(MyVar var:value.varMap.values()){
                    System.out.println("    var:"+var.name+" "+var.varType.name+" "+var.bias);
                }
                for(MyFunc func:value.funcMap.values()){
                    System.out.println("  func:"+func.returnType.name+" "+func.name+" "+func.bias);
                    for(MySymbol type:func.argList){
                        System.out.println("    arg:"+type.name);
                    }
                    for(MyVar var:func.varMap.values()){
                        System.out.println("    var:"+var.name+" "+var.varType.name+" "+var.tempNo);
                    }
                }
            }
            */
            //System.out.println("Type checking passed.");

            //root.accept(new ClassExtendVisitor());
            //root.accept(new FunctionVisitor());
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
