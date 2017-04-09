package wifisurveyor;

/**
 * Created by Behrouz on 14/03/2017.
 */
public class PlainTextTable
{
    private String[] tableColumn;
    private String[][] tableData;

    public PlainTextTable(String[] tableColumn, String[][] tableData)
    {
        this.tableColumn = tableColumn;
        this.tableData = tableData;
    }

    /**
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
