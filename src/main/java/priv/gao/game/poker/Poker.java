package priv.gao.game.poker;

import java.util.LinkedList;
import java.util.Objects;

public class Poker implements Comparable{

    public static final String[] COLORS = {"E","O","M","F"};//花色 黑红梅方
    public static final String[] NUMS = {"3","4","5","6","7","8","9","10","J","Q","K","A","2"};//数字
    public static final String[] NUM_OTH = {"XW","DW"};//大小王

    public static final int JG = -1;//进贡
    public static final int ZC = 0;//正常
    public static final int HD = 1;//皇帝牌
    public static final int BZ = 2;//保子牌

    private Integer rank; //分值
    private String num;//数字
    private String suit; //花色
    private Integer type;//-1进贡 0正常 1皇帝牌 2保子牌

    public Poker() { }

    public Poker(Integer rank, String num, String suit) {
        this.rank = rank;
        this.num = num;
        this.suit = suit;
        this.type = ZC;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return (this.type != 0 ? this.type : "") + this.suit + this.num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poker poker = (Poker) o;
        return Objects.equals(num, poker.num) && Objects.equals(suit, poker.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, suit, type);
    }

    @Override
    public int compareTo(Object o) {
        Poker poker = (Poker)o;
        return poker.sortValue() - this.sortValue();
    }

    public int sortValue(){
        switch (this.suit){
            case "E" : return this.rank * 4;
            case "O" : return this.rank * 4 - 1;
            case "M" : return this.rank * 4 - 2;
            case "F" : return this.rank * 4 - 3;
            case "XW" : return this.rank * 4 - 3;
            case "DW" : return (this.rank - 1) * 4 - 2;
            default: return 0;
        }
    }

    public static LinkedList<Poker> createPoker(int size){
        LinkedList<Poker> pokers = new LinkedList<Poker>();
        for (int i = 0; i < size; i++) {
            pokers.add(new Poker(14,NUM_OTH[0],NUM_OTH[0]));
            pokers.add(new Poker(15,NUM_OTH[1],NUM_OTH[1]));
            for (int j = 0; j < COLORS.length; j++) {
                for (int k = 0; k <  NUMS.length; k++) {
                    pokers.add(new Poker(k+1, NUMS[k], COLORS[j]));
                }
            }
        }
        return pokers;
    }
}
