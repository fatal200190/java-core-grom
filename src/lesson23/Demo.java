package lesson23;

/**
 * Created by Alex on 29.11.2017.
 */
public class Demo {
    public static void main(String[] args) {
        Validator validator1 = new Validator("user");
        System.out.println(validator1.getCheckCount());

        Validator validator2 = new Validator("company");
        System.out.println(validator2.getCheckCount());
    }
}
