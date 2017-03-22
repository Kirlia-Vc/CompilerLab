package mytypes;

public class MyVar extends MySymbol{
	public MySymbol varType;
	public static MyVar MyInt=new MyVar("int",VAR,null,MyBasicType.MyIntType);
	public static MyVar MyArray=new MyVar("array",VAR,null,MyBasicType.MyArrayType);
	public static MyVar MyBoolean=new MyVar("boolean",VAR,null,MyBasicType.MyBooleanType);
	public MyVar(String name, int type,MySymbol upper,MySymbol varType) {
		super(name, type,upper);
		this.varType=varType;
		// TODO Auto-generated constructor stub
	}
	public MyVar(String name, int type,MySymbol upper) {
		super(name, type, upper);
		// TODO Auto-generated constructor stub
	}
	public boolean isTypeEqual(MySymbol m){
	    if(!(m instanceof MyVar)){
	        return false;
        }
        MyVar var=(MyVar)m;
        return this.varType.equals(var.varType);
    }
	
	
}
