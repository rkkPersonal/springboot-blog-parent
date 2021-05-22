package org.xr.happy.lamda;

public class LambadaObj {

    private int age;

    private String name;


    public LambadaObj(MyInterface myInterface) {
        //myInterface.methodA();
        // myInterface.methodB(100);
        int i = myInterface.methodC(10, 20);
        System.out.println(i);
    }

    public LambadaObj(FunctionallyLambada functionallyLambada) {
        functionallyLambada.show();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
