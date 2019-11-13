package testing2;
class Wallet
{
    Double money;
    Wallet()
    {
        money = 0.0;
    }
    Wallet(double Money)
    {
        money = Money;
    }
    boolean removeMoney(double price)
    {
        if(money < price)
        {
            return false;
        }
        else
        {
            money -= price;
            if(money  == 0)
            {
                //send an alert that wallet is empty
            }
            return true;
        }
    }
    Double getMoney()
    {
        return money;
    }
    boolean addMoney(double amount)
    {
        if(amount <= 0)
        {
            return false;
        }
        else{
            money += amount;
            return true;
        }
    }
}