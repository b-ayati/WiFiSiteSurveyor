package wifisurveyor;

/**
 * Created by Behrouz on 14/03/2017.
 */
public class PlainTextTable
{
    private String[] tableColumn;
    private String[][] tableData;

    public PlainTextTable(String[] tableColumn, String[][] tableData) {
        this.tableColumn = tableColumn;
        this.tableData = tableData;
    }
    public PlainTextTable(){
        this.tableColumn = new String[0];
        this.tableData = new String[0][0];
    }
    /**
     *
     * @return table's data in Jtable format: table[row][column]
     */
    public String[][] getData()
    {
        return tableData;
    }
    public String[] getColumnNames()
    {
        return tableColumn;
    }
}
