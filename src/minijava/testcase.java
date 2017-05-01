package minijava;

/**
 * Created by Vc on 2017/4/14.
 */
class C{
    A a;
    public int some(){
        return a.getI();
    }
    public int init(){
        a=new A();
        return a.setI();
    }
}
class A{
    int i;
    public int getI(){
        return i;
    }
    public int setI(){
        i=7;
        return i;
    }
}
class B extends A{
    int i;
    public int getI(){
        return i;
    }
    public int setI(){
        i=6;
        return i;
    }
}
public class testcase {

    public static void main(String[] args){
        C c;
        int a;
        c=new C();
        a=c.init();
        a=c.some();
        System.out.println(a);
    }
}
