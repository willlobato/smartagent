package net.pupunha.smartagent.jmx;

public interface MetricMBean {

    void setHit(long hit);
    long getHit();

    void setElapsedTimeTotal(long elapsedTimeTotal);
    long getElapsedTimeTotal();

    void clearAll();

}
