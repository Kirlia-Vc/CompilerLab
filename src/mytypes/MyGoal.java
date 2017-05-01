package mytypes;

import java.util.HashMap;
import java.util.Map;

public class MyGoal extends MySymbol{
	public Map<String,MyClass>classMap;
	public Map<Integer,MyClass>classIdMap;
	public MyGoal(){
		classMap=new HashMap<>();
	}
}
