// Eli Rosenfeld
// 4/24/2026
// The purpose of this class is to define my own exception
// that will occur when the number of stats is greater than 6

public class UnusualNumberOfStatsException extends Exception
{
    public UnusualNumberOfStatsException(String s)
    {
        super(s);

    }

}