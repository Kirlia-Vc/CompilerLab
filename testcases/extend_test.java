class Factorial{

    public static void main(String[] a){
        Fac m;
        System.out.println(new Fact().ComputeFact(m.ComputeFact(2)));
    }
}
class Fact extends Fac{

}
class Fac {
    Fact t;
    public int ComputeFact(int num){
        int num_aux ;
        if (num < (6))
            num_aux = 1 ;
        else
            num_aux = num * (new Fac().ComputeFact(num-1)) ;
        return this ;
    }
}
