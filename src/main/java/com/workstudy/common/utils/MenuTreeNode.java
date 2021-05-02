package com.workstudy.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 刘其悦
 * @Date: 2020/11/24 20:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeNode {
    private Integer id;
    private Integer pid;
    private Integer level;
    private String name;
    private String path;
    private ArrayList<MenuTreeNode> children = new ArrayList<>(16);

    public MenuTreeNode(Integer id, Integer pid, Integer level, String name, String path){
        this.id = id;
        this.pid = pid;
        this.level = level;
        this.name = name;
        this.path = path;
    }

    public static List<MenuTreeNode> build(List<MenuTreeNode> menuTreeNodes, Integer topId){
        List<MenuTreeNode> result=new ArrayList<>(16);
        for (MenuTreeNode menuTreeNode : menuTreeNodes) {
            if (menuTreeNode.getPid().equals(topId)){
                result.add(menuTreeNode);
            }
            for (MenuTreeNode treeNode : menuTreeNodes) {
                if (treeNode.getPid().equals(menuTreeNode.getId())){
                    menuTreeNode.getChildren().add(treeNode);
                }
            }
        }
        return result;
    }
}
