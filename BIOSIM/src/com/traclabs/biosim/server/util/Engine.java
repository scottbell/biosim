package biosim.server.util;

public interface Engine{
    public void open();
    public void put(double[] inputVector);
    public double[] get();
    public void close();
}
