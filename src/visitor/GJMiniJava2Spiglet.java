package visitor;

import minijava.MiniJava2Piglet;
import minijava.MyOutput;
import mytypes.*;
import syntaxtree.*;

import java.util.ArrayList;

/**
 * Created by Vc on 2017/4/14.
 */
public class GJMiniJava2Spiglet extends GJDepthFirst<MySymbol,MySymbol>{
    private static String HLOAD=" HLOAD ";
    private static String TEMP(int i){
        return " TEMP "+SV(i);
    }
    private static String L(int i){
        return " L"+String.valueOf(i)+" NOOP ";
    }
    private static String HSTORE=" HSTORE ";
    private static String HALLOCATE=" HALLOCATE ";
    private static String TIMES=" TIMES ";
    private static String LT=" LT ";
    private static String PLUS=" PLUS ";
    private static String MINUS=" MINUS ";
    private static String MOVE=" MOVE ";
    private static String CJUMP=" CJUMP ";
    private static String JUMP=" JUMP ";
    private static String BEGIN=" BEGIN ";
    private static String RETURN=" RETURN ";
    private static String END=" END ";
    private static String CALL=" CALL ";
    public int morethan20=0;
    public int morethan20temp=0;
    public static String SV(int i){
        return " "+String.valueOf(i)+" ";
    }
    private int getTemp(MySymbol argu){
        MyFunc func=(MyFunc)argu;
        return func.tempNo++;
    }
    int global_temp=20;
    int global_label=1;
    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    public MySymbol visit(Goal n, MySymbol argu){
        MySymbol _ret=null;
        MyOutput.println("MAIN");
        n.f0.accept(this, argu);
        MyOutput.println("END");
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }
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
        MyClass myClass=myGoal.classMap.get(n.f1.getName());
        argu=myClass.funcMap.get("main");
        n.f0.accept(this, argu);
        //n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        n.f7.accept(this, argu);
        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        n.f10.accept(this, argu);
        //n.f11.accept(this, argu);
        n.f12.accept(this, argu);
        n.f13.accept(this, argu);
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
        n.f0.accept(this, argu);
        MyGoal myGoal=(MyGoal)argu;
        MyClass thisClass=myGoal.classMap.get(n.f1.getName());
        argu=thisClass;
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
        MySymbol _ret = null;
        global_temp=20;
        if(argu instanceof MyClass) {
            String funcName = n.f2.getName();
            MyFunc func = ((MyClass) argu).funcMap.get(funcName);
            MyOutput.println(((MyClass)argu).name+"_"+funcName+" [ "+String.valueOf(func.argList.size()+1)+" ] ");
            argu=func;
        }
        MyOutput.println(BEGIN);
        n.f8.accept(this, argu);
        n.f9.accept(this, argu);
        MySymbol ret=n.f10.accept(this, argu);
        MyOutput.print(" NOOP "+RETURN+TEMP(ret.stempNo));

        n.f11.accept(this, argu);
        n.f12.accept(this, argu);
        MyOutput.println(END);
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
        // 参数还是函数或类的普通变量？
        MyFunc func=(MyFunc)argu;
        int tempt1=func.tempNo++;
        MySymbol expf2=n.f2.accept(this,argu);
        //函数参数或局部变量
        MyVar var=func.varMap.get(n.f0.getName());
        if(var!=null){
            if(func.argList.size()<20||var.tempNo>func.argList.size()) {    //store in TEMP
                MyOutput.println(MOVE+TEMP(var.tempNo)+TEMP(expf2.stempNo));    //f0=f2

            }
            else{
                MyOutput.println(HSTORE+TEMP(1)+SV(4*var.tempNo-4)+TEMP(var.tempNo));
            }
        }

        else{       //类的变量
            MyClass myClass=(MyClass)func.upper;
            MyOutput.println(MOVE+TEMP(tempt1)+TEMP(0));
            while(myClass!=null){

                var=myClass.varMap.get(n.f0.getName());
                if(var!=null){
                    MyOutput.print(HSTORE+TEMP(tempt1)+SV(var.bias*4)+TEMP(expf2.stempNo));
                    break;
                }
                myClass=(MyClass)myClass.upper;
                MyOutput.println(HLOAD+TEMP(tempt1)+TEMP(tempt1)+SV(0));
            }

        }




        //n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        if(expf2 instanceof MyVar){
            var.varType=((MyVar) expf2).varType;
        }
        n.f3.accept(this, argu);
        return _ret;
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
        MyFunc func=(MyFunc)argu;
        MySymbol idf0=n.f0.accept(this,argu);
        MySymbol expf2=n.f2.accept(this, argu);
        MySymbol expf5=n.f5.accept(this, argu);
        MyOutput.println(MOVE+TEMP(expf2.stempNo)+TIMES+TEMP(expf2.stempNo)+SV(4));
        MyOutput.println(MOVE+TEMP(idf0.stempNo)+PLUS+TEMP(idf0.stempNo)+TEMP(expf2.stempNo));
        MyOutput.println(HSTORE+TEMP(idf0.stempNo)+SV(0)+TEMP(expf5.stempNo));
        return _ret;
    }
    /*
        output EXP.
     */
    public void getIdAddr(String name, MySymbol argu){
        MyFunc func=(MyFunc)argu;
        int tempt1=func.tempNo++;
        //函数参数或局部变量
        MyVar var=func.varMap.get(name);
        if(var!=null){
            MyOutput.println(TEMP(var.tempNo));

        }

        else{       //类的变量
            MyClass myClass=(MyClass)func.upper;
            while(myClass!=null){
                MyOutput.println(BEGIN);
                MyOutput.println(MOVE+TEMP(tempt1)+TEMP(0));
                var=myClass.varMap.get(name);
                if(var!=null){
                    MyOutput.println(RETURN+PLUS+TEMP(tempt1)+SV(var.bias));
                    break;
                }
                myClass=(MyClass)myClass.upper;
                MyOutput.println(HLOAD+TEMP(tempt1)+"0 ");
            }

        }
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
        int else_label=global_label++;
        int after_label=global_label++;

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol cond=n.f2.accept(this, argu);
        MyOutput.print(CJUMP+TEMP(cond.stempNo));
        MyOutput.println(L(else_label));   //CJUMP ... else
        n.f4.accept(this, argu);
        MyOutput.println(JUMP+L(after_label));
        MyOutput.print(L(else_label)+"   ");
        n.f5.accept(this, argu);
        n.f6.accept(this, argu);
        MyOutput.print(L(after_label));
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
        int start_label=global_label++;
        int end_label=global_label++;
        MySymbol _ret=null;
        MyOutput.print(L(start_label));

        n.f1.accept(this, argu);
        MySymbol exp=n.f2.accept(this, argu);   //calculate condition
        MyOutput.println(CJUMP+TEMP(exp.stempNo)+L(end_label));
        n.f4.accept(this, argu);
        MyOutput.println("JUMP "+L(start_label));
        MyOutput.println(L(end_label));
        return _ret;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    public MySymbol visit(PrintStatement n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        MySymbol symbol=n.f2.accept(this, argu);
        MyOutput.print("PRINT "+TEMP(symbol.stempNo));

        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }
    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    public MySymbol visit(AndExpression n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol sf0=n.f0.accept(this, argu);
        MySymbol sf2=n.f2.accept(this, argu);
        int temp=getTemp(argu);
        MyOutput.println(MOVE+TEMP(temp)+TIMES+TEMP(sf0.stempNo)+TEMP(sf2.stempNo));
        return new MySymbol(temp);
    }
    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    public MySymbol visit(CompareExpression n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol sf0=n.f0.accept(this, argu);
        MySymbol sf2=n.f2.accept(this, argu);
        int temp=getTemp(argu);
        MyOutput.println(MOVE+TEMP(temp)+LT+TEMP(sf0.stempNo)+TEMP(sf2.stempNo));
        return new MySymbol(temp);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    public MySymbol visit(PlusExpression n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol sf0=n.f0.accept(this, argu);
        MySymbol sf2=n.f2.accept(this, argu);
        int temp=getTemp(argu);
        MyOutput.println(MOVE+TEMP(temp)+PLUS+TEMP(sf0.stempNo)+TEMP(sf2.stempNo));
        return new MySymbol(temp);
    }
    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    public MySymbol visit(MinusExpression n, MySymbol argu) {
        MySymbol _ret=null;
        MySymbol sf0=n.f0.accept(this, argu);
        MySymbol sf2=n.f2.accept(this, argu);
        int temp=getTemp(argu);
        MyOutput.println(MOVE+TEMP(temp)+MINUS+TEMP(sf0.stempNo)+TEMP(sf2.stempNo));
        return new MySymbol(temp);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    public MySymbol visit(TimesExpression n, MySymbol argu) {
        MySymbol _ret=null;

        MySymbol sf0=n.f0.accept(this, argu);
        MySymbol sf2=n.f2.accept(this, argu);
        int temp=getTemp(argu);
        MyOutput.println(MOVE+TEMP(temp)+TIMES+TEMP(sf0.stempNo)+TEMP(sf2.stempNo));
        return new MySymbol(temp);
    }
    /**
     * finish sp
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     *
     * BEGIN HLOAD TEMP*
     *  PLUS f0 TIMES 4 f2
     *  0
     *  RETURN TEMP*
     *  END
     */
    public MySymbol visit(ArrayLookup n, MySymbol argu) {
        MySymbol _ret=null;
        MyFunc func=(MyFunc)argu;
        int tempid1=func.tempNo++;
        int tempid2=func.tempNo++;
        //MyOutput.println(MOVE+TEMP(tempid1));       // temp[tempid1]=f2;
        int expf0=n.f0.accept(this, argu).stempNo;
        int expf2=n.f2.accept(this,argu).stempNo;
        MyOutput.println(MOVE+TEMP(expf2)+TIMES+SV(4)+TEMP(expf2));   //expf2=expf2*4
        //MyOutput.print(BEGIN+HLOAD+TEMP(tempid1));    //assign a new temp

        MyOutput.println(MOVE+TEMP(tempid2)+PLUS+TEMP(expf0)+TEMP(expf2));      //temp[tempid2]=tempid1+f0
        MyOutput.println(HLOAD+TEMP(tempid2)+TEMP(tempid2)+SV(0));        //tempid2=addr[tempid2]
        //n.f2.accept(this, argu);
        //MyOutput.println(RETURN+TEMP(tempid2)+END);
        return new MySymbol(tempid2);   //return tempid2
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     * ????
     */
    public MySymbol visit(ArrayLength n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     * BEGIN
     * LOAD temp t1 f0 -- get class instance at temp t1
     * HLOAD temp t2 temp t1 0 -- get class Dtable
     * HLOAD temp t3 temp t2 X -- get func bias
     * RETURN CALL temp t3 (temp t1 f5)
     * END
     */
    public MySymbol visit(MessageSend n, MySymbol argu) {
        MySymbol _ret=null;
        ArrayList argList=new ArrayList();
        int tempt1=((MyFunc)argu).tempNo++;
        int tempt2=((MyFunc)argu).tempNo++;
        int tempt3=((MyFunc)argu).tempNo++;
        int tempt4=((MyFunc)argu).tempNo++;
        MyVar symbol=(MyVar)n.f0.accept(this, argu);   //get the type of caller class
        MyOutput.println(MOVE+TEMP(tempt1)+TEMP(symbol.stempNo));
        MyClass myClass=(MyClass)symbol.varType;
        MyFunc func=null;
        String funcLastname=n.f2.getName();

        while(myClass!=null){
            func=myClass.funcMap.get(funcLastname);

            if(func!=null){     //get the required function
                break;

            }
            myClass=(MyClass)myClass.upper;     //go to the upper class
            //MyOutput.println(HLOAD+TEMP(tempt1)+TEMP(tempt1)+"0");
        }

        MyOutput.println(HLOAD+TEMP(tempt4)+TEMP(tempt1)+SV(4));    //tempt4=dtable
        for(int i=0;i<myClass.funcList.size();i++){
            String funcName=myClass.funcList.get(i);
            if(funcName.contains(funcLastname)){
                MyOutput.println(HLOAD+TEMP(tempt4)+TEMP(tempt4)+SV(4*i));  //tempt4=func addr
                break;
            }
        }
        MyArgs args=new MyArgs((MyFunc)argu);
        if(func.argList.size()>20){
            MyOutput.println(MOVE+TEMP(tempt2)+HALLOCATE+SV(4*func.argList.size()));    //allocate array
            morethan20=0;
            morethan20temp=tempt2;
            n.f4.accept(this, args);
            MyOutput.println(MOVE+TEMP(tempt3)+CALL+TEMP(tempt4));
            MyOutput.println("("+TEMP(tempt1)+TEMP(tempt2)+")");
        }
        else{
            morethan20=-1;
            n.f4.accept(this, args);
            MyOutput.println(MOVE+TEMP(tempt3)+CALL+TEMP(tempt4)+" (");
            MyOutput.print(TEMP(tempt1));
            for(MySymbol i:args.argList){
                MyOutput.print(TEMP(i.stempNo));
            }
            MyOutput.println(")");
        }
        ((MyFunc)argu).tempNo=args.tempNo+1;
        if(func.returnType instanceof MyClass){
            return new MyVar(((MyClass) func.returnType).myThis,tempt3);
        }
        return new MySymbol(tempt3);
    }
    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    public MySymbol visit(ExpressionList n, MySymbol argu) {
        MySymbol _ret=null;
        int tempf0=n.f0.accept(this, argu).stempNo;
        if(morethan20>=0){
            MyOutput.println(HSTORE+TEMP(morethan20temp)+SV(morethan20*4)+TEMP(tempf0));    //save in the array
            morethan20++;
        }
        else{
            ((MyArgs)argu).addSymbol(new MySymbol(tempf0));
        }
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    public MySymbol visit(ExpressionRest n, MySymbol argu) {
        MySymbol _ret=null;
        int tempf1=n.f1.accept(this, argu).stempNo;
        if(morethan20>=0){
            MyOutput.println(HSTORE+TEMP(morethan20temp)+SV(morethan20*4)+TEMP(tempf1));
            morethan20++;
        }
        else{
            ((MyArgs)argu).addSymbol(new MySymbol(tempf1));
        }
        return _ret;
    }
    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     * BEGIN
     */
    public MySymbol visit(AllocationExpression n, MySymbol argu) {
        MySymbol _ret=null;
        String className=n.f1.getName();
        MyClass myClass= MiniJava2Piglet.myGoal.classMap.get(className);    //get the class from symbol table
        int stempNo=BuildClass(myClass,argu);
        return new MyVar(myClass.myThis,stempNo);     //return this class.
    }
    private int BuildClass(MyClass myClass, MySymbol argu){
        int bias=0;
        int tempt1=((MyFunc)argu).tempNo++;
        MyOutput.println(MOVE+TEMP(tempt1));
        MyOutput.println(HALLOCATE+SV(4*(myClass.varMap.size()+2)));
        if(myClass.upper!=null){    //build the upper class

            int upper=BuildClass((MyClass)myClass.upper,argu);
            MyOutput.println(HSTORE+TEMP(tempt1)+SV(0)+TEMP(upper));
        }
        int func_num=0;
        MyClass tClass=myClass;
        while(tClass!=null){
            func_num+=tClass.funcMap.size();
            tClass=(MyClass)tClass.upper;
        }
        //build Dtable
        ArrayList<String>funcList=myClass.funcList;
        if(funcList.size()>0){   // output Dtable
            int tempt2=((MyFunc)argu).tempNo++;
            MyOutput.println(MOVE+TEMP(tempt2)+HALLOCATE+SV(funcList.size()*4));
            bias=0;
            for(String funcName:funcList){
                MyOutput.println(HSTORE+TEMP(tempt2)+" "+SV(bias)+" "+funcName);
                bias+=4;
            }
            MyOutput.println(HSTORE+TEMP(tempt1)+" 4 TEMP"+SV(tempt2));
        }

        int tempt4=getTemp(argu);
        MyOutput.println(MOVE+TEMP(tempt4)+SV(0));
        for(int i=0;i<myClass.varMap.size();i++){
            MyOutput.println(HSTORE+TEMP(tempt1)+SV(i*4+8)+TEMP(tempt4));
        }
        return tempt1;

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
     *  For Spiglet, every primary expression returns a TEMP value.
     */
    public MySymbol visit(PrimaryExpression n, MySymbol argu) {
        MySymbol _ret=null;
        return n.f0.accept(this, argu);
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public MySymbol visit(IntegerLiteral n, MySymbol argu) {
        MySymbol _ret=null;
        int tempNo=getTemp(argu);
        MyOutput.println(MOVE+TEMP(tempNo)+n.f0.tokenImage+" ");
        return new MySymbol(tempNo);
    }
    /**
     * f0 -> "true"
     */
    public MySymbol visit(TrueLiteral n, MySymbol argu) {
        MySymbol _ret=null;
        int tempNo=getTemp(argu);
        MyOutput.println(MOVE+TEMP(tempNo)+SV(1));
        return new MySymbol(tempNo);
    }

    /**
     * f0 -> "false"
     */
    public MySymbol visit(FalseLiteral n, MySymbol argu) {
        MySymbol _ret=null;
        int tempNo=getTemp(argu);
        MyOutput.println(MOVE+TEMP(tempNo)+SV(0));
        return new MySymbol(tempNo);
    }
    /**
     * f0 -> <IDENTIFIER>
     */
    public MySymbol visit(Identifier n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        if(!(argu instanceof MyFunc))
            return _ret;
        MyFunc func=(MyFunc)argu;
        MyVar var=func.varMap.get(n.getName());
        int tempt1=func.tempNo++;
        if(var!=null){
            if(func.argList.size()<20||var.tempNo>func.argList.size()){
                MyOutput.println(MOVE+TEMP(tempt1)+TEMP(var.tempNo));
                return new MyVar(var,tempt1);
            }
            else{
                MyOutput.println(HLOAD+TEMP(tempt1)+TEMP(1)+SV(4*var.tempNo-4));
                return new MyVar(var,tempt1);
            }
        }
        MyClass myClass=(MyClass)argu.upper;

        MyOutput.println(MOVE+TEMP(tempt1)+TEMP(0));
        while(myClass!=null){
            var=myClass.varMap.get(n.getName());
            if(var!=null){
                MyOutput.println(HLOAD+TEMP(tempt1)+TEMP(tempt1)+var.bias*4);
                return new MyVar(var,tempt1);
            }
            myClass=(MyClass)myClass.upper;
            MyOutput.println(HLOAD+TEMP(tempt1)+TEMP(tempt1)+SV(0));
        }
        return _ret;
    }
    /**
     * f0 -> "this"
     */
    public MySymbol visit(ThisExpression n, MySymbol argu) {
        MySymbol _ret=null;
        return new MyVar(((MyClass)argu.upper).myThis,0);
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    public MySymbol visit(ArrayAllocationExpression n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f4.accept(this, argu);
        int tempf3=n.f3.accept(this,argu).stempNo;  //tempf3=f3
        MyOutput.println(MOVE+TEMP(tempf3)+TIMES+TEMP(tempf3)+SV(4));   //tempf3=4*f3
        MyOutput.println(MOVE+TEMP(tempf3)+HALLOCATE+TEMP(tempf3));         //tempf3=hallocate(tempf3)
        return new MySymbol(tempf3);
    }
    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    public MySymbol visit(NotExpression n, MySymbol argu) {
        MySymbol _ret=null;
        n.f0.accept(this, argu);
        int temp=n.f1.accept(this, argu).stempNo;   //temp=f1
        MyOutput.println(MOVE+TEMP(temp)+MINUS+SV(1)+TEMP(temp));   //temp=1-temp;
        return new MySymbol(temp);
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    public MySymbol visit(BracketExpression n, MySymbol argu) {
        MySymbol _ret=null;
        return n.f1.accept(this, argu);
    }

    /**
     * f0 -> AndExpression()
     *       | CompareExpression()
     *       | PlusExpression()
     *       | MinusExpression()
     *       | TimesExpression()
     *       | ArrayLookup()
     *       | ArrayLength()
     *       | MessageSend()
     *       | PrimaryExpression()
     */
    public MySymbol visit(Expression n, MySymbol argu) {
        return n.f0.accept(this, argu);
    }


}
