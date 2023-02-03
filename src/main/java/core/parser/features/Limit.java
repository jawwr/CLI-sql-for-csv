package core.parser.features;

import core.structure.Table;

import java.util.List;

public class Limit implements Feature{
    @Override
    public Table parse(List<String> args, Table table) {
        int limitValue = 0;
        try{
            limitValue = Integer.parseInt(args.get(0));
            limitValue = Math.min(table.getValues().length, limitValue);
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid 'LIMIT' parameter");
        }
        var values = table.getValues();
        var newValues = new String[limitValue][];
        System.arraycopy(values, 0, newValues, 0, limitValue);
        table.setValues(newValues);

        return table;
    }
}
