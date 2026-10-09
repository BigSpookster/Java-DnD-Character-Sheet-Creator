// Eli Rosenfeld
// 4/26/2026
// The purpose of this class is to create a custom exception
// that is thrown when someone tries to make a hit point total negative or 0

public class InvalidHitPointsException extends Exception
{
    public InvalidHitPointsException(String s)
    {
        super(s);

    }

}