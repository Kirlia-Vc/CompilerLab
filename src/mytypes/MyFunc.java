package mytypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyFunc extends MySymbol{

	public Map<String, MyVar> varMap;
	public MySymbol returnType;             //return type
	public ArrayList<MySymbol> argList;     //argument list of this function.
	public MyFunc(String name, int type, MySymbol upper, MySymbol returnType) {
		super(name, type, upper);
		varMap=new HashMap<>();
		argList=new ArrayList<>();
		this.returnType=returnType;
		// TODO Auto-generated constructor stub
	}
	public MyFunc(MyFunc myFunc){
	    super(myFunc.name,myFunc.type,myFunc.upper);
	    varMap=myFunc.varMap;
	    argList=myFunc.argList;
    }
	public boolean isEqual(MyFunc func){
	    if(!func.returnType.equals(returnType))
	        return false;
	    if(argList.size()!=func.argList.size())
	        return false;
	    for(int i=0;i<argList.size();i++){
	        if(!argList.get(i).equals(func.argList.get(i)))
	            return false;
        }
        return true;
    }
    public MyVar getReturnInstance(){
	    if(returnType.equals(MyBasicType.MyArrayType))
	        return MyVar.MyArray;
	    if(returnType.equals(MyBasicType.MyBooleanType))
	        return MyVar.MyBoolean;
	    if(returnType.equals(MyBasicType.MyIntType))
	        return MyVar.MyInt;
	    return ((MyClass)returnType).myThis;
    }

}
