package visitor;

import minijava.MyException;
import minijava.MyOutput;
import mytypes.*;
import syntaxtree.*;

public class GJBuildSymbolTable extends GJDepthFirst<MySymbol, MySymbol>{
	/*
	 * GJBuildSymbolTable:建立符号表的第二步
	 * 检查Extend父类不存在、循环继承的错误
	 * 检查变量名重复定义的错误
	 * 检查变量类型未定义的错误
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
    public MySymbol visit(MainClass n, MySymbol argu) {
        MySymbol _ret=null;
        MyGoal myGoal=(MyGoal)argu;
        argu=myGoal.classMap.get(n.f1.getName());
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        MyFunc main=new MyFunc("main",MySymbol.FUNCTION,argu,MyBasicType.MyVoid);
        ((MyClass)argu).funcMap.put("main",main);
        argu=main;
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
    public MySymbol visit(ClassDeclaration n, MySymbol argu) {
        MySymbol _ret=null;
        MyGoal myGoal=(MyGoal)argu;
        argu=myGoal.classMap.get(n.f1.getName());
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
    public MySymbol visit(ClassExtendsDeclaration n, MySymbol argu) {
        MySymbol _ret=null;
        MyGoal myGoal=(MyGoal)argu;
        MyClass thisClass=myGoal.classMap.get(n.f1.getName());
        MyClass fatherClass=myGoal.classMap.get(n.f3.getName());
        //check if the father class is exist.
        if(fatherClass==null){
            throw new MyException("Father class "+n.f3.getName()+" is not exist.");
        }
        thisClass.upper=fatherClass;
        while(fatherClass!=null){
            if(fatherClass.equals(thisClass)){
                throw new MyException("Circulated Extension: "+n.f1.getName());
            }
            fatherClass=(MyClass)fatherClass.upper;
        }

        argu=thisClass;

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

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    public MySymbol visit(VarDeclaration n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol varType=n.f0.getMyType();
        //check if the type is declared

        if(varType==null){
            throw new MyException("Type identifier \""+n.f0.toIdentifier().getName()+"\" is not declared.");
        }
        MyVar var=new MyVar(n.f1.getName(),MySymbol.VAR,null,n.f0.getMyType());
        if(argu instanceof MyClass){    //the variable is defined in class
            MyClass myClass=(MyClass)argu;
            if(myClass.varMap.get(n.f1.getName())!=null){ //already defined
                throw new MyException(n.f1.getPos()+"variable \""+n.f1.getName()+"\" is already declared.");
            }
            myClass.varMap.put(n.f1.getName(),var);
        }
        else if(argu instanceof MyFunc){    //the variable is defined in a function
            MyFunc func=(MyFunc)argu;
            if(func.varMap.get(n.f1.getName())!=null){
                throw new MyException(n.f1.getPos()+"variable \""+n.f1.getName()+"\" is already declared.");
            }
            func.varMap.put(n.f1.getName(),var);
        }
        else{
            MyOutput.error("Unknown Error.");
        }
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
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
    public MySymbol visit(MethodDeclaration n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol returnType=n.f1.getMyType();
        //check if the type is declared
        if(returnType==null){
            throw new MyException(n.f1.toIdentifier().getPos()+"Type identifier \""+n.f1.toIdentifier().getName()+"\" is not declared.");
        }
        MyClass myClass=(MyClass)argu;
        //check if the function name is declared
        if(myClass.funcMap.get(n.f2.getName())!=null){
            throw new MyException(n.f2.getPos()+"Function \""+n.f2.getName()+"\"is already declared.");
        }
        MyFunc func=new MyFunc(n.f2.getName(),MySymbol.FUNCTION,myClass,returnType);
        argu=func;
        myClass.funcMap.put(n.f2.getName(),func);
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
        return _ret;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    public MySymbol visit(FormalParameter n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol varType=n.f0.getMyType();
        //check if the type is declared
        if(varType==null){
            throw new MyException("Type identifier \""+n.f0.toIdentifier().getName()+"\" is not declared.");
        }
        //check if the argument is declared
        MyFunc func=(MyFunc)argu;
        if(func.varMap.get(n.f1.getName())!=null){
            throw new MyException("Argument \""+n.f1.getName()+"\" is already declared.");
        }
        MyVar var=new MyVar(n.f1.getName(),MySymbol.VAR,func,varType);
        func.varMap.put(n.f1.getName(),var);
        func.argList.add(varType);
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }
}
