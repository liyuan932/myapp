package com.mycompany.myapp.service;

/**
 * @author wb-liyuan.j
 * @date 2016-07-01
 */
public class Test {
    public static void main(String[] args) throws Exception {

        Integer execute = execute(new Function<Integer>(3) {
            @Override
            public Integer execute() {

                return 1;
            }
        });

        System.out.println(execute);
    }

    public static <T> T execute(Function<T> function) throws Exception {
        return function.test();
    }

    abstract static class Function<T>{

        private int times;

        private Exception ex;

        public Function(int times){
            this.times = times;
        }

        public abstract  T execute();

        public  T test() throws Exception{
            for(int i = 0;i< times;i++){
                try {
                    System.out.println(String.format("第%s次",i));
                    return execute();
                } catch (Exception ex) {
                    this.ex = ex;
                }
            }
            throw ex;
        }
    }
}
