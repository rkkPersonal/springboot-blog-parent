package org.xr.happy.transaction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 多线程异步处理时的事务管理
 * 1.addFunction 添加要异步执行的方法
 * 2.execute方法中，使用全局的计数器和异常标记字段，统计个异步线程执行的结果
 *      当所有异步线程执行完之后，根据异常标记字段判断是回滚还是提交事务。
 */
public class MultiThreadTransactionComponent {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    private PlatformTransactionManager platformTransactionManager;
    private ThreadPoolExecutor threadPoolExecutor;

    private List<Callable> supplierList=new ArrayList();
    // 创建执行计数器
    private CountDownLatch countDownLatch;
    // 是否存在异常
    AtomicReference<Boolean> isError = new AtomicReference<>(false);

    /**
     * @param transactionManager  如果为null  就不启用事务
     * @param threadPoolExecutor
     */
    public MultiThreadTransactionComponent(PlatformTransactionManager transactionManager,ThreadPoolExecutor threadPoolExecutor){
        this.platformTransactionManager=transactionManager;
        this.threadPoolExecutor=threadPoolExecutor;
    }

    /**
     * 添加要异步执行的方法程序
     * @param supplier
     */
    public void addFunction(Callable supplier){
        supplierList.add(supplier);
    }

    public List execute(){
        countDownLatch=new CountDownLatch(supplierList.size());
        logger.info("【多线程事务】开始...");
        List<Future> futureList=new ArrayList<>();
        for(Callable supplier:supplierList){
            Future submit = this.threadPoolExecutor.submit(new TransactionRunnable(platformTransactionManager, supplier));
            futureList.add(submit);
        }
        List resultList=new ArrayList();
        try {
            countDownLatch.await();
            //获取线程处理结果
            for(Future future:futureList){
                Object result = future.get();
                if(result instanceof Collection){
                    resultList.addAll((Collection) result);
                }else {
                    resultList.add(result);
                }
            }
            if(isError.get()) {
                logger.error("【多线程事务】多线程执行失败");
                // 主线程抛出自定义的异常
                throw new RuntimeException("多线程执行失败");
            }
            logger.info("【多线程事务】多线程执行完成");
        } catch (Exception e) {
            logger.error("多线程执行失败");
            // 主线程抛出自定义的异常
            throw new RuntimeException("多线程执行失败"+e.getMessage());
        }
        return resultList;
    }


    class TransactionRunnable implements Callable {

        private PlatformTransactionManager platformTransactionManager;
        private Callable supplier;

        public TransactionRunnable(PlatformTransactionManager platformTransactionManager, Callable supplier) {
            this.platformTransactionManager=platformTransactionManager;
            this.supplier=supplier;
        }

        @Override
        public Object call() {
            TransactionStatus transaction=null;
            if(platformTransactionManager!=null){
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                transaction = this.platformTransactionManager.getTransaction(def);
            }
            Object result=null;
            try {
                result= this.supplier.call();
            } catch (Exception e) {
                //设置错误标记
                isError.set(true);
                logger.error("【多线程事务】执行失败{}",e.getMessage());
            }
            countDownLatch.countDown();
            try{
                countDownLatch.await();
                if(platformTransactionManager!=null) {
                    if (isError.get()) {
//                      logger.info("【多线程事务-子线程】事务回滚");
                        platformTransactionManager.rollback(transaction);
                    } else {
//                    logger.info("【多线程事务-子线程】事务提交");
                        platformTransactionManager.commit(transaction);
                    }
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return result;
        }
    }

}
