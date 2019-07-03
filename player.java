
import java.util.ArrayList;
import java.util.List;

public class player extends user{
    private int position;
    private int penalize;
    private int beforePosition;
    private int countProperties;
    private int countPenalize;
    private int lastStep;
    private int exceptionMoney;
    List properties = new ArrayList();
    List properties2 = new ArrayList();
    public player(String name){
        setName(name);
        setMoney(15000);
        position = 1;
        penalize = 0;
        beforePosition = 1;
        countProperties = 0;
        countPenalize = 1;
        exceptionMoney = 0;
    }
    @Override
    public String getName(){
        return super.getName();
    }
    public int play(int step){
        beforePosition = position;
        position += step;
        if(position>40){
            position = position % 40;
            if(position!=11){
                plusMoney(200);
                plusExceptionMoney();
            }
        }
        if(penalize>0){
            position-=step;
        }
        return position;
        
    }
    public int getExceptionMoney(){
        return exceptionMoney; 
    }
    public void plusExceptionMoney(){
        exceptionMoney++;
    }
    public void minusExceptionMoney(){
        exceptionMoney--;
    }
    public void addProperties(int x){
        properties.add(x);
        countProperties++;
    }
    public boolean inProperties(int x){
        return properties.contains(x);
    }
    public int rent(int cost,String forWhich){
        if(getMoney() < cost ){
            return -1;
        }else{
            minusMoney(cost);
            return 1;
        }
    }
    public int buy(int cost,int indexOfProperty,String nameOfProperty){
        if(cost>getMoney()){
            return -1;
        }else{
            minusMoney(cost);
            properties.add(indexOfProperty);
            properties2.add(nameOfProperty);
        }
        return 0;
    }
    public void setPosition(int position){
        this.position = position;
    }
    public void minusPenalize(){
        penalize-=1;
        if(penalize==0){
            countPenalize = 0;
        }
        countPenalize++;
    }
    public boolean getPenalize(){
        if(penalize>0){
            return false;
        }
        return true;
    }
    public void penalize(){
        penalize = 3;
    }
    public int getPenalizeSize(){
        return countPenalize;
    }
    public int getBefore(){
        return beforePosition;
    }
    public int getPosition(){
        return position;
    }
    public String getProperties(){
        String st = "";
        for(int i=0;i<properties2.size();i++){
            if(i!=properties2.size()-1){
                st+=properties2.get(i);
                st+=",";
            }else{
                st+=properties2.get(i);
            }
        }
        return st;
    }
    
    public String getProperties2(){
        String st = "";
        for(int i=0;i<properties.size();i++){
            if(i!=properties.size()-1){
                st+=properties.get(i);
                st+=",";
            }else{
                st+=properties.get(i);
            }
        }
        return st;
    }
    public static int getIndex(property properties[],int len,int position){
        for(int i=0;i<len;i++){
            if(properties[i].getPosition()==position){
                return properties[i].getIndex();
            }
        }
        return -1;
    }
    
}
