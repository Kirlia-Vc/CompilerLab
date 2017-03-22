package visitor;

import java.awt.Window.Type;

import minijava.TypeCheck;
import mytypes.MyClass;
import mytypes.MyGoal;
import mytypes.MySymbol;
import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.MainClass;

public class ClassVisitor extends DepthFirstVisitor{
	/* Class visitor:
		* 遍历所有的类，加入符号表，第一步不考虑继承关系
		* 检查类的重复定义
		*/
	/**
	    * f0 -> "class"
	    * f1 -> Identifier()
	    * f2 -> "{"
	    * f3 -> "public"
	    * f4 -> "static"
	    * f5 -> "void"
	    * f6 -> "main"
	    * f7 -> "("
	    * f8 -> "String"
	    * f9 -> "["
	    * f10 -> "]"
	    * f11 -> Identifier()
	    * f12 -> ")"
	    * f13 -> "{"
	    * f14 -> ( VarDeclaration() )*
	    * f15 -> ( Statement() )*
	    * f16 -> "}"
	    * f17 -> "}"
	    */
	   public void visit(MainClass n) {
		  String name=n.f1.getName();
		  MyClass tempClass=new MyClass(name,MySymbol.CLASS);
		  MyGoal.classMap.put(name, tempClass);
		  n.f0.accept(this);
	      n.f1.accept(this);
	      n.f2.accept(this);
	      n.f3.accept(this);
	      n.f4.accept(this);
	      n.f5.accept(this);
	      n.f6.accept(this);
	      n.f7.accept(this);
	      n.f8.accept(this);
	      n.f9.accept(this);
	      n.f10.accept(this);
	      n.f11.accept(this);
	      n.f12.accept(this);
	      n.f13.accept(this);
	      n.f14.accept(this);
	      n.f15.accept(this);
	      n.f16.accept(this);
	      n.f17.accept(this);
	   }
	   /**
	    * f0 -> "class"
	    * f1 -> Identifier()
	    * f2 -> "{"
	    * f3 -> ( VarDeclaration() )*
	    * f4 -> ( MethodDeclaration() )*
	    * f5 -> "}"
	    */
	   public void visit(ClassDeclaration n) {
		  String name=n.f1.getName();
		  if(MyGoal.classMap.get(name)!=null){
			  System.out.println("Error: class "+name+" is already declared");
			  return;
		  }
		  MyClass tempClass=new MyClass(name,MySymbol.CLASS);
		  MyGoal.classMap.put(name, tempClass);
	      n.f0.accept(this);
	      n.f1.accept(this);
	      n.f2.accept(this);
	      n.f3.accept(this);
	      n.f4.accept(this);
	      n.f5.accept(this);
	   }
	   
	   /**
	    * f0 -> "class"
	    * f1 -> Identifier()
	    * f2 -> "extends"
	    * f3 -> Identifier()
	    * f4 -> "{"
	    * f5 -> ( VarDeclaration() )*
	    * f6 -> ( MethodDeclaration() )*
	    * f7 -> "}"
	    */
	   public void visit(ClassExtendsDeclaration n) {
		  String name=n.f1.getName();
		  if(MyGoal.classMap.get(name)!=null){
			  System.out.println("Error: class "+name+" is already declared");
			  return;
		  }
		  MyClass tempClass=new MyClass(name,MySymbol.CLASS);
		  MyGoal.classMap.put(name, tempClass);
	      n.f0.accept(this);
	      n.f1.accept(this);
	      n.f2.accept(this);
	      n.f3.accept(this);
	      n.f4.accept(this);
	      n.f5.accept(this);
	      n.f6.accept(this);
	      n.f7.accept(this);
	   }
}
