package mytypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyClass extends MySymbol{
    public static int GLOBAL_ID=1;
	public Map<String,MyFunc> funcMap;
	public Map<String,MyVar> varMap;
	public ArrayList<String> funcList;
	public MyVar myThis;
	public int funcBias=0;
	public int varBias=2;
	public int id;
	public MyClass(String name, int type) {
		super(name, type);
		funcMap=new HashMap<>();
		varMap=new HashMap<>();
		funcList=new ArrayList<>();
		myThis=new MyVar("this",MySymbol.VAR,this,this);
		id=GLOBAL_ID++;
		// TODO Auto-generated constructor stub
	}
	public MyClass(String name, int type,MySymbol upper) {
		super(name, type, upper);
		funcMap=new HashMap<>();
		varMap=new HashMap<>();
        funcList=new ArrayList<>();
        myThis=new MyVar("this",MySymbol.VAR,this,this);
        id=GLOBAL_ID++;
		// TODO Auto-generated constructor stub
	}
	
}
