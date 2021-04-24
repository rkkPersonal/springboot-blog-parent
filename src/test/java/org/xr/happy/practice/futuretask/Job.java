package org.xr.happy.practice.futuretask;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Job extends RecursiveTask {


    private List<String> urls;

    private int start;

    private int end;

    public Job(List<String> urls, int start, int end){
        this.urls=urls;
        this.start=start;
        this.end=end;
    }



    @Override
    protected Object compute() {


        if (urls.size()<=10){

            for (String url : urls) {
                Request.doRequest(urls);
            }
        }else {
            int  x = (start-end)/2;

            Job job = new Job(urls, start, x);

            job.fork();

            Job job1 = new Job(urls, x, end);

            job1.fork();

            String ob = null;
            ob += job.join();
            ob += job1.join();
            return ob;
        }

        return null;
    }


}