package Temp;

import java.util.List;
import java.util.ArrayList;

public class LabelList {
   public Label head;
   public LabelList tail;
   public LabelList(Label h, LabelList t) {head=h; tail=t;}
   public static List<Label> listLabelToList(LabelList labelList) {
      return listLabelToList(new ArrayList<Label>(), labelList);
   }

   private static List<Label> listLabelToList(List<Label> list, LabelList labelList) {
      if (labelList == null || labelList.head == null) {
         return list;
      }
      list.add(labelList.head);
      return listLabelToList(list, labelList.tail);
   }
}

