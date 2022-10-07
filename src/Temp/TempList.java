package Temp;

public class TempList {

    public Temp head;
    public TempList tail;
    public TempList(Temp h, TempList t) {
        head=h;
        tail=t;
    }

    public static TempList toTempList(Temp[] t) {
        TempList[] tailList = new TempList[t.length];
        for (int i = 0; i < t.length; i++)
        {
            tailList[i] = new TempList(t[i],null);
        }
        /*
        *
        * [1,2,3] => { [1] , [2] , [3] }
        *
        * */
        TempList tempList = new TempList(t[0],null);
        for (int i = t.length-2; i != 0; i--)
        {
            tempList.tail = tailList[i+1];
        }
        /*
         *
         * { [1] , [2] , [3] } => [1,[2,[3]]]
         *
         * */

        return tempList;
    }

    public static Temp[] toTempArray(TempList list) {
        Temp[] result = new Temp[1024];
        Temp h = list.head;
        int i = 0;
        while (h != null) {
            result[i] = h;
            list.head = list.tail.head;
            list.tail = list.tail.tail;
            h = list.head;
        }
        return result;
    }

}

