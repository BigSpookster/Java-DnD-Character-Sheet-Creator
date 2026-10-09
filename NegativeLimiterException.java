// Eli Rosenfeld
// 4/26/2026
// The purpose of this class is to create a custom exception
// that is thrown when you try to make a piece of armor have a negative limiter

public class NegativeLimiterException extends Exception
{
    public NegativeLimiterException (String s)
    {
        super(s);

    }

}