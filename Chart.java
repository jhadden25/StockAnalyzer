import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingWorker;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class Chart {

    MySwingWorker mySwingWorker;
    SwingWrapper<XYChart> sw;
    XYChart chart;
    private int value = 1, highValue = 1, lowValue = 1;
    private int customValue = 1;
    private int myValue = 0;
    private int speed = 4;

    public void setCustomValue(int customValue)
    { this.customValue = customValue; }

    public void setSpeed(int speed)
    {this.speed += speed;}

    public int getMyValue()
    {return myValue;}

    public int getValue()
    {return value;}

    public int getHighValue()
    {return highValue;}

    public int getLowValue()
    {return lowValue;}

    public int getSpeed()
    {return speed;}

    public void go() {

        // Create Chart
        chart = QuickChart.getChart("Stock Value", "Time", "Price", "randomWalk", new double[] { 0 }, new double[] { 0 });
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisTicksVisible(false);

        // Show it
        sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart();

        mySwingWorker = new MySwingWorker();
        mySwingWorker.execute();
    }

    private Boolean status = false;
    public Boolean isOn()
    {return status;}

    public class MySwingWorker extends SwingWorker<Boolean, double[]> {

        LinkedList<Double> fifo = new LinkedList<Double>();
        public LinkedList<Double> valueList()
        {return fifo;}

        public void addNumber()
        {fifo.add(fifo.get(fifo.size() - 1) + Math.random() - .5);}

        public MySwingWorker() {

            fifo.add((Math.random() * (200-20+1)) + 20);
        }

        protected Boolean doInBackground() throws Exception {

        ValueCalculator valueCalculator = new ValueCalculator();
        for(int k = 0; k < 100; k++)
        {fifo.add(fifo.get(fifo.size() - 1) + Math.random() - .5);}
            while (!isCancelled()) {
                for(int i = 1; i <= speed/2; i++)
                {addNumber();}
                addNumber();

                if (fifo.size() > 100) {
                    fifo.removeFirst();
                }
                status = true;

                double[] array = new double[fifo.size()];
                for (int i = 0; i < fifo.size(); i++) {
                    array[i] = fifo.get(i);
                }
                // WHERE THE UPDATE WOULD GO
                value = valueCalculator.calculate(fifo, 7);
                highValue = valueCalculator.calculate(fifo, "high");
                lowValue = valueCalculator.calculate(fifo, "low");
                myValue = valueCalculator.calculate(fifo, customValue);
                publish(array);

                try {
                    Thread.sleep(250 * speed);
                } catch (InterruptedException e) {
                    // eat it. caught when interrupt is called
                    System.out.println("MySwingWorker shut down.");
                }

            }

            return true;
        }

        protected void process(List<double[]> chunks) {
            double[] mostRecentDataSet = chunks.get(chunks.size() - 1);

            chart.updateXYSeries("randomWalk", null, mostRecentDataSet, null);
            sw.repaintChart();

            long start = System.currentTimeMillis();
            long duration = System.currentTimeMillis() - start;
            try {
                Thread.sleep(40 - duration); // 40 ms ==> 25fps
            } catch (InterruptedException e) {
            }

        }
    }
}