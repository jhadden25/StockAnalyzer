import java.util.LinkedList;

public class ValueCalculator implements Calculator{

    public int calculate(LinkedList<Double> chart)
    {
        return calculate(chart, 7);
    }

    public int calculate(LinkedList<Double> chart, String sensitivity)
    {
        if(sensitivity.toLowerCase().equals("high"))
            return calculate(chart, 3);
        else
            return calculate(chart, 40);
    }

    public int calculate(LinkedList<Double> chart, int sensitivity)
    {
        double prevTotal = 0;
        double newTotal = 0;
        for(int i = sensitivity+2; i < (sensitivity+1)*2; i++)
        {
            prevTotal += chart.get(chart.size() - i);
        }
        for(int j = 1; j < 1+sensitivity; j++)
        {
            newTotal += chart.get(chart.size() - j);
        }
        prevTotal /= sensitivity;
        newTotal /= sensitivity;

        if(newTotal <= prevTotal)
            return 1; // BUY THE DIP
        else
            return 0; // SELL THE PEAK
    }

}
