package wifisurveyor;

/**
 * Created by Behrouz on 14/03/2017.
 */
public class PlainTextTable
{
    /**
     *
     * @return table's data in Jtable format: table[row][column]
     */
    public String[][] getData()
    {
        String [][] data = {
                {"Kathy", "Smith",
                        "Snowboarding"},
                {"John", "Doe",
                        "Rowing"},
                {"Sue", "Black",
                        "Knitting"},
                {"Jane", "White",
                        "Speed reading"},
                {"Joe", "Brown",
                        "Pool"}
        };
        return data;
    }
    public String[] getColumnNames()
    {
        String[] columnNames = {"First Name",
                "Last Name",
                "Sport"};
        return columnNames;
    }
}
