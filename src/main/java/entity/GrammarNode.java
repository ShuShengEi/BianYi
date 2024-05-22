package entity;

import java.util.ArrayList;
import java.util.List;

public class GrammarNode {
    private String value;
    private List<GrammarNode> children;

    public GrammarNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public GrammarNode getChild(int i) {
        return this.children.get(i);
    }
    public boolean addChild(GrammarNode child) {
        this.children.add(child);
        return true;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<GrammarNode> getChildren() {
        return children;
    }

    public void setChildren(List<GrammarNode> children) {
        this.children = children;
    }
    public void printTree(String prefix) {
        System.out.println(prefix + value); // 打印当前节点
        for (GrammarNode child : children) {
            child.printTree(prefix + "--"); // 对每个子节点递归调用此方法，并增加缩进
        }
    }
    @Override
    public String toString() {
        return buildString("", true); // 从根节点开始构建字符串
    }

    // 辅助方法，用于递归构建字符串
    private String buildString(String prefix, boolean isTail) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix + (isTail ? "└── " : "├── ") + value + "\n");
        for (int i = 0; i < children.size() - 1; i++) {
            builder.append(children.get(i).buildString(prefix + (isTail ? "    " : "│   "), false));
        }
        if (children.size() > 0) {
            builder.append(children.get(children.size() - 1)
                    .buildString(prefix + (isTail ?"    " : "│   "), true));
        }
        return builder.toString();
    }
}
