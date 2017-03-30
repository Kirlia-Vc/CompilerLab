package visitor;

import minijava.MyException;
import minijava.MyOutput;
import minijava.TypeCheck;
import mytypes.*;
import syntaxtree.*;

/**
 * Created by Vc on 2017/3/21.
 */
public class GJTypeCheckVisitor extends GJDepthFirst<MySymbol, MySymbol> {
    /*
    GJTypeCheckVisitor:类型检查的主要visitor
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
     * @param argu MyGoal
     * @return null
     */
    public MySymbol visit(MainClass n, MySymbol argu) {
        MySymbol _ret=null;
        MyGoal myGoal=(MyGoal)argu;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        argu=myGoal.classMap.get(n.f1.getName());
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        argu=((MyClass)argu).funcMap.get("main");   //get to the "main" function
        n.f7.accept(this, argu);
        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        n.f10.accept(this, argu);
        //n.f11.accept(this, argu);     don't check String[] args, useless.
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
     * @return null
     */
    public MySymbol visit(ClassDeclaration n, MySymbol argu) {
        MySymbol _ret=null;
        MyGoal myGoal=(MyGoal)argu;
        argu=myGoal.classMap.get(n.f1.getName());
        n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
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
     * @param argu MyClass
     * @return null
     */
    public MySymbol visit(ClassExtendsDeclaration n, MySymbol argu) {
        MySymbol _ret=null;
        MyGoal myGoal=(MyGoal)argu;
        MyClass thisClass=myGoal.classMap.get(n.f1.getName());

        argu=thisClass;

        n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        //n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
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
     * @param argu MyClass
     * @return null
     */
    public MySymbol visit(MethodDeclaration n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol returnType=n.f1.getMyType();
        MyClass myClass=(MyClass)argu;
        MyFunc func=myClass.funcMap.get(n.f2.getName());
        argu=func;
        n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        //n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        //n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        MySymbol retVal=n.f10.accept(this, argu);
        MySymbol retValType=((MyVar)retVal).varType;
        //check if the return value type is compatible with the defined type
        while(!retValType.equals(returnType)){
            if(retValType.upper==null)
                throw new MyException(n.f2.getPos()+"return type is not equal with the defination, expected "
                        +returnType.name+", get "+((MyVar)retVal).varType.name);
            else
                retValType=retValType.upper;
        }

        n.f11.accept(this, argu);
        n.f12.accept(this, argu);
        return _ret;
    }

    /**
     * Expression
     * argu是当前函数名，返回值是expression的类型 - int/boolean/array/class
     * f0 -> AndExpression()
     *       | CompareExpression()
     *       | PlusExpression()
     *       | MinusExpression()
     *       | TimesExpression()
     *       | ArrayLookup()
     *       | ArrayLength()
     *       | MessageSend()
     *       | PrimaryExpression()
     *  @param argu MyFunc
     *  @return MyVar
     */
    public MySymbol visit(Expression n, MySymbol argu) {
        MySymbol _ret=null;
        _ret=n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * AndExpression:检查两个变量是否都为boolean
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     * @param argu MyFunc
     * @return MyVar
     */
    public MySymbol visit(AndExpression n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol varType1=n.f0.accept(this, argu);
        if(!MyVar.MyBoolean.isTypeEqual(varType1)){
            return null;
        }
        n.f1.accept(this, argu);
        MySymbol varType2=n.f2.accept(this, argu);
        if(!MyVar.MyBoolean.isTypeEqual(varType2)){
            return null;
        }
        return MyVar.MyBoolean;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     * @return MyInt
     */
    public MySymbol visit(IntegerLiteral n, MySymbol argu) {
        n.f0.accept(this, argu);
        return MyVar.MyInt;
    }

    /**
     * f0 -> "true"
     * @return MyBoolean
     */
    public MySymbol visit(TrueLiteral n, MySymbol argu) {
        n.f0.accept(this, argu);
        return MyVar.MyBoolean;
    }

    /**
     * f0 -> "false"
     * @return MyBoolean
     */
    public MySymbol visit(FalseLiteral n, MySymbol argu) {
        n.f0.accept(this, argu);
        return MyVar.MyBoolean;
    }

    /**
     * f0 -> <IDENTIFIER>
     *     check whether the method or variable is declared
     *     there could be 3 kinds of identifier:
     *     1) Variable
     *     2) Method
     *     3) Class
     *     We should know which kind of identifier we are expecting.
     */
    public MySymbol visit(Identifier n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        String name=n.getName();
        if(argu.expectingMethod){   //if we are expecting a method invocation here
            MyClass myClass=(MyClass)argu;
            while(myClass!=null){
                MyFunc func=myClass.funcMap.get(name);
                if(func!=null)
                    return func;
                myClass=(MyClass)myClass.upper; //didn't get a match, check the parent class.
            }
            //can't get any method
            MyOutput.error(n.getPos()+", method "+name+" is not declared");
            return _ret;
        }
        if(argu instanceof MyFunc){     //otherwise, expecting a variable here
            //check if it's a variable declared in the method
            MyFunc myFunc=(MyFunc)argu;
            MyVar var=myFunc.varMap.get(name);
            if(var!=null)
                return var;
            //if not, check if it's a variable declared in the class
            argu=argu.upper;
        }
        if(argu instanceof MyClass){
            MyClass myClass=(MyClass)argu;
            while(myClass!=null){
                MyVar var=myClass.varMap.get(name);
                if(var!=null)
                    return var;
                myClass=(MyClass)myClass.upper; //didn't get a match, check the parent class.
            }
            //can't get any variable matched
            MyOutput.error(n.getPos()+", variable "+name+" is not declared");
            return _ret;
        }
        if(argu instanceof MyGoal){     //expecting a class(e.g:T a=new T();)
            MyClass myClass= ((MyGoal) argu).classMap.get(name);
            if(myClass!=null)
                return myClass;
        }
        return _ret;
    }

    /**
     * f0 -> "this"
     * @return the "this" variable in the class.
     */
    public MySymbol visit(ThisExpression n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        MyClass myClass=(MyClass)argu.upper;
        return myClass.myThis;
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     * @param argu MyFunc
     * @return MyArray
     */
    public MySymbol visit(ArrayAllocationExpression n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        MySymbol exp=n.f3.accept(this, argu);
        if(!MyVar.MyInt.isTypeEqual(exp)){
            throw new MyException("line:"+n.f0.beginLine+": Expression is not an integer.");
        }
        n.f4.accept(this, argu);
        return MyVar.MyArray;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     * @param argu MyFunc
     * @return MyVar, an instance of the class.
     */
    public MySymbol visit(AllocationExpression n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        MySymbol classType=n.f1.accept(this, TypeCheck.myGoal);
        if(classType==null){
            MyOutput.error(n.f1.getPos()+"type "+n.f1.getName()+" not declared.");
            return _ret;
        }
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        MyVar var=((MyClass)classType).myThis;
        return var;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    public MySymbol visit(NotExpression n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        _ret=n.f1.accept(this, argu);
        if(!MyVar.MyBoolean.isTypeEqual(_ret)){
            //MyOutput.error("expected a boolean expression.");
            throw new MyException("line "+n.f0.beginLine+", expected a boolean expression before '!'.");
        }
        return _ret;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    public MySymbol visit(BracketExpression n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        _ret=n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }
    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    public MySymbol visit(CompareExpression n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol var1=n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol var2=n.f2.accept(this, argu);
        if(MyVar.MyInt.isTypeEqual(var1)&&MyVar.MyInt.isTypeEqual(var2))
            return MyVar.MyBoolean;
        throw new MyException("at line "+n.f1.beginLine+", variable not compatible in '<' expression, expected integer, get "
                +((MyVar)var1).varType.name+" and "+((MyVar)var2).varType.name);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    public MySymbol visit(PlusExpression n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol var1=n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol var2=n.f2.accept(this, argu);
        if(MyVar.MyInt.isTypeEqual(var1)&&MyVar.MyInt.isTypeEqual(var2))
            return MyVar.MyInt;
        throw new MyException("at line "+n.f1.beginLine+", variable not compatible in plus expression, expected integer, get "
                +((MyVar)var1).varType.name+" and "+((MyVar)var2).varType.name);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    public MySymbol visit(MinusExpression n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol var1=n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol var2=n.f2.accept(this, argu);
        if(MyVar.MyInt.isTypeEqual(var1)&&MyVar.MyInt.isTypeEqual(var2))
            return MyVar.MyInt;
        throw new MyException("at line "+n.f1.beginLine+", variable not compatible in minus expression, expected integer, get "
                +((MyVar)var1).varType.name+" and "+((MyVar)var2).varType.name);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    public MySymbol visit(TimesExpression n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol var1=n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol var2=n.f2.accept(this, argu);
        if(MyVar.MyInt.isTypeEqual(var1)&&MyVar.MyInt.isTypeEqual(var2))
            return MyVar.MyInt;
        throw new MyException("at line "+n.f1.beginLine+", variable not compatible in times expression, expected integer, get "
                +((MyVar)var1).varType.name+" and "+((MyVar)var2).varType.name);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    public MySymbol visit(ArrayLookup n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol array=n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol aInt=n.f2.accept(this, argu);
        if(MyVar.MyArray.isTypeEqual(array)&&MyVar.MyInt.isTypeEqual(aInt))
            return MyVar.MyInt;
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    public MySymbol visit(ArrayLength n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol arrayType=n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        if(MyVar.MyArray.isTypeEqual(arrayType))
            return MyVar.MyInt;
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */
    public MySymbol visit(MessageSend n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol object=n.f0.accept(this, argu);
        if(!(object instanceof MyVar)){
            throw new MyException("object is not a class instance.");
        }
        MySymbol objType=((MyVar) object).varType;
        if(!(objType instanceof MyClass)){
            throw new MyException("object is not a class instance.");
        }
        MyClass objClass=(MyClass)objType;
        objClass.expectingMethod=true;
        n.f1.accept(this, argu);
        MySymbol func=n.f2.accept(this, objClass);    //find a method from the class.
        objClass.expectingMethod=false;
        if(func==null||!(func instanceof MyFunc)){
            throw new MyException(n.f2.getPos()+"Can't find the expecting method");
        }
        n.f3.accept(this, argu);
        //build the argument table with the expressions
        MyArgs args=new MyArgs((MyFunc)argu);
        n.f4.accept(this, args);
        //now compare args with the function's argument list
        if(((MyFunc)func).argList.size()!=args.argList.size()){
            throw new MyException(n.f2.getPos()+"Argument list size is not matched");
        }
        for(int i=0;i<((MyFunc) func).argList.size();i++){
            MySymbol funcArg=((MyFunc) func).argList.get(i);
            MySymbol callArg=args.argList.get(i);
            while(!funcArg.equals(callArg)){
                if(callArg.upper!=null)
                    callArg=callArg.upper;
                else
                    throw new MyException(n.f2.getPos()+"argument "+i+" is not matched. Expected "+funcArg.name+", get " +
                        callArg.name+".");
                //return null;
            }
        }
        n.f5.accept(this, argu);
        return ((MyFunc)func).getReturnInstance();
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    public MySymbol visit(ExpressionList n, MySymbol argu) {
        MySymbol _ret=null;

        if(!(argu instanceof MyArgs)){
            throw new MyException("Unknown Error.");
        }
        MyVar var=(MyVar)n.f0.accept(this, argu);
        ((MyArgs)argu).addSymbol(var.varType);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    public MySymbol visit(ExpressionRest n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        MyVar var=(MyVar)n.f1.accept(this, argu);
        ((MyArgs)argu).addSymbol(var.varType);
        return _ret;
    }
    /**
     * f0 -> IntegerLiteral()
     *       | TrueLiteral()
     *       | FalseLiteral()
     *       | Identifier()
     *       | ThisExpression()
     *       | ArrayAllocationExpression()
     *       | AllocationExpression()
     *       | NotExpression()
     *       | BracketExpression()
     */
    public MySymbol visit(PrimaryExpression n, MySymbol argu) {
        MySymbol _ret=null;
        _ret=n.f0.accept(this, argu);
        return _ret;
    }
    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     * Already built the symbol table,don't do anything when encountering a variable declaration.
     */
    public MySymbol visit(VarDeclaration n, MySymbol argu) {
        return null;
    }
    /**
     * f0 -> Block()
     *       | AssignmentStatement()
     *       | ArrayAssignmentStatement()
     *       | IfStatement()
     *       | WhileStatement()
     *       | PrintStatement()
     */
    public MySymbol visit(Statement n, MySymbol argu) {
        MySymbol _ret=null;
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    public MySymbol visit(Block n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    public MySymbol visit(AssignmentStatement n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol var=n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol exp=n.f2.accept(this, argu);
        if(!(exp instanceof MyVar)||!(var instanceof MyVar)){
            //MyOutput.error("assignment result is not a variable");
            throw new MyException(n.f0.getPos()+"assignment result is not a variable");
        }
        while(!((MyVar)var).isTypeEqual(exp)){
            if(exp.upper!=null)
                exp=exp.upper;
            else
                throw new MyException(n.f0.getPos()+"assignment value type is incompatible");
        }
        n.f3.accept(this, argu);
        return MyVar.MyBoolean;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "["
     * f2 -> Expression()
     * f3 -> "]"
     * f4 -> "="
     * f5 -> Expression()
     * f6 -> ";"
     */
    public MySymbol visit(ArrayAssignmentStatement n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol array=n.f0.accept(this, argu);
        //not an array type
        if(!(array instanceof MyVar)||!((MyVar)array).isTypeEqual(MyVar.MyArray)){
            throw new MyException(n.f0.getPos()+n.f0.getName()+" is not an array variable");
        }
        n.f1.accept(this, argu);
        MySymbol index=n.f2.accept(this, argu);
        if(!(index instanceof MyVar)||!((MyVar)index).isTypeEqual(MyVar.MyInt)){
            throw new MyException(n.f0.getPos()+"array index is not an integer");
        }
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        MySymbol value=n.f5.accept(this, argu);
        if(!(value instanceof MyVar)||!((MyVar)value).isTypeEqual(MyVar.MyInt)){
            throw new MyException(n.f0.getPos()+"assignment value is not an integer");
        }
        n.f6.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "if"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     * f5 -> "else"
     * f6 -> Statement()
     */
    public MySymbol visit(IfStatement n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol condition=n.f2.accept(this, argu);
        if(!(condition instanceof MyVar)||!(((MyVar) condition).isTypeEqual(MyVar.MyBoolean)))
            throw new MyException("if condition is not boolean type");
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    public MySymbol visit(WhileStatement n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol condition=n.f2.accept(this, argu);
        if(!(condition instanceof MyVar)||!(((MyVar) condition).isTypeEqual(MyVar.MyBoolean)))
            throw new MyException("while condition is not boolean type");
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     *
     */
    public MySymbol visit(PrintStatement n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol exp=n.f2.accept(this, argu);
        if(!MyVar.MyInt.isTypeEqual(exp)){
            throw new MyException("at line "+n.f0.beginLine+", expected an integer value on printing" +
                    "expression.");
        }
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

}
