class Factorial{

    public static void main(String[] a){
        Fac m;
        System.out.println(new Fact().ComputeFact(m.ComputeFact(2,new Fact()),new Fac()));
    }
}
class Fac extends Fact{

    public int TSG(){
        int T;
        T=7;
        return T;
    }
}
class Fact {
    Fact t;
    public int ComputeFact(int num,Fact a){
        int num_aux ;
        if (num < (6))
            num_aux = 1 ;
        else
            num_aux = num * (this.ComputeFact(num-1,new Fac())) ;
        return num_aux ;
    }
    public Fact GetA(int t){
        return new Fac();
    }
}
