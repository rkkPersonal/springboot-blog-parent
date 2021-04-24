package org.xr.happy.tread.aqs;

public class KaiSemaphore {

    Sync sync;

    public KaiSemaphore(int count) {
        sync = new Sync(count);
    }


    public void acquire() {
        sync.acquireShare();
    }

    public void release() {
        sync.releaseShare();
    }

}
