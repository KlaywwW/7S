package com.example.check.util;

import java.util.*;

/**
 * 将数组里的值分组
 */
public class ArrayGroupUtil {
    class Group {
        private List<String> ins = new ArrayList<String>();

        public List<String> getIns() {
            return ins;
        }

        public void setIns(List<String> ins) {
            this.ins = ins;
        }

    }
    public int merge(List<String> hay) {
        Set<String> keys = new HashSet<String>();
        Map<String, Group> groups = new HashMap<String, Group>();
        for (String each : hay) {
            keys.add(each);
        }
        for (String key : keys) {
            groups.put(key, new Group());
        }
        for (String each : hay) {
            groups.get(each).getIns().add(each);
        }
       return display(groups);
    }

    public int display(Map<String, Group> groups) {
        int count=0;
        for (Map.Entry<String, Group> each : groups.entrySet()) {
            System.err.println("++++++++++++++++++==");
            for (String g : each.getValue().getIns()) {
                System.err.println(g);
            }
            System.err.println("+++++++++++++++++++++");
            count++;
        }
        System.out.println(count);
        return count;
    }
}
