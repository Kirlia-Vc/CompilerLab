package mytypes;

public class MySymbol {
	public static final int CLASS=0;
	public static final int FUNCTION=1;
	public static final int VAR=2;
	public static final int GOAL=3;
	public String name;
	public int type;
	public MySymbol upper;
	public boolean expectingMethod;
	public int stempNo;
	public MySymbol(String name,int type){
		this.name=name;
		this.type=type;
		this.upper=null;
		expectingMethod=false;
	}
	public MySymbol(String name,int type,MySymbol upper){
		this.name=name;
		this.type=type;
		this.upper=upper;
		expectingMethod=false;
	}
	public MySymbol(){
		this.type=3;
		this.upper=null;
		expectingMethod=false;
	}
	public MySymbol(int stempNo){
	    this.stempNo=stempNo;
    }
}
