package mytypes;

public class MyBasicType extends MySymbol{

	public static MyBasicType MyIntType=new MyBasicType("int", 2);
	public static MyBasicType MyBooleanType=new MyBasicType("boolean", 2);
	public static MyBasicType MyArrayType=new MyBasicType("array", 2);
	public static MyBasicType MyVoid=new MyBasicType("void",2);
	public MyBasicType(String name, int type) {
		super(name, type);
		// TODO Auto-generated constructor stub
	}

	
}
