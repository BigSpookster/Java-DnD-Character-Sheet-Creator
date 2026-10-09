// Eli Rosenfeld
// 4/26/2026
// The purpose of this class is to create a custom exception
// that is thrown if the damage die or range is not positive

public class InvalidWeaponException extends Exception
{
    public InvalidWeaponException(String s)
    {
        super(s);

    }

}