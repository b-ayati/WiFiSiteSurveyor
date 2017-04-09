package wifisurveyor;

/**
 * Created by ayati on 3/15/2017.
 */
public interface UI
{
    void reportStatus(String status);

    void showGeneralErrorMessage(Exception e);

    void showConnectionErrorMessage(Exception e);

    void showFatalErrorMessage(Exception e);
}
