package org.xr.happy.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class StevenUnsafe {

    public volatile  int f =0;
    private static Unsafe unsafe = null;
    private static long valueOffset = 0;


    static {

        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");

            theUnsafe.setAccessible(true);

            unsafe = (Unsafe) theUnsafe.get(null);


            Field i1 = StevenUnsafe.class.getDeclaredField("f");

            valueOffset  = unsafe.objectFieldOffset(i1);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public  void add(){

        while (true){

            int currentValue = unsafe.getIntVolatile(this, valueOffset);

            boolean b = unsafe.compareAndSwapInt(this, valueOffset, currentValue, currentValue + 1);

            if (b){
                break;
            }

        }
    }


    public static void main(String[] args) throws InterruptedException {

       final StevenUnsafe stevenUnsafe = new StevenUnsafe();


        for (int i = 0; i < 6; i++) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 1000; j++) {
                        stevenUnsafe.add();

                        }

                    }
                }).start();
        }


        Thread.sleep(5000L);

        System.out.println(stevenUnsafe.f);
    }
}
