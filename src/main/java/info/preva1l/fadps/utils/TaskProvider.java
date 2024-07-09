package info.preva1l.fadps.utils;

import java.time.Duration;

public interface TaskProvider {
    /**
     * Execute a task off the main thread.
     * @param runnable the task to execute
     */
    void executeAsync(Runnable runnable);
    /**
     * Execute a repeating task off the main thread.
     * @param runnable the task to execute
     * @param repeatingTime a duration of the interval between calls
     */
    void executeRepeatingAsync(Runnable runnable, Duration repeatingTime);

    /**
     * Execute a task on the main thread.
     * @param runnable the task to execute
     */
    void executeSync(Runnable runnable);
    /**
     * Execute a repeating task on the main thread.
     * @param runnable the task to execute
     * @param repeatingTime a duration of the interval between calls
     */
    void executeRepeatingSync(Runnable runnable, Duration repeatingTime);
}