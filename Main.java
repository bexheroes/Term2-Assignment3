
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.*;

public class Ass3 {
    public static void main(String[] args) {
		
        player [] players = new player[2];
        banker [] thebanker = new banker[1];
        thebanker[0] = new banker();
        int count_players = 0;
        int control;
        property properties[] = new property[50];
        int count_properties = 0;
        String chanceList[] = new String[50];
        String chestCommunityList[] = new String[50];
        int count_chance = 0;
        int count_chestCommunity = 0;
        int total_chance[] = new int[1];
        int total_chestCommunity[] = new int[1];
        total_chance[0] = 0;
        total_chestCommunity[0] = 0;
        int gameover[] = new int[1];
        gameover[0] = 0;
        int count_command = 0;
        
        try{
            File f = new File(args[0]);
            Scanner s = new Scanner(f);
            
            JSONParser parser = new JSONParser();
                    try{
                        Object obj = parser.parse(new FileReader("property.json"));
                        Object obj2 = parser.parse(new FileReader("list.json"));
                        JSONObject jsonObject = (JSONObject) obj;
                        JSONObject jsonObject2 = (JSONObject) obj2;
                        JSONArray h1 = (JSONArray) jsonObject2.get("communityChestList");
                        JSONArray h2 = (JSONArray) jsonObject2.get("chanceList");
                        JSONArray j1 = (JSONArray) jsonObject.get("1");
                        JSONArray j2 = (JSONArray) jsonObject.get("2");
                        JSONArray j3 = (JSONArray) jsonObject.get("3");
                        for(int i=0;i<j1.size();i++){
                            JSONObject okey_j1 = (JSONObject) j1.get(i);
                            String name = (String) okey_j1.get("name");
                            String id = (String) okey_j1.get("id");
                            String cost = (String) okey_j1.get("cost");
                            property instance = new property(name,Integer.parseInt(id),Integer.parseInt(cost),1,count_properties);
                            properties[count_properties] = instance;
                            count_properties++;
                        }
                        for(int i=0;i<j2.size();i++){
                            JSONObject okey_j2 = (JSONObject) j2.get(i);
                            String name = (String) okey_j2.get("name");
                            String id = (String) okey_j2.get("id");
                            String cost = (String) okey_j2.get("cost");
                            property instance = new property(name,Integer.parseInt(id),Integer.parseInt(cost),2,count_properties);
                            properties[count_properties] = instance;
                            count_properties++;
                        }
                        for(int i=0;i<j3.size();i++){
                            JSONObject okey_j3 = (JSONObject) j3.get(i);
                            String name = (String) okey_j3.get("name");
                            String id = (String) okey_j3.get("id");
                            String cost = (String) okey_j3.get("cost");
                            property instance = new property(name,Integer.parseInt(id),Integer.parseInt(cost),3,count_properties);
                            properties[count_properties] = instance;
                            count_properties++;
                        }
                        for(int i=0;i<h1.size();i++){
                            JSONObject okey_h1 = (JSONObject) h1.get(i);
                            String item = (String) okey_h1.get("item");
                            chestCommunityList[count_chestCommunity] = item;
                            count_chestCommunity++;
                        }
                        for(int i=0;i<h2.size();i++){
                            JSONObject okey_h2 = (JSONObject) h2.get(i);
                            String item = (String) okey_h2.get("item");
                            chanceList[count_chance] = item;
                            count_chance++;
                        }
                        
                    }catch(Exception e){
                        System.out.println(e);
                    }
            
            while(s.hasNextLine()){
                String line = s.nextLine();
                count_command++;
                if(line.trim().equals("show()")){
                    
                }else{
                    String [] splited_line = line.split(";");
                    control = isThere(players,count_players,splited_line[0]);
                    if(control == 0){
                        count_players++;
                    }
                    
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("Not such a file");
        }
        try{
            File f = new File(args[0]);
            Scanner s = new Scanner(f);
            int say = 0;
            while(s.hasNextLine()){
                String line = s.nextLine();
                say++;
                if(line.trim().equals("show()")){
                    if(gameover[0] == 0){
                        System.out.println("------------------------------------------------------------------------------------------");
                        for(int i=0;i<count_players;i++){
                            System.out.println(players[i].getName()+"\t"+players[i].getMoney()+"\thave: "+players[i].getProperties());  
                        }
                        System.out.println("Banker\t"+thebanker[0].getMoney());
                        System.out.println("Winner "+getWinner(players, count_players));
                        System.out.println("------------------------------------------------------------------------------------------");
                    }
                }else{
                    String [] splited_line = line.split(";");
                    
                    play(players, count_players, thebanker ,properties, count_properties , getPlayerIndex(players, count_players, splited_line[0]), players[getPlayerIndex(players, count_players, splited_line[0])].play(Integer.parseInt(splited_line[1])),total_chance,total_chestCommunity,Integer.parseInt(splited_line[1]),chanceList,chestCommunityList,gameover);
                    if(players[getPlayerIndex(players, count_players, splited_line[0])].getExceptionMoney()>0){
                        thebanker[0].minusMoney(200);
                        players[getPlayerIndex(players, count_players, splited_line[0])].minusExceptionMoney();
                    }
                }
                if(say == count_command){
                    if( gameover[0] != 2 ){
                        System.out.println("------------------------------------------------------------------------------------------");
                        for(int i=0;i<count_players;i++){
                            System.out.println(players[i].getName()+"\t"+players[i].getMoney()+"\thave: "+players[i].getProperties());  
                        }
                        System.out.println("Banker\t"+thebanker[0].getMoney());
                        System.out.println("Winner "+getWinner(players, count_players));
                    }
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("Not such a file");
        }
    }
    public static int isThere(player arr[],int len,String s){
        for(int i=0;i<len;i++){
            if(arr[i].getName().equals(s)){
                return 1;
            }
        }
        arr[len] = new player(s);
        return 0;
    }
    public static boolean inArray(int arr[],int len,int key){
        for(int i=0;i<len;i++){
            if(arr[i]==key){
                return true;
            }
        }
        return false;
    }
    public static boolean isOwner(player players[],int len_player,int position){
        for(int i=0;i<len_player;i++){
            if(players[i].inProperties(position)){
                return true;
            }
        }
        return false;
    }
    public static String getWinner(player players[],int len){
        if(players[0].getMoney() > players[1].getMoney()){
            return players[0].getName();
        }else{
            return players[1].getName();
        }
    }
    public static int getIndex(property properties[],int len,int position){
        for(int i=0;i<len;i++){
            if(properties[i].getPosition()==position){
                return properties[i].getIndex();
            }
        }
        return -1;
    }
    public static int getRent(player players[],int len_player , property properties[] , int len_prop , int index ){
        int total = 0;
        for(int i=0;i<len_player;i++){
            if(i!=index){
                String data = players[1-index].getProperties2();
                String parcala[] = data.split(",");
                if(parcala.length>0){
                    for(int j=0; j < parcala.length ; j++){
                        if(Integer.parseInt(parcala[j])==6 || Integer.parseInt(parcala[j]) ==16 || Integer.parseInt(parcala[j]) == 26 || Integer.parseInt(parcala[j]) == 36){
                            total+=1;
                        }
                    }
                }else{
                   if(Integer.parseInt(data)==6 || Integer.parseInt(data) ==16 || Integer.parseInt(data) == 26 || Integer.parseInt(data) == 36){
                            total+=1;
                        }
                }
            }
        }
        return total;
    }
        
    public static int getPlayerIndex(player players[],int len,String name){
        for(int i=0;i<len;i++){
            if(players[i].getName().equals(name)){
                return i;
            }
        }
        return -1;
    }
    public static void play(player players[],int len_player,banker thebanker[],property prop[],int len_prop,int index,int position,int total_chance[],int total_chestCommunity[],int dice,String chanceList[],String chestCommunityList[],int gameover[]){
        if(gameover[0] == 0){
            if(players[index].getPenalize()){
                int properties[] = {2,4,7,9,10,12,14,15,17,19,20,22,24,25,27,28,30,32,33,35,38,40,6,16,26,36,13,29};
                int communityChest[] = {3,18,34};
                int chance[] = {8,23,37};
                int penalize[] = {5,39};
                if(inArray(properties, properties.length , position)){
                    if(players[index].inProperties(position)){
                    
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName());
               
                    }else{
                        if(isOwner(players, len_player, position)){
                            if(prop[getIndex(prop, len_prop, position)].getType() == 2){
                                int price =  getRent(players, len_player,prop , len_prop , index) * 25;
                                int req = players[index].rent( price, prop[getIndex(prop, len_prop , position)].getName());
                                if(req == -1 ){
                                    gameover[0] = 1;
                                }else{
                                    int otherindex = 1 - index;
                                    players[otherindex].plusMoney(price);
                                    System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" paid rent for "+prop[getIndex(prop, len_prop, position)].getName());
                                }
                            }else{
                                int req = players[index].rent( prop[getIndex(prop, len_prop , position)].getRent(dice), prop[getIndex(prop, len_prop , position)].getName());
                                if(req == -1){
                                    gameover[0] = 1;
                                }else{
                                    int otherindex = 1 - index;
                                    players[otherindex].plusMoney(prop[getIndex(prop, len_prop, position)].getRent(dice));
                                    System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" paid rent for "+prop[getIndex(prop, len_prop, position)].getName());
                                }
                            }
                        }else{
                            int req = players[index].buy(prop[getIndex(prop, len_prop, position)].getCost(),position,prop[getIndex(prop, len_prop, position)].getName());
                            if(req == -1){
                                gameover[0] = 1;
                            }else{
                                thebanker[0].plusMoney(prop[getIndex(prop, len_prop, position)].getCost());
                                System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" bought "+prop[getIndex(prop, len_prop, position)].getName());
                            }
                            
                        }
                    }
                }else if(inArray(communityChest,communityChest.length , position)){
                    int get = total_chestCommunity[0] % 11;
                    total_chestCommunity[0] = total_chestCommunity[0] + 1;
                    if(get == 0){
                        players[index].setPosition(1);
                        players[index].plusMoney(200);
                        thebanker[0].minusMoney(200);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[0]);
                    }else if(get == 1){
                        players[index].plusMoney(75);
                        thebanker[0].minusMoney(75);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[1]);
                    }else if(get == 2){
                        players[index].minusMoney(50);
                        thebanker[0].plusMoney(50);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[2]);
                    }else if(get == 3){
                        for(int i=0;i<len_player;i++){
                            if(i!=index){
                                players[i].minusMoney(10);
                                players[index].plusMoney(10);
                            }
                        }
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[3]);
                    }else if(get == 4){
                        for(int i=0;i<len_player;i++){
                            if(i!=index){
                                players[i].minusMoney(50);
                                players[index].plusMoney(50);
                            }
                        }
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[4]);
                    }else if(get == 5){
                        players[index].plusMoney(20);
                        thebanker[0].minusMoney(20);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[5]);
                    }else if(get == 6){
                        players[index].plusMoney(100);
                        thebanker[0].minusMoney(100);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[6]);
                    }else if(get == 7){
                        players[index].minusMoney(100);
                        thebanker[0].plusMoney(100);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[7]);
                    }else if(get == 8){
                        players[index].minusMoney(50);
                        thebanker[0].plusMoney(50);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[8]);
                    }else if(get == 9){
                        players[index].plusMoney(100);
                        thebanker[0].minusMoney(100);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[9]);
                    }else if(get == 10){
                        players[index].plusMoney(50);
                        thebanker[0].minusMoney(50);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chestCommunityList[10]);
                    }
                }else if(inArray(chance, chance.length, position)){
                    int get = total_chance[0] % 6;
                    total_chance[0] = total_chance[0] + 1;
                    if(get == 0){
                        players[index].setPosition(1);
                        players[index].plusMoney(200);
                        thebanker[0].minusMoney(200);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chanceList[0]);
                    }else if(get == 1){
                        if(27>players[index].getPosition()){
                            
                        }else{
                            players[index].plusMoney(200);
                            thebanker[0].minusMoney(200);
                        }
                        players[index].setPosition(27);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chanceList[1]);
                        play(players, len_player, thebanker ,prop, len_prop , index, 27,total_chance,total_chestCommunity,dice,chanceList,chestCommunityList,gameover);
                        
                    }else if(get == 2){
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chanceList[2]);
                        players[index].setPosition(position-3);
                        play(players, len_player, thebanker ,prop, len_prop , index, position-3,total_chance,total_chestCommunity,dice,chanceList,chestCommunityList,gameover);
                    }else if(get == 3){
                        players[index].minusMoney(15);
                        thebanker[0].plusMoney(15);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chanceList[3]);
                    }else if(get == 4){
                        players[index].plusMoney(150);
                        thebanker[0].minusMoney(150);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chanceList[4]);
                    }else if(get == 5){
                        players[index].plusMoney(100);
                        thebanker[0].minusMoney(100);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" draw "+chanceList[5]);
                    }
                }else if(position==11){
                    System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" went to jail");
                    players[index].penalize();
                }else if(inArray(penalize, penalize.length, position)){
                        players[index].minusMoney(100);
                        thebanker[0].plusMoney(100);
                        System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" paid tax");
                }else if(position==21){
                    System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" is in Free Parking");
                }else if(position==31){ 
                    players[index].setPosition(11);
                    play(players, len_player, thebanker ,prop, len_prop , index, 11 ,total_chance,total_chestCommunity,dice,chanceList,chestCommunityList,gameover);
                }else{
                    System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName());               
                }
            }else{
                System.out.println(players[index].getName()+"\t"+dice+"\t"+players[index].getPosition()+"\t"+players[0].getMoney()+"\t"+players[1].getMoney()+"\t"+players[index].getName()+" in jail (count="+players[index].getPenalizeSize()+")");
                players[index].minusPenalize();
            }
            if(players[index].getMoney()<0 || players[1-index].getMoney() <0 ){
                gameover[0] = 1;
            }
        }else if(gameover[0] == 1){
                    System.out.println("------------------------------------------------------------------------------------------");
                    for(int i=0;i<len_player;i++){
                        System.out.println(players[i].getName()+"\t"+players[i].getMoney()+"\thave: "+players[i].getProperties());  
                    }
                    System.out.println("Banker \t"+thebanker[0].getMoney());
                    System.out.println("Winner "+getWinner(players, len_player));
                    gameover[0] = 2;
        }
    }
}