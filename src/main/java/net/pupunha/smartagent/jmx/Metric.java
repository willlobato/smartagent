package net.pupunha.smartagent.jmx;

public class Metric implements MetricMBean {

    private long hit;
    private long elapsedTimeTotal;

    @Override
    public void setHit(long hit) {
        this.hit = hit;
    }

    @Override
    public long getHit() {
        return this.hit;
    }

    @Override
    public void setElapsedTimeTotal(long elapsedTimeTotal) {
        this.elapsedTimeTotal = elapsedTimeTotal;
    }

    @Override
    public long getElapsedTimeTotal() {
        return this.elapsedTimeTotal;
    }

    @Override
    public void clearAll() {
        hit = 0;
        elapsedTimeTotal = 0;
    }

}
