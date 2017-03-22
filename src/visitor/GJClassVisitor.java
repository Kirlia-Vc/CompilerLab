package visitor;

import minijava.MyOutput;
import mytypes.MyClass;
import mytypes.MyGoal;
import mytypes.MySymbol;
import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.MainClass;

public class GJClassVisitor<R> extends GJDepthFirst<R,MySymbol>{
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
	   public R visit(MainClass n,MySymbol argu) {
	      R _ret=null;
	      String name=n.f1.getName();
		  MyClass tempClass=new MyClass(name,MySymbol.CLASS);
		  MyGoal myGoal=(MyGoal)argu;
		  myGoal.classMap.put(name, tempClass);
	      n.f0.accept(this, argu);
	      n.f1.accept(this, argu);
	      n.f2.accept(this, argu);
	      n.f3.accept(this, argu);
	      n.f4.accept(this, argu);
	      n.f5.accept(this, argu);
	      n.f6.accept(this, argu);
	      n.f7.accept(this, argu);
	      n.f8.accept(this, argu);
	      n.f9.accept(this, argu);
	      n.f10.accept(this, argu);
	      n.f11.accept(this, argu);
	      n.f12.accept(this, argu);
	      n.f13.accept(this, argu);
	      n.f14.accept(this, argu);
	      n.f15.accept(this, argu);
	      n.f16.accept(this, argu);
	      n.f17.accept(this, argu);
	      return _ret;
	   }
	   /**
	    * f0 -> "class"
	    * f1 -> Identifier()
	    * f2 -> "{"
	    * f3 -> ( VarDeclaration() )*
	    * f4 -> ( MethodDeclaration() )*
	    * f5 -> "}"
	    */
	   public R visit(ClassDeclaration n, MySymbol argu) {
	      R _ret=null;
	      MyGoal myGoal=(MyGoal)argu;
	      String name=n.f1.getName();
		  if(myGoal.classMap.get(name)!=null){
			  MyOutput.error("class "+name+" is already declared");
			  return _ret;
		  }
		  MyClass tempClass=new MyClass(name,MySymbol.CLASS);
		  myGoal.classMap.put(name, tempClass);
	      n.f0.accept(this, argu);
	      n.f1.accept(this, argu);
	      n.f2.accept(this, argu);
	      n.f3.accept(this, argu);
	      n.f4.accept(this, argu);
	      n.f5.accept(this, argu);
	      return _ret;
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
	   public R visit(ClassExtendsDeclaration n, MySymbol argu) {
	      R _ret=null;
	      String name=n.f1.getName();
	      MyGoal myGoal=(MyGoal)argu;
		  if(myGoal.classMap.get(name)!=null){
			  MyOutput.error("class "+name+" is already declared");
			  return _ret;
		  }
		  MyClass tempClass=new MyClass(name,MySymbol.CLASS);
		  myGoal.classMap.put(name, tempClass);
	      n.f0.accept(this, argu);
	      n.f1.accept(this, argu);
	      n.f2.accept(this, argu);
	      n.f3.accept(this, argu);
	      n.f4.accept(this, argu);
	      n.f5.accept(this, argu);
	      n.f6.accept(this, argu);
	      n.f7.accept(this, argu);
	      return _ret;
	   }
}
