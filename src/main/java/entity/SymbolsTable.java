package entity;

public class SymbolsTable {

   String area;
   String type;
   String name;
   String value;

    public SymbolsTable(String area, String type, String name, String value) {
        this.area = area;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.area + "\t" + this.type + "\t" + this.name  + "\n";
    }

    public SymbolsTable() {
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
