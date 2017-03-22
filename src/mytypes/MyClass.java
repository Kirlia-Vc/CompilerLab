package mytypes;

import java.util.HashMap;
import java.util.Map;

public class MyClass extends MySymbol{
	public Map<String,MyFunc> funcMap;
	public Map<String,MyVar> varMap;
	public MyVar myThis;
	public MyClass(String name, int type) {
		super(name, type);
		funcMap=new HashMap<>();
		varMap=new HashMap<>();
		myThis=new MyVar("this",MySymbol.VAR,this,this);
		// TODO Auto-generated constructor stub
	}
	public MyClass(String name, int type,MySymbol upper) {
		super(name, type, upper);
		funcMap=new HashMap<>();
		varMap=new HashMap<>();
        myThis=new MyVar("this",MySymbol.VAR,this,this);
		// TODO Auto-generated constructor stub
	}
	
}
