package visitor;

import minijava.MyIdentifier;
import minijava.TypeCheck;
import syntaxtree.ClassExtendsDeclaration;

public class ClassExtendVisitor extends DepthFirstVisitor{
	/*ClassExtendVisitor:
	 * 查找继承错误的问题，包括循环继承、继承不存在的类
	 */
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
		  String name=n.f1.f0.tokenImage;
		  MyIdentifier tempClass=TypeCheck.argumentMap.get(name);
		  MyIdentifier fatherClass=TypeCheck.argumentMap.get(n.f3.f0.tokenImage);
		  if(fatherClass==null){
			  System.out.println("Error:Father class is not exist.");
			  return;
		  }
		  tempClass.upper=fatherClass;
		  while(fatherClass.upper!=null){
			  fatherClass=fatherClass.upper;
			  if(fatherClass.equals(tempClass)){
				  System.out.println("Error:circulated extension.");
				  return;
			  }
		  }
		  
		  //TypeCheck.argumentMap.put(name, tempClass);
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
