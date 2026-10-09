// Eli Rosenfeld
// 4/25/2026
// The purpose of this class is to define my own exception
// that triggers when you try to have a negative number of items

public class NegativeItemNumberException extends Exception
{
    public NegativeItemNumberException(String s)
    {
        super(s);

    }

}