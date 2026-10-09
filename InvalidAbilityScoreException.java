// Eli Rosenfeld
// 4/24/2026
// The purpose of this class is to define my own exception
// that will occur when an ability score is above 30 or below 1

public class InvalidAbilityScoreException extends Exception
{
    public InvalidAbilityScoreException (String s)
    {
        super(s);

    }

}