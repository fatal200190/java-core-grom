package lesson24.firstexample;

public class Demo <T>{


//    private printUser(User user){
//        //print
//    }

    //    private printSolution(Solution solution){
//        //print
//    }


//  private printChanel(Chanel chanel){
//        //print
//    }


     public void print(T t){
        System.out.println(t);
    }

    private static void calculatePrice(AbstractOrder abstractOrder){
        System.out.println(abstractOrder.getPrice());
    }
//    private static void calculatePrice(SecondOrder secondOrder){
//        Sys.out.println(secondOrder.getPrice());
//    }

    public static void main(String[] args) {
        calculatePrice(new FirstOrder());
        calculatePrice(new SecondOrder());

    }
}