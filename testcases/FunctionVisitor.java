package visitor;

import minijava.MyIdentifier;
import minijava.TypeCheck;
import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.FormalParameter;
import syntaxtree.FormalParameterList;
import syntaxtree.FormalParameterRest;
import syntaxtree.Identifier;
import syntaxtree.MainClass;
import syntaxtree.MethodDeclaration;
import syntaxtree.VarDeclaration;

public class FunctionVisitor extends DepthFirstVisitor{
	/*
	 *
	 */
	MyIdentifier tempClass;
	MyIdentifier tempFunc;
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
		  tempClass=n.f1.getMyIdentifier();
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
	      tempClass=null;
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
		  tempClass=n.f1.getMyIdentifier();
	      n.f0.accept(this);
	      n.f1.accept(this);
	      n.f2.accept(this);
	      n.f3.accept(this);
	      n.f4.accept(this);
	      n.f5.accept(this);
	      tempClass=null;
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
		  tempClass=n.f1.getMyIdentifier();
	      n.f0.accept(this);
	      n.f1.accept(this);
	      n.f2.accept(this);
	      n.f3.accept(this);
	      n.f4.accept(this);
	      n.f5.accept(this);
	      n.f6.accept(this);
	      n.f7.accept(this);
	      tempClass=null;
	   }
	/**
	    * f0 -> "public"
	    * f1 -> Type()
	    * f2 -> Identifier()
	    * f3 -> "("
	    * f4 -> ( FormalParameterList() )?
	    * f5 -> ")"
	    * f6 -> "{"
	    * f7 -> ( VarDeclaration() )*
	    * f8 -> ( Statement() )*
	    * f9 -> "return"
	    * f10 -> Expression()
	    * f11 -> ";"
	    * f12 -> "}"
	    */
	   public void visit(MethodDeclaration n) {
	      n.f0.accept(this);
	      if(n.f1.isIdentifier()){//should be defined in class
	    	  Identifier id=n.f1.toIdentifier();
	    	  MyIdentifier argu=TypeCheck.argumentMap.get(id.f0.tokenImage);
	    	  if(argu==null){
	    		  System.out.println("Error:Return type undefined.");
	    		  return;
	    	  }
	      }
	      System.out.println("func:"+n.f1.f0.which+" "+n.f2.f0.tokenImage);
	      n.f1.accept(this);
	      //MyIdentifier methodName=
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
	   }

	   /**
	    * f0 -> FormalParameter()
	    * f1 -> ( FormalParameterRest() )*
	    */
	   public void visit(FormalParameterList n) {
	      n.f0.accept(this);
	      n.f1.accept(this);
	   }

	   /**
	    * f0 -> Type()
	    * f1 -> Identifier()
	    */
	   public void visit(FormalParameter n) {
	      n.f0.accept(this);
	      n.f1.accept(this);
	   }

	   /**
	    * f0 -> ","
	    * f1 -> FormalParameter()
	    */
	   public void visit(FormalParameterRest n) {
	      n.f0.accept(this);
	      n.f1.accept(this);
	   }
	   
	   /**
	    * f0 -> Type()
	    * f1 -> Identifier()
	    * f2 -> ";"
	    */
	   public void visit(VarDeclaration n) {
		  System.out.println(n.f0.f0.which+" "+n.f1.f0.tokenImage);
	      n.f0.accept(this);
	      n.f1.accept(this);
	      n.f2.accept(this);
	   }
}
