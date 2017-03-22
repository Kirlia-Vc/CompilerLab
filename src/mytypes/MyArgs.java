package mytypes;

import java.util.ArrayList;

/**
 * Created by Vc on 2017/3/22.
 */
public class MyArgs extends MyFunc {
    public ArrayList<MySymbol> argList;
    public MyArgs(String name, int type, MySymbol upper, MySymbol returnType) {
        super(name, type, upper, returnType);
        argList=new ArrayList<>();
    }
    public MyArgs(MyFunc myFunc){
        super(myFunc);
        argList=new ArrayList<>();
    }
    public void addSymbol(MySymbol symbol){
        argList.add(symbol);
    }
}
