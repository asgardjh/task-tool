package cn.org.opendfl.task.test.scheduler;

import cn.org.opendfl.tasktool.task.TaskCompute;
import cn.org.opendfl.task.test.biz.ITaskTestBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定时任务测试
 *
 * @author chenjh
 */
@Service
@Slf4j
public class TaskTestScheduler {
    private static Random random = new Random();
    private static AtomicInteger counter = new AtomicInteger();

    @Resource
    private ITaskTestBiz taskTestBiz;

    @Scheduled(fixedDelay = 500, initialDelay = 1000)
    public void testSchedulesJob() {
        int v = counter.incrementAndGet();

        //用于通知TaskComputeAspect接口调用来源
        String currentMethodName=this.getClass().getSimpleName()+".testSchedulesJob";
        //走接口调用,@TaskCompute有效
        int rnd = this.taskTestBiz.randomNum(currentMethodName);

        //走本地调用，@TaskCompute不起作用
        this.testSchedules();

        if (v % 100 == 0 || v < 3) {
            log.debug("-----testSchedulesJob--count={} rnd={}", v, rnd);
        }
    }

    /**
     * 本地调用，TaskCompute接口调用统计不起作用。
     * @deprecated
     */
    @TaskCompute
    public void testSchedules() {
        int rnd = random.nextInt(100);
        try {
            Thread.sleep((rnd % 3 + 1) * 1000L);
        } catch (InterruptedException e) {
            log.warn("-----testSchedules--error={}", e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
