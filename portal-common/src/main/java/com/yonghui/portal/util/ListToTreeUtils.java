package com.yonghui.portal.util;

import com.yonghui.portal.model.global.Menu;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ListToTreeUtils<T> {
    Logger log = Logger.getLogger(this.getClass());

    public List<Menu> listTreeMenu(List<Menu> list) {

        List<Menu> nodeList = new ArrayList<Menu>();
        for (Menu node1 : list) {
            boolean mark = false;
            for (Menu node2 : list) {
                if (node1.getPid() != 0 && node1.getPid().equals(node2.getId())) {
                    mark = true;
                    if (node2.getChildren() == null)
                        node2.setChildren(new ArrayList<Menu>());
                    node2.setText(node2.getName());
                    node1.setText(node1.getName());
//					node1.setChecked("0");
                    node2.getChildren().add(node1);
                    break;
                }
            }
            if (!mark) {
//				node1.setChecked("0");
                node1.setText(node1.getName());
//				node1.setState("closed");
                nodeList.add(node1);
            }
        }
        return nodeList;
    }


}
