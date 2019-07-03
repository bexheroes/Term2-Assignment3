
public class property {
    private String name;
    private int cost;
    private int available;
    private int rent;
    private int position;
    private int type;
    private int index;
    public property(String name,int position,int cost,int type,int index){
        this.name = name;
        this.position = position;
        this.cost = cost;
        this.rent = cost;
        this.available = 1;
        this.type = type;
        this.index = index;
    }
    public int getRent(int dice){
       if(type==1){
            if(rent >0 && rent<=2000){
                return rent*40/100;
            }else if(rent > 2000 && rent <= 3000){
                return rent*30/100;
            }else if(rent > 3000 && rent <= 4000){
                return rent*35/100;
            }
        }else if(type==3){
            return 4*dice;
        }
        return 0;
    }
    public int getType(){
        return type;
    }
    public int getPosition(){
        return position;
    }
    public int getIndex(){
        return index;
    }
    public String getName(){
        return name;
    }
    public int getCost(){
        return cost;
    }
}
