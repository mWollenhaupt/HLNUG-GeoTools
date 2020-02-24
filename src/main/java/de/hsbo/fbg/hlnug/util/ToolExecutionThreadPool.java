package de.hsbo.fbg.hlnug.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Moritz Wollenhaupt <moritz.wollenhaupt@hs-bochum.de>
 */
public class ToolExecutionThreadPool {
    
    private ExecutorService executorService; 
    
    public ToolExecutionThreadPool() {
        this.executorService = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
    }
    
    public void execute(Runnable runnable) {
        executorService.execute(runnable);
    }
    
    
}
