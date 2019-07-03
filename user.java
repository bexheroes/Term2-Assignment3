
public class user {
    private String name;
    private int money;
    private int beforeMoney;
    public void setName(String name){
        this.name = name;
    }
    public void setMoney(int money){
        this.money = money;
        this.beforeMoney = money;
    }
    public String getName(){
        return name;
    }
    public void minusMoney(int money){
        beforeMoney = this.money;
        this.money-=money;
    }
    public void plusMoney(int money){
        beforeMoney = this.money;
        this.money+=money;
    }
    public int getMoney(){
        return money;
    }
    public int getBeforeMoney(){
        return beforeMoney;
    }
}
